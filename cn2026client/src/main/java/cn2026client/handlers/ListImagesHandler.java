package cn2026client.handlers;

import servicestubs.*;

public class ListImagesHandler {
    public static void handle(SFServiceGrpc.SFServiceBlockingStub sfBlockingStub) {
        try {
            ListImagesRequest request = ListImagesRequest.newBuilder().build();
            ListImagesResponse response = sfBlockingStub.listImages(request);

            System.out.println("Images in bucket:");
            if(response.getImageNamesCount() == 0) {
                System.out.println("  (no images)");
            } else {
                for(String name : response.getImageNamesList()) {
                    System.out.println("  - " + name);
                }
            }
        } catch (Exception e) {
            System.out.println("Error listing images: " + e.getMessage());
        }
    }
}

