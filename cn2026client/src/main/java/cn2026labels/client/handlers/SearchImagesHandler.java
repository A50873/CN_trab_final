package cn2026labels.client.handlers;

import servicestubs.*;
import com.google.protobuf.Timestamp;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.Scanner;

public class SearchImagesHandler {

    public static void handle(Scanner scanner, SFServiceGrpc.SFServiceBlockingStub sfBlockingStub) {
        scanner.nextLine(); // clear buffer from previous nextInt()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("Start date (dd/MM/yyyy HH:mm:ss): ");
        long startSeconds;
        try{
            String startDateStr = scanner.nextLine().trim();
            if(startDateStr.isEmpty()){
                startSeconds = 0;
            } else {
                LocalDateTime startDt = LocalDateTime.parse(startDateStr, formatter);
                ZonedDateTime startZdt = startDt.atZone(ZoneId.of("UTC"));
                startSeconds = startZdt.toEpochSecond() - 3600;
            }
        } catch (Exception e){
            System.out.println("Invalid start date: " + e.getMessage());
            return;
        }

        System.out.println("End date (dd/MM/yyyy HH:mm:ss): ");
        long endSeconds;
        try{
            String endDateStr = scanner.nextLine().trim();
            if(endDateStr.isEmpty()){
                endSeconds = Instant.now().getEpochSecond();
            } else {
                LocalDateTime endDt = LocalDateTime.parse(endDateStr, formatter);
                ZonedDateTime endZdt = endDt.atZone(ZoneId.of("UTC"));
                endSeconds = endZdt.toEpochSecond() - 3600;
            }
        } catch (Exception e){
            System.out.println("Invalid end date: " + e.getMessage());
            return;
        }

        System.out.println("Label: ");
        String label = scanner.nextLine().trim();
        if(label.isEmpty()){
            System.out.println("Invalid label.");
            return;
        }

        try{
            SearchImagesRequest request = SearchImagesRequest.newBuilder()
                    .setDateStart(Timestamp.newBuilder().setSeconds(startSeconds).build())
                    .setDateEnd(Timestamp.newBuilder().setSeconds(endSeconds).build())
                    .setLabel(label)
                    .build();

            SearchImagesResponse response = sfBlockingStub.searchImages(request);

            System.out.println("Search results:");
            if(response.getImageNamesCount() == 0){
                System.out.println("  (no matches)");
            } else {
                for(String name : response.getImageNamesList()){
                    System.out.println("  - " + name);
                }
            }

        }catch (Exception e){
            System.out.println("Error searching images: " + e.getMessage());
        }
    }
}

