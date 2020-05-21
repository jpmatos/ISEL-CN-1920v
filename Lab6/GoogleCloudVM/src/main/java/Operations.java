import com.google.api.services.compute.Compute;
import com.google.api.services.compute.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Operations {
    private final Compute compute;

    public Operations(Compute computeService) {
        this.compute = computeService;
    }

    public void listVms(String projectID, String zoneName) throws IOException {
        Compute.Instances.List instances = compute.instances().list(projectID, zoneName);
        InstanceList list = instances.execute();
        if (list.getItems() != null) {
            for (Instance instance : list.getItems()) {
                //System.out.println(instance.toPrettyString());
                System.out.println(instance.getName());
            }
        }
    }

    public Operation createNewInstance(String instanceName, String projectID, String zoneName, String machineType) throws IOException {
        Instance instance = new Instance();
        instance.setName(instanceName);
        instance.setMachineType(
                "https://www.googleapis.com/compute/v1/projects/"
                        + projectID + "/zones/"
                        + zoneName + "/machineTypes/"
                        + machineType);
        // Add Network Interface to be used by VM Instance.
        instance.setNetworkInterfaces(
                Collections.singletonList(createNetworkInterface(projectID)));
        // Add attached Persistent Disk to be used by VM Instance.
        instance.setDisks(Collections.singletonList(createDisk(instanceName, projectID, zoneName)));
        Compute.Instances.Insert insert =
                compute.instances().insert(projectID, zoneName, instance);
        Operation op = insert.execute();
        return op;
    }

    public Operation deleteVM(String projectID, String zoneName, String instanceName) throws IOException {
        Compute.Instances.Delete delete =
                compute.instances().delete(projectID, zoneName, instanceName);
        return delete.execute();
    }

    public void listGroupManagers(String projectID, String zoneName) throws IOException {
        Compute.InstanceGroupManagers.List request =
                compute.instanceGroupManagers().list(projectID, zoneName);
        InstanceGroupManagerList list = request.execute();
        for (InstanceGroupManager grp : list.getItems()) {
            System.out.println("Group: " + grp.getName());
            System.out.println("\tBase instance name: " + grp.getInstanceTemplate());
            System.out.println("\tTarget size: " + grp.getTargetSize());
        }
    }

    public Operation resizeInstanceGroup(String projectID, String zoneName, String instanceGroup, int newSize) throws IOException {
        Compute.InstanceGroupManagers.Resize request = compute
                .instanceGroupManagers()
                .resize(projectID, zoneName, instanceGroup, newSize);
        return request.execute();
    }

    private NetworkInterface createNetworkInterface(String projectID)
    {
        NetworkInterface ifc = new NetworkInterface();
        ifc.setNetwork(
                "https://www.googleapis.com/compute/v1/projects/"
                        + projectID
                        + "/global/networks/default");
        List<AccessConfig> configs = new ArrayList<AccessConfig>();
        AccessConfig config = new AccessConfig();
        config.setType("ONE_TO_ONE_NAT");
        config.setName("External NAT");
        configs.add(config);
        ifc.setAccessConfigs(configs);
        return ifc;
    }

    private AttachedDisk createDisk(String instanceName, String projectID, String zoneName) {
        AttachedDisk disk = new AttachedDisk();
        disk.setBoot(true);
        disk.setAutoDelete(true);
        disk.setType("PERSISTENT");
        AttachedDiskInitializeParams params = new AttachedDiskInitializeParams();
        // Assign the Persistent Disk the same name as the VM Instance.
        params.setDiskName(instanceName);
        // more info
        // https://cloud.google.com/compute/docs/reference/rest/v1/disks/insert
        params.setSourceImage("projects/centos-cloud/global/images/family/centos-7");
        // Specify the disk type as Standard Persistent Disk
        params.setDiskType("https://www.googleapis.com/compute/v1/projects/"
                + projectID + "/zones/"
                + zoneName + "/diskTypes/pd-standard");
        disk.setInitializeParams(params);
        return disk;
    }
}
