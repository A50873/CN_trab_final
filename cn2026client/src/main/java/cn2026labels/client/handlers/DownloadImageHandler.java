package cn2026labels.client.handlers;

import servicestubs.*;
import io.grpc.stub.StreamObserver;
import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class DownloadImageHandler {

    public static void handle(Scanner scanner, SFServiceGrpc.SFServiceStub sfNoBlockStub) {
        System.out.println("Image name: ");
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

        File outputFile = new File(downloadDir, imageName);

        if(outputFile.exists()) {
            System.out.println("File already exists: " + outputFile.getAbsolutePath());
            return;
        }

        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<ImageChunk> responseObserver = new StreamObserver<ImageChunk>() {
            private java.io.FileOutputStream fos;

            @Override
            public void onNext(ImageChunk chunk) {
                try {
                    if(fos == null) {
                        fos = new java.io.FileOutputStream(outputFile);
                    }
                    chunk.getData().writeTo(fos);
                } catch (Exception e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Download failed: " + throwable.getMessage());
                try {
                    if(fos != null) fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(outputFile.exists()) {
                    outputFile.delete();
                }
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                try {
                    if(fos != null) {
                        fos.close();
                    }
                    System.out.println("Download completed: " + outputFile.getAbsolutePath());
                } catch (Exception e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
                finishLatch.countDown();
            }
        };

        try {
            DownloadImageRequest request = DownloadImageRequest.newBuilder().setImageName(imageName).build();
            sfNoBlockStub.downloadImage(request, responseObserver);

            if(!finishLatch.await(60, TimeUnit.SECONDS)) {
                System.out.println("Download timed out");
                if(outputFile.exists()) {
                    outputFile.delete();
                }
            }
        } catch (Exception e) {
            System.out.println("Error downloading image: " + e.getMessage());
            if(outputFile.exists()) {
                outputFile.delete();
            }
        }
    }
}

