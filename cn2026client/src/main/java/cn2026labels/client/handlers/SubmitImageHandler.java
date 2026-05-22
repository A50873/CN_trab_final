package cn2026labels.client.handlers;

import cn2026labels.client.GrpcClient; // for types only
import io.grpc.stub.StreamObserver;
import servicestubs.*;

import com.google.protobuf.ByteString;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class SubmitImageHandler {

    public static void handle(Scanner scanner, SFServiceGrpc.SFServiceStub sfNoBlockStub) {
        System.out.println("Image path: ");
        String imagePath = scanner.next();

        if(imagePath.contains("\"")){
            imagePath = imagePath.replace("\"", "");
        }

        File file = new File(imagePath);

        if(!file.exists() || !file.isFile()){
            System.out.println("Invalid file path.");
            return;
        }

        String imageName = file.getName();
        String contentType;

        try{
            contentType = Files.probeContentType(file.toPath());
        }catch (Exception e){
            contentType = null;
        }

        if(contentType == null || contentType.isBlank()){
            contentType = "application/octet-stream";
        }

        CountDownLatch finishLatch = new CountDownLatch(1);

        StreamObserver<SubmitImageResponse> responseStreamObserver = new StreamObserver<SubmitImageResponse>() {
            @Override
            public void onNext(SubmitImageResponse response) {
                System.out.println("request_id="+ response.getRequestId());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("submitImage failed: " + throwable.getMessage());
                finishLatch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("submitImage completed.");
                finishLatch.countDown();
            }
        };

        StreamObserver<ImageChunk> requestObserver = sfNoBlockStub.submitImage(responseStreamObserver);

        try(FileInputStream fis = new FileInputStream(file)) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while((bytesRead = fis.read(buffer)) != -1){
                ImageChunk chunk = ImageChunk.newBuilder().setImageName(imageName)
                        .setContentType(contentType)
                        .setData(ByteString.copyFrom(buffer, 0, bytesRead)).build();
                requestObserver.onNext(chunk);
            }

            requestObserver.onCompleted();

            if(!finishLatch.await(10, TimeUnit.SECONDS)){
                System.out.println("Timed out waiting for server response");
            }

        }catch (Exception e){
            requestObserver.onError(e);
            System.out.println("Error reading/sending file: " + e.getMessage());
        }
    }
}

