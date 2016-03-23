package crudDB;

import objects.Vlan;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class VlanServiceTest {

    @Test
    public void ShouldAddVlan() {
        Vlan vlan = new Vlan("123");

        Vlan addedVlan = VlanService.add(vlan);
        Vlan foundVlan = VlanService.get(addedVlan.getId());

        try {
            Assert.assertNotNull(addedVlan);
            Assert.assertNotNull(foundVlan);
            Assert.assertEquals(addedVlan.getId(), foundVlan.getId());
        } finally {
            VlanService.delete(addedVlan.getId());
        }
    }

    @Test
    public void ShouldDeleteVlan() {
        Vlan vlan = new Vlan("123");

        Vlan addedVlan = VlanService.add(vlan);
        long addedVlanId = addedVlan.getId();
        VlanService.delete(addedVlanId);

        Assert.assertNull(VlanService.get(addedVlanId));
    }

    @Test
    public void ShouldUpdateVlan() {
        String oldNumber = "123";
        String newNumber = "1234";
        Vlan vlan = new Vlan();

        vlan.setNumber(oldNumber);
        Vlan addedVlan = VlanService.add(vlan);

        addedVlan.setNumber(newNumber);
        VlanService.update(addedVlan);
        Vlan foundVlan = VlanService.get(addedVlan.getId());

        try {
            Assert.assertEquals(foundVlan.getNumber(), newNumber);
        } finally {
            VlanService.delete(foundVlan.getId());
        }
    }

    @Test
    public void ShouldGetAllVlans() {
        int expectedVlansCount = 3;
        String number1 = "12";
        String number2 = "123";
        String number3 = "1234";

        List<String> numbersList = Arrays.asList(number1, number2, number3);

        Vlan addedVlan1 = VlanService.add(new Vlan(number1));
        Vlan addedVlan2 = VlanService.add(new Vlan(number2));
        Vlan addedVlan3 = VlanService.add(new Vlan(number3));

        int vlansFound = 0;
        List<Vlan> vlanList = VlanService.getAll();
        for (Vlan vlan : vlanList) {
            if (numbersList.contains(vlan.getNumber())) {
                vlansFound++;
            }
        }

        try {
            Assert.assertEquals(expectedVlansCount, vlansFound);
        } finally {
            VlanService.delete(addedVlan1.getId());
            VlanService.delete(addedVlan2.getId());
            VlanService.delete(addedVlan3.getId());
        }
    }


}