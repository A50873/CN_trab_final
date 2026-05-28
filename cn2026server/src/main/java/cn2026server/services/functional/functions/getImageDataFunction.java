package cn2026server.services.functional.functions;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.GetImageDataResponse;
import servicestubs.ImageIdRequest;
import servicestubs.LabelData;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class getImageDataFunction {

	private getImageDataFunction() {
	}

	public static void getImageData(Firestore firestore,
	                                Storage storage,
	                                String bucketName,
	                                String collectionName,
	                                ImageIdRequest request,
	                                StreamObserver<GetImageDataResponse> responseObserver) {
		try {
			String identifier = request.getImageName();

			if (identifier.isBlank()) {
				responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("image name is required").asRuntimeException());
				return;
			}

			System.out.println("getImageData: Looking for image/request: " + identifier);

			FunctionalServiceSupport.ResolvedImageStorage resolvedImageStorage =
					FunctionalServiceSupport.resolveImageStorage(firestore, collectionName, bucketName, identifier);

			if (resolvedImageStorage == null) {
				responseObserver.onError(Status.NOT_FOUND.withDescription("No image found for: " + identifier).asRuntimeException());
				return;
			}

			Blob blob = storage.get(resolvedImageStorage.blobId());

			if (blob == null) {
				System.out.println("getImageData: Blob not found: " + resolvedImageStorage.blobId().getName());
				responseObserver.onError(Status.NOT_FOUND.withDescription("No image found for: " + identifier).asRuntimeException());
				return;
			}

			System.out.println("getImageData: Blob found, reading metadata from Firestore...");

			QuerySnapshot snapshot = firestore.collection(collectionName)
					.whereEqualTo("request_id", resolvedImageStorage.requestId() != null ? resolvedImageStorage.requestId() : identifier)
					.get()
					.get();

			if (snapshot.isEmpty()) {
				snapshot = firestore.collection(collectionName)
						.whereEqualTo("image_name", resolvedImageStorage.imageName() != null ? resolvedImageStorage.imageName() : identifier)
						.get()
						.get();
			}

			if (snapshot.isEmpty()) {
				responseObserver.onError(Status.NOT_FOUND.withDescription("No image data found for: " + identifier).asRuntimeException());
				return;
			}

			DocumentSnapshot selectedDoc = snapshot.getDocuments().getFirst();

			String requestId = selectedDoc.getString("request_id");
			String imageName = selectedDoc.getString("image_name");
			Date processedAtDate = selectedDoc.getDate("processed_at");

			GetImageDataResponse.Builder responseBuilder = GetImageDataResponse.newBuilder();

			if (requestId != null && !requestId.isBlank()) {
				responseBuilder.setRequestId(requestId);
			}

			responseBuilder.setImageName(imageName);

			if (processedAtDate != null) {
				responseBuilder.setProcessedAt(
						Timestamp.newBuilder()
								.setSeconds(processedAtDate.getTime() / 1000)
								.setNanos((int) ((processedAtDate.getTime() % 1000) * 1_000_000))
								.build()
				);
			} else {
				responseBuilder.setProcessedAt(
						Timestamp.newBuilder()
								.setSeconds(System.currentTimeMillis() / 1000)
								.build()
				);
			}

			Object labelsObj = selectedDoc.get("labels");
			if (labelsObj instanceof List<?> labelsList) {
				for (Object entryObj : labelsList) {
					if (!(entryObj instanceof Map<?, ?> entry)) {
						continue;
					}

					String labelEng = valueAsString(entry.get("label_eng"));
					String labelPt = valueAsString(entry.get("label_pt"));
					float confidenceScore = valueAsFloat(entry.get("confidence_score"));

					LabelData labelData = LabelData.newBuilder()
							.setLabelEng(labelEng != null ? labelEng : "")
							.setLabelPt(labelPt != null ? labelPt : "")
							.setConfidenceScore(confidenceScore)
							.build();

					responseBuilder.addLabels(labelData);
				}
			}

			responseObserver.onNext(responseBuilder.build());
			responseObserver.onCompleted();
			System.out.println("getImageData: Response sent successfully");
		} catch (Throwable e) {
			System.out.println("getImageData: Error - " + e.getClass().getName() + ": " + e.getMessage());
			responseObserver.onError(Status.INTERNAL.withDescription("Failed to process image: " + e.getMessage()).withCause(e).asRuntimeException());
		}
	}

	private static String valueAsString(Object value) {
		return value == null ? null : value.toString();
	}

	private static float valueAsFloat(Object value) {
		if (value instanceof Number number) {
			return number.floatValue();
		}
		if (value != null) {
			try {
				return Float.parseFloat(value.toString());
			} catch (NumberFormatException ignored) {
			}
		}
		return 0.0f;
	}
}