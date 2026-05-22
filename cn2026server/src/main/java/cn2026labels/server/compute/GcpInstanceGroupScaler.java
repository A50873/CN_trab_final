package cn2026labels.server.compute;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.compute.v1.*;

import java.io.IOException;

/**
 * Utility class to scale Compute Engine Managed Instance Groups (MIGs).
 * Provides methods to increase/decrease VMs in a MIG.
 */
public class GcpInstanceGroupScaler {
    private final String projectId;
    private final String zone;
    private final String instanceGroupName;
    private final InstanceGroupManagersClient managerClient;

    public GcpInstanceGroupScaler(String projectId, String zone, String instanceGroupName) throws IOException {
        this.projectId = projectId;
        this.zone = zone;
        this.instanceGroupName = instanceGroupName;
        this.managerClient = InstanceGroupManagersClient.create();
    }

    /**
     * Get the current size of the managed instance group
     */
    public int getCurrentSize() throws Exception {
        try {
            InstanceGroupManager igm = managerClient.get(projectId, zone, instanceGroupName);
            Integer targetSize = igm.getTargetSize();
            return targetSize != null ? targetSize : 0;
        } catch (Exception e) {
            System.err.println("Failed to get current size: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Resize the managed instance group to a new size
     */
    public void resize(int newSize) throws Exception {
        if (newSize < 0) {
            throw new IllegalArgumentException("New size must be >= 0");
        }

        try {
            ResizeInstanceGroupManagerRequest request = ResizeInstanceGroupManagerRequest.newBuilder()
                    .setProject(projectId)
                    .setZone(zone)
                    .setInstanceGroupManager(instanceGroupName)
                    .setSize(newSize)
                    .build();

            Operation operation = managerClient.resizeAsync(request).get();
            System.out.println("Resize operation: " + operation.getName() + " - Status: " + operation.getStatus());
        } catch (Exception e) {
            System.err.println("Failed to resize instance group: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Close the client resources
     */
    public void close() {
        if (managerClient != null) {
            managerClient.close();
        }
    }
}

