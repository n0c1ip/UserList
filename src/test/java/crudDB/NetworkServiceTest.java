package crudDB;

import objects.Network;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class NetworkServiceTest {

    @Test
    public void ShouldAddNetwork() {
        Network network = new Network("networkToAdd");

        Network addedNetwork = NetworkService.add(network);
        Network foundNetwork = NetworkService.get(addedNetwork.getId());

        try {
            Assert.assertNotNull(addedNetwork);
            Assert.assertNotNull(foundNetwork);
            Assert.assertEquals(addedNetwork.getId(), foundNetwork.getId());
        } finally {
            NetworkService.delete(addedNetwork.getId());
        }
    }

    @Test
    public void ShouldDeleteNetwork() {
        Network network = new Network("networkToDelete");

        Network addedNetwork = NetworkService.add(network);
        long addedNetworkId = addedNetwork.getId();
        NetworkService.delete(addedNetworkId);

        Assert.assertNull(NetworkService.get(addedNetworkId));
    }

    @Test
    public void ShouldUpdateNetwork() {
        String oldName = "networkToUpdate";
        String newName = "updatedNetwork";
        Network network = new Network();

        network.setNetwork(oldName);
        Network addedNetwork = NetworkService.add(network);

        addedNetwork.setNetwork(newName);
        NetworkService.update(addedNetwork);
        Network foundNetwork = NetworkService.get(addedNetwork.getId());

        try {
            Assert.assertEquals(foundNetwork.getNetwork(), newName);
        } finally {
            NetworkService.delete(foundNetwork.getId());
        }
    }

    @Test
    public void ShouldGetAllNetworks() {
        int expectedNetworksCount = 3;
        String name1 = "networkOne";
        String name2 = "networkTwo";
        String name3 = "networkThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        Network addedNetwork1 = NetworkService.add(new Network(name1));
        Network addedNetwork2 = NetworkService.add(new Network(name2));
        Network addedNetwork3 = NetworkService.add(new Network(name3));

        int networksFound = 0;
        List<Network> networkList = NetworkService.getAll();
        for (Network network : networkList) {
            if (namesList.contains(network.getNetwork())) {
                networksFound++;
            }
        }

        try {
            Assert.assertEquals(expectedNetworksCount, networksFound);
        } finally {
            NetworkService.delete(addedNetwork1.getId());
            NetworkService.delete(addedNetwork2.getId());
            NetworkService.delete(addedNetwork3.getId());
        }
    }

}