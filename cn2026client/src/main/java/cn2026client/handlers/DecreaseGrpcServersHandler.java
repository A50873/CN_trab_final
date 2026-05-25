package cn2026client.handlers;

import servicestubs.*;
import io.grpc.StatusRuntimeException;
import io.grpc.Status;
import java.util.Scanner;

public class DecreaseGrpcServersHandler {
    public static void handle(Scanner scanner, SGServiceGrpc.SGServiceBlockingStub sgBlockingStub) {
        System.out.println("Number of servers to remove: ");
        try {
            int amount = scanner.nextInt();

            if(amount <= 0) {
                System.out.println("Amount must be greater than 0.");
                return;
            }

            ScaleRequest request = ScaleRequest.newBuilder().setAmount(amount).build();

            try {
                ScaleResponse response = sgBlockingStub.decreaseGrpcServers(request);
                printResponse(response);
            } catch (StatusRuntimeException e) {
                if (e.getStatus().getCode() == Status.Code.PERMISSION_DENIED) {
                    String message = e.getStatus().getDescription();
                    if (message != null && message.contains("CONFIRMATION REQUIRED")) {
                        System.out.println("\n" + message);
                        System.out.print("Proceed? [y/n]: ");
                        String confirmation = scanner.next().trim().toLowerCase();

                        if ("y".equals(confirmation) || "yes".equals(confirmation)) {
                            System.out.println("Confirmed. Reducing gRPC servers...");
                            ScaleResponse response = sgBlockingStub.decreaseGrpcServers(request);
                            printResponse(response);
                        } else {
                            System.out.println("Operation cancelled.");
                        }
                    } else {
                        System.out.println("Error: " + e.getStatus().getDescription());
                    }
                } else {
                    System.out.println("Error decreasing gRPC servers: " + e.getStatus().getDescription());
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void printResponse(ScaleResponse response) {
        System.out.println("Response:");
        System.out.println("  Success: " + response.getSuccess());
        System.out.println("  Previous size: " + response.getPreviousSize());
        System.out.println("  New size: " + response.getNewSize());
        System.out.println("  Message: " + response.getMessage());
    }
}
