package cn2026server.services.functional.functions;

import cn2026labels.labels.LabelsApp;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Storage;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.GetImageDataResponse;
import servicestubs.ImageIdRequest;
import servicestubs.LabelData;

import java.util.ArrayList;
import java.util.List;

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

			FunctionalServiceSupport.ResolvedImageStorage resolvedImageStorage = FunctionalServiceSupport.resolveImageStorage(firestore, collectionName, bucketName, identifier);
			if (resolvedImageStorage == null) {
				responseObserver.onError(Status.NOT_FOUND.withDescription("No image found for: " + identifier).asRuntimeException());
				return;
			}

			com.google.cloud.storage.Blob blob = storage.get(resolvedImageStorage.blobId());

			if (blob == null) {
				System.out.println("getImageData: Blob not found: " + resolvedImageStorage.blobId().getName());
				responseObserver.onError(Status.NOT_FOUND.withDescription("No image found for: " + identifier).asRuntimeException());
				return;
			}

			System.out.println("getImageData: Blob found, detecting labels...");

			String requestId = resolvedImageStorage.requestId();
			if (requestId == null || requestId.isBlank()) {
				requestId = blob.getMetadata() != null ? blob.getMetadata().get(FunctionalServiceSupport.REQUEST_ID_METADATA_KEY) : null;
			}

			String imageName = resolvedImageStorage.imageName() != null && !resolvedImageStorage.imageName().isBlank()
					? resolvedImageStorage.imageName()
					: FunctionalServiceSupport.imageNameFromBlobName(resolvedImageStorage.blobId().getName());

			String gsURI = "gs://" + bucketName + "/" + resolvedImageStorage.blobId().getName();
			System.out.println("getImageData: Calling detectLabels with URI: " + gsURI);
			List<LabelsApp.DetectedLabel> detectedLabels = LabelsApp.detectLabels(gsURI);
			List<String> labelsEng = new ArrayList<>();
			for (LabelsApp.DetectedLabel detectedLabel : detectedLabels) {
				labelsEng.add(detectedLabel.description);
			}
			System.out.println("getImageData: Labels detected: " + labelsEng.size());

			List<String> labelsPt = LabelsApp.translateLabels(labelsEng);
			System.out.println("getImageData: Labels translated: " + labelsPt.size());

			try {
				LabelsApp.saveProcessingMetadata(firestore, requestId, imageName, detectedLabels, labelsPt, bucketName, collectionName);
			} catch (Exception firestoreError) {
				System.out.println("getImageData: warning - failed to persist processing metadata in Firestore: " + firestoreError.getMessage());
			}

			GetImageDataResponse.Builder responseBuilder = GetImageDataResponse.newBuilder();
			if (requestId != null) {
				responseBuilder.setRequestId(requestId);
			}
			responseBuilder.setImageName(imageName);
			responseBuilder.setProcessedAt(Timestamp.newBuilder().setSeconds(System.currentTimeMillis() / 1000).build());

			int count = Math.min(labelsEng.size(), labelsPt.size());
			for (int i = 0; i < count; i++) {
				LabelData labelData = LabelData.newBuilder()
						.setLabelEng(labelsEng.get(i))
						.setLabelPt(labelsPt.get(i))
						.setConfidenceScore(detectedLabels.get(i).confidence)
						.build();
				responseBuilder.addLabels(labelData);
			}

			responseObserver.onNext(responseBuilder.build());
			responseObserver.onCompleted();
			System.out.println("getImageData: Response sent successfully");
		} catch (Throwable e) {
			System.out.println("getImageData: Error - " + e.getClass().getName() + ": " + e.getMessage());
			responseObserver.onError(Status.INTERNAL.withDescription("Failed to process image: " + e.getMessage()).withCause(e).asRuntimeException());
		}
	}
}
