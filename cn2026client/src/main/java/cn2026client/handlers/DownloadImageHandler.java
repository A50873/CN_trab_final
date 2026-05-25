package cn2026client.handlers;

import servicestubs.*;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class DownloadImageHandler {

    private static void deleteFileQuietly(File file) {
        if (file != null && file.exists() && !file.delete()) {
            System.err.println("Unable to delete file: " + file.getAbsolutePath());
        }
    }

    public static void handle(Scanner scanner, SFServiceGrpc.SFServiceStub sfNoBlockStub) {
        System.out.println("Request ID ou nome da imagem: ");
        String imageName = scanner.next();

        if(imageName.isBlank()) {
            System.out.println("Invalid image name.");
            return;
        }

        String downloadsPath = System.getProperty("user.home") + File.separator + "Downloads";
        File downloadDir = new File(downloadsPath);

        if(!downloadDir.exists() || !downloadDir.isDirectory()) {
            System.out.println("Downloads folder not found at: " + downloadsPath);
            return;
        }

        CountDownLatch finishLatch = new CountDownLatch(1);
        final File[] outputFileHolder = new File[1];
        final AtomicBoolean failed = new AtomicBoolean(false);

        ClientResponseObserver<DownloadImageRequest, ImageChunk> responseObserver = new ClientResponseObserver<>() {
            private java.io.FileOutputStream fos;
            private ClientCallStreamObserver<DownloadImageRequest> requestStream;

            @Override
            public void onNext(ImageChunk chunk) {
                try {
                    if (fos == null) {
                        outputFileHolder[0] = new File(downloadDir, chunk.getImageName());
                        File outputFile = outputFileHolder[0];

                                if (outputFile.exists()) {
                                    System.out.println("File already exists: " + outputFile.getAbsolutePath());
                                    // mark as failed and stop processing further chunks on client side
                                    failed.set(true);
                                    // cancel the RPC so the server stops sending chunks
                                    if (requestStream != null) {
                                        try {
                                            requestStream.cancel("client-abort-file-exists", null);
                                        } catch (Exception ex) {
                                            // ignore
                                        }
                                    }
                                    finishLatch.countDown();
                                    return;
                                }

                        fos = new java.io.FileOutputStream(outputFile);
                    }
                    chunk.getData().writeTo(fos);
                } catch (Exception e) {
                    failed.set(true);
                    System.out.println("Error writing to file: " + e.getMessage());
                    try {
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (Exception closeError) {
                        System.err.println("Error closing file after write failure: " + closeError.getMessage());
                    }
                    deleteFileQuietly(outputFileHolder[0]);
                    finishLatch.countDown();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                failed.set(true);
                System.out.println("Download failed: " + throwable.getMessage());
                try {
                    if (fos != null) fos.close();
                } catch (Exception e) {
                    System.err.println("Error closing file after download failure: " + e.getMessage());
                }
                deleteFileQuietly(outputFileHolder[0]);
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (failed.get()) {
                        System.out.println("Download failed.");
                    } else if (outputFileHolder[0] != null) {
                        System.out.println("Download completed: " + outputFileHolder[0].getAbsolutePath());
                    } else {
                        System.out.println("Download completed.");
                    }
                } catch (Exception e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
                finishLatch.countDown();
            }
            @Override
            public void beforeStart(ClientCallStreamObserver<DownloadImageRequest> requestStream) {
                this.requestStream = requestStream;
            }
        };

        try {
            DownloadImageRequest request = DownloadImageRequest.newBuilder().setImageName(imageName).build();
            sfNoBlockStub.downloadImage(request, responseObserver);

            if(!finishLatch.await(60, TimeUnit.SECONDS)) {
                System.out.println("Download timed out");
                deleteFileQuietly(outputFileHolder[0]);
            }
        } catch (Exception e) {
            System.out.println("Error downloading image: " + e.getMessage());
        }
    }
}

