package crudDB;

import objects.PhysicalServer;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class PhysicalServerServiceTest {

    @Test
    public void ShouldAddPhysicalServer() {
        PhysicalServer physicalServer = new PhysicalServer();
        physicalServer.setName("physicalServerToAdd");

        PhysicalServer addedPhysicalServer = PhysicalServerService.add(physicalServer);
        PhysicalServer foundPhysicalServer = PhysicalServerService.get(addedPhysicalServer.getId());

        try {
            Assert.assertNotNull(addedPhysicalServer);
            Assert.assertNotNull(foundPhysicalServer);
            Assert.assertEquals(addedPhysicalServer.getId(), foundPhysicalServer.getId());
        } finally {
            PhysicalServerService.delete(addedPhysicalServer.getId());
        }
    }

    @Test
    public void ShouldDeletePhysicalServer() {
        PhysicalServer physicalServer = new PhysicalServer();
        physicalServer.setName("physicalServerToDelete");

        PhysicalServer addedPhysicalServer = PhysicalServerService.add(physicalServer);
        long addedPhysicalServerId = addedPhysicalServer.getId();
        PhysicalServerService.delete(addedPhysicalServerId);

        Assert.assertNull(PhysicalServerService.get(addedPhysicalServerId));
    }

    @Test
    public void ShouldUpdatePhysicalServer() {
        String oldName = "physicalServerToUpdate";
        String newName = "updatedPhysicalServer";
        PhysicalServer physicalServer = new PhysicalServer();

        physicalServer.setName(oldName);
        PhysicalServer addedPhysicalServer = PhysicalServerService.add(physicalServer);

        addedPhysicalServer.setName(newName);
        PhysicalServerService.update(addedPhysicalServer);
        PhysicalServer foundPhysicalServer = PhysicalServerService.get(addedPhysicalServer.getId());

        try {
            Assert.assertEquals(foundPhysicalServer.getName(), newName);
        } finally {
            PhysicalServerService.delete(foundPhysicalServer.getId());
        }
    }

    @Test
    public void ShouldGetByNamePhysicalServer() {
        PhysicalServer physicalServer = new PhysicalServer();
        physicalServer.setName("physicalServerToGetByName");

        PhysicalServer addedPhysicalServer = PhysicalServerService.add(physicalServer);
        PhysicalServer gottenPhysicalServer = PhysicalServerService.getByName(addedPhysicalServer.getName());

        try {
            Assert.assertNotNull(gottenPhysicalServer);
            Assert.assertEquals(physicalServer.getName(), gottenPhysicalServer.getName());
        } finally {
                PhysicalServerService.delete(addedPhysicalServer.getId());
        }
    }

    @Test
    public void ShouldGetAllPhysicalServers() {
        int expectedPhysicalServersCount = 3;
        String name1 = "physicalServerOne";
        String name2 = "physicalServerTwo";
        String name3 = "physicalServerThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        PhysicalServer physicalServer1 = new PhysicalServer();
        PhysicalServer physicalServer2 = new PhysicalServer();
        PhysicalServer physicalServer3 = new PhysicalServer();

        physicalServer1.setName(name1);
        physicalServer2.setName(name2);
        physicalServer3.setName(name3);

        PhysicalServer addedPhysicalServer1 = PhysicalServerService.add(physicalServer1);
        PhysicalServer addedPhysicalServer2 = PhysicalServerService.add(physicalServer2);
        PhysicalServer addedPhysicalServer3 = PhysicalServerService.add(physicalServer3);

        int physicalServersFound = 0;
        List<PhysicalServer> physicalServerList = PhysicalServerService.getAll();
        for (PhysicalServer physicalServer : physicalServerList) {
            if (namesList.contains(physicalServer.getName())) {
                physicalServersFound++;
            }
        }

        try {
            Assert.assertEquals(expectedPhysicalServersCount, physicalServersFound);
        } finally {
            PhysicalServerService.delete(addedPhysicalServer1.getId());
            PhysicalServerService.delete(addedPhysicalServer2.getId());
            PhysicalServerService.delete(addedPhysicalServer3.getId());
        }
    }

}