package cn2026lookupfunction;

import com.google.cloud.compute.v1.*;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class LookupFunction implements HttpFunction {

    private static final String PROJECT = "cn2526-t3-g07";
    private static final String ZONE = "europe-west1-b";
    private static final String MIG_NAME = "cn2026-server-mig";

    @Override
    public void service(HttpRequest request,
                        HttpResponse response) throws Exception {

        List<String> ips = new ArrayList<>();

        try (InstanceGroupManagersClient migClient =
                     InstanceGroupManagersClient.create();

             InstancesClient instancesClient =
                     InstancesClient.create()) {

            for (ManagedInstance managedInstance :
                    migClient.listManagedInstances(
                            PROJECT,
                            ZONE,
                            MIG_NAME
                    ).iterateAll()) {

                String instanceUrl =
                        managedInstance.getInstance();

                String instanceName =
                        instanceUrl.substring(
                                instanceUrl.lastIndexOf("/") + 1
                        );

                Instance vm =
                        instancesClient.get(
                                PROJECT,
                                ZONE,
                                instanceName
                        );

                if (!vm.getNetworkInterfacesList().isEmpty()) {

                    NetworkInterface networkInterface =
                            vm.getNetworkInterfacesList().get(0);

                    if (!networkInterface
                            .getAccessConfigsList()
                            .isEmpty()) {

                        AccessConfig accessConfig =
                                networkInterface
                                        .getAccessConfigsList()
                                        .get(0);

                        ips.add(accessConfig.getNatIP());
                    }
                }
            }
        }

        response.appendHeader(
                "Content-Type",
                "application/json"
        );

        BufferedWriter writer =
                response.getWriter();

        writer.write(ips.toString());
    }
}