package cn2026server.services.functional.functions;

import cn2026labels.labels.LabelsApp;
import cn2026labels.pubsub.PubSubPublisher;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import servicestubs.ImageChunk;
import servicestubs.SubmitImageResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public final class SubmitImageFunction {

	private SubmitImageFunction() {
	}

	public static StreamObserver<ImageChunk> submitImage(com.google.cloud.firestore.Firestore firestore,
														 PubSubPublisher publisher,
														 Storage storage,
														 String bucketName,
														 String collectionName,
														 StreamObserver<SubmitImageResponse> responseObserver) {

		return new StreamObserver<>() {

			final ByteArrayOutputStream bytesBuffer = new ByteArrayOutputStream();
			String imageName;
			String contentType;

			@Override
			public void onNext(ImageChunk imageChunk) {
				if (imageChunk == null) {
					responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("image chunk can't be null").asRuntimeException());
					return;
				}

				if (imageName == null || imageName.isBlank()) {
					imageName = imageChunk.getImageName();
				}

				if (contentType == null || contentType.isBlank()) {
					contentType = imageChunk.getContentType();
				}

				try {
					imageChunk.getData().writeTo(bytesBuffer);
				} catch (IOException e) {
					responseObserver.onError(Status.INTERNAL.withDescription("Failed to process image data").withCause(e).asRuntimeException());
				}
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("submitImage stream failed:. " + throwable.getMessage());
				bytesBuffer.reset();
			}

			@Override
			public void onCompleted() {
				if (bytesBuffer.size() == 0 || imageName == null || imageName.isBlank()) {
					responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("No image data was received").asRuntimeException());
					return;
				}

				String requestId = UUID.randomUUID().toString();

				try {
					BlobId blobId = BlobId.of(bucketName, FunctionalServiceSupport.blobNameFor(requestId, imageName));
					if (storage.get(blobId) != null) {
						responseObserver.onError(Status.ALREADY_EXISTS.withDescription("Image already exists in bucket: " + blobId.getName()).asRuntimeException());
						return;
					}

					byte[] imageBytes = bytesBuffer.toByteArray();
					BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
							.setContentType(contentType)
							.setMetadata(Map.of(FunctionalServiceSupport.REQUEST_ID_METADATA_KEY, requestId))
							.build();
					storage.create(blobInfo, imageBytes);

					try {
						LabelsApp.saveUploadMetadata(firestore, requestId, imageName, contentType, imageBytes.length, bucketName, collectionName);
					} catch (Exception firestoreError) {
						storage.delete(blobId);
						throw firestoreError;
					}

					if (publisher != null) {
						try {
							String gsUri = "gs://" + bucketName + "/" + blobId.getName();
							String json = "{\"requestId\":\"" + requestId + "\",\"gsUri\":\"" + gsUri + "\",\"imageName\":\"" + imageName + "\",\"bucket\":\"" + bucketName + "\"}";
							publisher.publishJson(json, Map.of("requestId", requestId));
						} catch (Exception ex) {
							System.err.println("Warning: failed to publish Pub/Sub message: " + ex.getMessage());
						}
					}
				} catch (Exception e) {
					responseObserver.onError(Status.INTERNAL.withDescription("Failed to persist image to Cloud Storage: " + e.getMessage()).withCause(e).asRuntimeException());
					return;
				}

				SubmitImageResponse response = SubmitImageResponse.newBuilder().setRequestId(requestId).build();
				responseObserver.onNext(response);
				responseObserver.onCompleted();
			}
		};
	}
}
