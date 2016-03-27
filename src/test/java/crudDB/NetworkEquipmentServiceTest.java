package crudDB;

import objects.NetworkEquipment;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class NetworkEquipmentServiceTest {

    @Test
    public void ShouldAddNetworkEquipment() {
        NetworkEquipment networkEquipment = new NetworkEquipment("networkEquipmentToAdd");

        NetworkEquipment addedNetworkEquipment = NetworkEquipmentService.add(networkEquipment);
        NetworkEquipment foundNetworkEquipment = NetworkEquipmentService.get(addedNetworkEquipment.getId());

        try {
            Assert.assertNotNull(addedNetworkEquipment);
            Assert.assertNotNull(foundNetworkEquipment);
            Assert.assertEquals(addedNetworkEquipment.getId(), foundNetworkEquipment.getId());
        } finally {
            NetworkEquipmentService.delete(addedNetworkEquipment.getId());
        }
    }

    @Test
    public void ShouldDeleteNetworkEquipment() {
        NetworkEquipment networkEquipment = new NetworkEquipment("networkEquipmentToDelete");

        NetworkEquipment addedNetworkEquipment = NetworkEquipmentService.add(networkEquipment);
        long addedNetworkEquipmentId = addedNetworkEquipment.getId();
        NetworkEquipmentService.delete(addedNetworkEquipmentId);

        Assert.assertNull(NetworkEquipmentService.get(addedNetworkEquipmentId));
    }

    @Test
    public void ShouldUpdateNetworkEquipment() {
        String oldName = "networkEquipmentToUpdate";
        String newName = "updatedNetworkEquipment";
        NetworkEquipment networkEquipment = new NetworkEquipment();

        networkEquipment.setName(oldName);
        NetworkEquipment addedNetworkEquipment = NetworkEquipmentService.add(networkEquipment);

        addedNetworkEquipment.setName(newName);
        NetworkEquipmentService.update(addedNetworkEquipment);
        NetworkEquipment foundNetworkEquipment = NetworkEquipmentService.get(addedNetworkEquipment.getId());

        try {
            Assert.assertEquals(foundNetworkEquipment.getName(), newName);
        } finally {
            NetworkEquipmentService.delete(foundNetworkEquipment.getId());
        }
    }

    @Test
    public void ShouldGetAllNetworkEquipments() {
        int expectedNetworkEquipmentsCount = 3;
        String name1 = "networkEquipmentOne";
        String name2 = "networkEquipmentTwo";
        String name3 = "networkEquipmentThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        NetworkEquipment addedNetworkEquipment1 = NetworkEquipmentService.add(new NetworkEquipment(name1));
        NetworkEquipment addedNetworkEquipment2 = NetworkEquipmentService.add(new NetworkEquipment(name2));
        NetworkEquipment addedNetworkEquipment3 = NetworkEquipmentService.add(new NetworkEquipment(name3));

        int networkEquipmentsFound = 0;
        List<NetworkEquipment> networkEquipmentList = NetworkEquipmentService.getAll();
        for (NetworkEquipment networkEquipment : networkEquipmentList) {
            if (namesList.contains(networkEquipment.getName())) {
                networkEquipmentsFound++;
            }
        }

        try {
            Assert.assertEquals(expectedNetworkEquipmentsCount, networkEquipmentsFound);
        } finally {
            NetworkEquipmentService.delete(addedNetworkEquipment1.getId());
            NetworkEquipmentService.delete(addedNetworkEquipment2.getId());
            NetworkEquipmentService.delete(addedNetworkEquipment3.getId());
        }
    }

}