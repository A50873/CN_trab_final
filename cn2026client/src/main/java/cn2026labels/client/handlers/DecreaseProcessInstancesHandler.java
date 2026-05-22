package cn2026labels.client.handlers;

import servicestubs.*;
import java.util.Scanner;

public class DecreaseProcessInstancesHandler {
    public static void handle(Scanner scanner, SGServiceGrpc.SGServiceBlockingStub sgBlockingStub) {
        System.out.println("Number of processing instances to remove: ");
        try {
            int amount = scanner.nextInt();

            if(amount <= 0) {
                System.out.println("Amount must be greater than 0.");
                return;
            }

            ScaleRequest request = ScaleRequest.newBuilder().setAmount(amount).build();
            ScaleResponse response = sgBlockingStub.decreaseProcessingInstances(request);

            System.out.println("Response:");
            System.out.println("  Success: " + response.getSuccess());
            System.out.println("  Previous size: " + response.getPreviousSize());
            System.out.println("  New size: " + response.getNewSize());
            System.out.println("  Message: " + response.getMessage());

        } catch (Exception e) {
            System.out.println("Error decreasing processing instances: " + e.getMessage());
        }
    }
}

