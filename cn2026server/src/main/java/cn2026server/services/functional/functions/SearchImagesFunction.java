package cn2026server.services.functional.functions;

import cn2026labels.labels.LabelsApp;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.SearchImagesRequest;
import servicestubs.SearchImagesResponse;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class SearchImagesFunction {

	private SearchImagesFunction() {
	}

	public static void searchImages(com.google.cloud.firestore.Firestore firestore,
									String collectionName,
									SearchImagesRequest request,
									StreamObserver<SearchImagesResponse> responseObserver) {

		String label = request.getLabel();
		boolean hasStart = request.hasDateStart();
		boolean hasEnd = request.hasDateEnd();

		if (!hasStart) {
			responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("start date is required").asRuntimeException());
			return;
		}

		if (!hasEnd) {
			responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("end date is required").asRuntimeException());
			return;
		}

		if (label.isBlank()) {
			responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("label is required").asRuntimeException());
			return;
		}

		Instant start = Instant.ofEpochSecond(request.getDateStart().getSeconds(), request.getDateStart().getNanos());
		Instant end = Instant.ofEpochSecond(request.getDateEnd().getSeconds(), request.getDateEnd().getNanos());

		if (start.isAfter(end)) {
			responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("starting date must be before or equal to end date").asRuntimeException());
			return;
		}

		try {
			System.out.println("searchImages: start date = " + start + ", end date = " + end + ", label = " + label);
			List<String> matches = new ArrayList<>();
			int docsChecked = 0;

			ApiFuture<QuerySnapshot> future = firestore.collection(collectionName)
					.whereGreaterThanOrEqualTo("processed_at", Date.from(start))
					.whereLessThanOrEqualTo("processed_at", Date.from(end))
					.get();

			for (QueryDocumentSnapshot doc : future.get().getDocuments()) {
				docsChecked++;

				String imageName = doc.getString("image_name");
				if (imageName == null || imageName.isBlank()) {
					continue;
				}

				String wanted = label.trim().toLowerCase();
				boolean matched = LabelsApp.matchesLabel(doc.get("labels_eng"), wanted)
						|| LabelsApp.matchesLabel(doc.get("labels_pt"), wanted);

				if (matched) {
					System.out.println("searchImages: adding " + imageName + " to results");
					matches.add(imageName);
				}

				if (matches.size() >= 100) {
					break;
				}
			}

			System.out.println("searchImages: checked " + docsChecked + " firestore docs, found " + matches.size() + " matches");
			SearchImagesResponse.Builder resp = SearchImagesResponse.newBuilder();
			resp.addAllImageNames(matches);
			responseObserver.onNext(resp.build());
			responseObserver.onCompleted();
		} catch (Throwable t) {
			System.out.println("searchImages: Error - " + t.getClass().getName() + ": " + t.getMessage());
			System.err.println("searchImages: unexpected error");
			responseObserver.onError(Status.INTERNAL.withDescription("Failed to perform search: " + t.getMessage()).withCause(t).asRuntimeException());
		}
	}
}

