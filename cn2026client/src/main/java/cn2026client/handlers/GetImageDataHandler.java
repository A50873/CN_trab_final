package cn2026client.handlers;

import servicestubs.*;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GetImageDataHandler {

    public static void handle(Scanner scanner, SFServiceGrpc.SFServiceBlockingStub sfBlockingStub) {
        System.out.println("Request ID ou nome da imagem: ");
        String imageName = scanner.next();

        if(imageName.isBlank()){
            System.out.println("Invalid image name.");
            return;
        }

        try {
            ImageIdRequest request = ImageIdRequest.newBuilder().setImageName(imageName).build();
            GetImageDataResponse response = sfBlockingStub.getImageData(request);

            System.out.println("Image data retrieved:");
            System.out.println("  Request ID: " + response.getRequestId());
            System.out.println("  Image Name: " + response.getImageName());

            Timestamp processedAt = response.getProcessedAt();
            String formattedProcessedAt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochSecond(processedAt.getSeconds(), processedAt.getNanos()));

            System.out.println("  Processed At: " + formattedProcessedAt);
            System.out.println("  Labels:");

            for(LabelData label : response.getLabelsList()) {
                System.out.println("    - " + label.getLabelEng() + " -> " + label.getLabelPt() + " (confidence: " + label.getConfidenceScore() + ")");
            }

        } catch (Exception e) {
            System.out.println("Error retrieving image data: " + e.getMessage());
        }
    }
}

