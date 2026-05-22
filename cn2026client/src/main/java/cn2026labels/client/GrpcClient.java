package cn2026labels.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import servicestubs.*;
import cn2026labels.client.handlers.*;
import java.util.Scanner;


public class GrpcClient {

    private static String svcIP = "localhost";
    private static int svcPort = 8000;

    private static SFServiceGrpc.SFServiceStub sfNoBlockStub;
    private static SFServiceGrpc.SFServiceBlockingStub sfBlockingStub;

    private static SGServiceGrpc.SGServiceStub sgNoBlockStub;
    private static SGServiceGrpc.SGServiceBlockingStub sgBlockingStub;

    // Start the CLI client, create the channel/stubs, and route the user through the menus.
    public static void main(String[] args) throws InterruptedException{
        Scanner scan = new Scanner(System.in);

        try{
            if(args.length == 2){
                svcIP = args[0];
                svcPort = Integer.parseInt(args[1]);
            }

            System.out.println("connect to " + svcIP + ":" + svcPort);

            ManagedChannel channel = ManagedChannelBuilder.forAddress(svcIP, svcPort)
                    .usePlaintext().build();


            sfBlockingStub = SFServiceGrpc.newBlockingStub(channel);

            sfNoBlockStub = SFServiceGrpc.newStub(channel);

            sgBlockingStub = SGServiceGrpc.newBlockingStub(channel);

            sgNoBlockStub = SGServiceGrpc.newStub(channel);

            // The main menu owns the application loop and dispatches to the SF/SG submenus.
            while (true){

                try{
                    int option = MainMenu(scan);

                    switch (option){
                        case 1:
                            runSFMenu(scan);
                            break;

                        case 2:
                            runSGMenu(scan);
                            break;
                        case 99:
                            System.exit(0);
                    }

                }catch (Exception e){
                    System.out.println("Execution call Error!");
                    e.printStackTrace();

                }
            }
        } catch (Exception e){
            System.out.println("Unhandled exception");
            e.printStackTrace();

        }
    }

    // Show the top-level menu that lets the user choose between SF and SG operations.
    private static int MainMenu(Scanner scanner){
        int op;

        do {
            System.out.println();
            System.out.println("MENU");
            System.out.println("1 - SF operations");
            System.out.println("2 - SG operations");
            System.out.println("99 - Exit");

            op = scanner.nextInt();

        }while (! ((op >= 1 && op <= 2) || op == 99));

        return op;
    }

    // Show the submenu for functional operations exposed by SFService.
    private static int SFMenu(Scanner scanner){

        int op;

        do{
            System.out.println();
            System.out.println("SF MENU");
            System.out.println("1 - submit image");
            System.out.println("2 - get image data");
            System.out.println("3 - search images");
            System.out.println("4 - list images");
            System.out.println("5 - download image");
            System.out.println("0 - back");

            op = scanner.nextInt();

        }while (! ((op >= 1 && op <= 5) || op == 0));

        return op;

    }

    // Show the submenu for elasticity operations exposed by SGService.
    private static int SGMenu(Scanner scanner){

        int op;
        do{
            System.out.println();
            System.out.println("SG MENU");
            System.out.println("1 - increase gRPC servers");
            System.out.println("2 - decrease gRPC servers");
            System.out.println("3 - increase processing instances");
            System.out.println("4 - decrease processing instances");
            System.out.println("0 - back");

            op = scanner.nextInt();

        }while (! ((op >= 1 && op <= 4) || op == 0));

        return op;
    }

    // Keep the user inside the SF submenu until they choose to go back.
    private static void runSFMenu(Scanner scanner){

        // Returning from this loop brings the user back to the main menu only.
        while (true){

            int op = SFMenu(scanner);

            switch (op){

                case 1:
                    handleSubmitImage(scanner);
                    break;

                case 2:
                    handleGetImageData(scanner);
                    break;

                case 3:
                    handleSearchImages(scanner);
                    break;

                case 4:
                    handleListImages();
                    break;

                case 5:
                    handleDownloadImage(scanner);
                    break;

                case 0:
                    return;
            }
        }
    }

    // Keep the user inside the SG submenu until they choose to go back.
    private static void runSGMenu(Scanner scanner){

        // Returning from this loop brings the user back to the main menu only.
        while (true){

            int op = SGMenu(scanner);

            switch (op){

                case 1:
                    handleIncreaseGrpcServers(scanner);
                    break;

                case 2:
                    handleDecreaseGrpcServers(scanner);
                    break;

                case 3:
                    handleIncreaseProcessInstances(scanner);
                    break;

                case 4:
                    handleDecreaseProcessInstances(scanner);
                    break;

                case 0:
                    return;
            }
        }
    }

    // Ask for an image path and submit that image to the server as a streamed upload.
    private static void handleSubmitImage(Scanner scanner){
        SubmitImageHandler.handle(scanner, sfNoBlockStub);
    }

    // Ask for a request identifier and fetch the processed data for that image.
    private static void handleGetImageData(Scanner scanner){
        GetImageDataHandler.handle(scanner, sfBlockingStub);
    }

    // Ask for a date range and label, then search for matching images.
    private static void handleSearchImages(Scanner scanner){
        SearchImagesHandler.handle(scanner, sfBlockingStub);
    }

    // List all images in the bucket.
    private static void handleListImages(){
        ListImagesHandler.handle(sfBlockingStub);
    }

    // Ask for blob name, then download it to the Downloads folder.
    private static void handleDownloadImage(Scanner scanner){
        DownloadImageHandler.handle(scanner, sfNoBlockStub);
    }

    // Ask for the desired scale change and request more gRPC server instances.
    private static void handleIncreaseGrpcServers(Scanner scanner){
        IncreaseGrpcServersHandler.handle(scanner, sgBlockingStub);
    }

    // Ask for the desired scale change and request fewer gRPC server instances.
    private static void handleDecreaseGrpcServers(Scanner scanner){
        DecreaseGrpcServersHandler.handle(scanner, sgBlockingStub);
    }

    // Ask for the desired scale change and request more processing instances.
    private static void handleIncreaseProcessInstances(Scanner scanner){
        IncreaseProcessInstancesHandler.handle(scanner, sgBlockingStub);
    }

    // Ask for the desired scale change and request fewer processing instances.
    private static void handleDecreaseProcessInstances(Scanner scanner){
        DecreaseProcessInstancesHandler.handle(scanner, sgBlockingStub);
    }
}
