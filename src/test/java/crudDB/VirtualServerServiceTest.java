package crudDB;

import objects.PhysicalServer;
import objects.VirtualServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class VirtualServerServiceTest {

    private static PhysicalServer physicalServer = null;

    @BeforeClass
    public static void addPhysicalServer(){
        physicalServer = new PhysicalServer();
        physicalServer = PhysicalServerService.add(physicalServer);
    }

    @AfterClass
    public static void deletePhysicalServer(){
        PhysicalServerService.delete(physicalServer.getId());
    }

    @Test
    public void ShouldAddVirtualServer() {
        VirtualServer virtualServer = new VirtualServer();
        virtualServer.setName("virtualServerToAdd");
        virtualServer.setpServer(physicalServer);

        VirtualServer addedVirtualServer = VirtualServerService.add(virtualServer);
        VirtualServer foundVirtualServer = VirtualServerService.get(addedVirtualServer.getId());

        try {
            Assert.assertNotNull(addedVirtualServer);
            Assert.assertNotNull(foundVirtualServer);
            Assert.assertEquals(addedVirtualServer.getId(), foundVirtualServer.getId());
        } finally {
            VirtualServerService.delete(addedVirtualServer.getId());
        }
    }

    @Test
    public void ShouldDeleteVirtualServer() {
        VirtualServer virtualServer = new VirtualServer();
        virtualServer.setName("virtualServerToDelete");
        virtualServer.setpServer(physicalServer);

        VirtualServer addedVirtualServer = VirtualServerService.add(virtualServer);
        long addedVirtualServerId = addedVirtualServer.getId();
        VirtualServerService.delete(addedVirtualServerId);

        Assert.assertNull(VirtualServerService.get(addedVirtualServerId));
    }

    @Test
    public void ShouldUpdateVirtualServer() {
        String oldName = "virtualServerToUpdate";
        String newName = "updatedVirtualServer";
        VirtualServer virtualServer = new VirtualServer();
        virtualServer.setpServer(physicalServer);

        virtualServer.setName(oldName);
        VirtualServer addedVirtualServer = VirtualServerService.add(virtualServer);

        addedVirtualServer.setName(newName);
        VirtualServerService.update(addedVirtualServer);
        VirtualServer foundVirtualServer = VirtualServerService.get(addedVirtualServer.getId());

        try {
            Assert.assertEquals(foundVirtualServer.getName(), newName);
        } finally {
            VirtualServerService.delete(foundVirtualServer.getId());
        }
    }

    @Test
    public void ShouldGetByNameVirtualServer() {
        VirtualServer virtualServer = new VirtualServer();
        virtualServer.setName("virtualServerToGetByName");
        virtualServer.setpServer(physicalServer);

        VirtualServer addedVirtualServer = VirtualServerService.add(virtualServer);
        VirtualServer gottenVirtualServer = VirtualServerService.getByName(addedVirtualServer.getName());

        try {
            Assert.assertNotNull(gottenVirtualServer);
            Assert.assertEquals(virtualServer.getName(), gottenVirtualServer.getName());
        } finally {
                VirtualServerService.delete(addedVirtualServer.getId());
        }
    }

    @Test
    public void ShouldGetAllVirtualServers() {
        int expectedVirtualServersCount = 3;
        String name1 = "virtualServerOne";
        String name2 = "virtualServerTwo";
        String name3 = "virtualServerThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        VirtualServer virtualServer1 = new VirtualServer();
        VirtualServer virtualServer2 = new VirtualServer();
        VirtualServer virtualServer3 = new VirtualServer();

        virtualServer1.setName(name1);
        virtualServer2.setName(name2);
        virtualServer3.setName(name3);

        virtualServer1.setpServer(physicalServer);
        virtualServer2.setpServer(physicalServer);
        virtualServer3.setpServer(physicalServer);

        VirtualServer addedVirtualServer1 = VirtualServerService.add(virtualServer1);
        VirtualServer addedVirtualServer2 = VirtualServerService.add(virtualServer2);
        VirtualServer addedVirtualServer3 = VirtualServerService.add(virtualServer3);

        int virtualServersFound = 0;
        List<VirtualServer> virtualServerList = VirtualServerService.getAll();
        for (VirtualServer virtualServer : virtualServerList) {
            if (namesList.contains(virtualServer.getName())) {
                virtualServersFound++;
            }
        }

        try {
            Assert.assertEquals(expectedVirtualServersCount, virtualServersFound);
        } finally {
            VirtualServerService.delete(addedVirtualServer1.getId());
            VirtualServerService.delete(addedVirtualServer2.getId());
            VirtualServerService.delete(addedVirtualServer3.getId());
        }
    }

}