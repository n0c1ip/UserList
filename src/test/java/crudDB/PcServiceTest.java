package crudDB;

import objects.Pc;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class PcServiceTest {

    @Test
    public void ShouldAddPc() {
        Pc pc = new Pc("pcToAdd");

        Pc addedPc = PcService.add(pc);
        Pc foundPc = PcService.get(addedPc.getId());

        try {
            Assert.assertNotNull(addedPc);
            Assert.assertNotNull(foundPc);
            Assert.assertEquals(addedPc.getId(), foundPc.getId());
        } finally {
            PcService.delete(addedPc.getId());
        }
    }

    @Test
    public void ShouldDeletePc() {
        Pc pc = new Pc("pcToDelete");

        Pc addedPc = PcService.add(pc);
        long addedPcId = addedPc.getId();
        PcService.delete(addedPcId);

        Assert.assertNull(PcService.get(addedPcId));
    }

    @Test
    public void ShouldUpdatePc() {
        String oldName = "pcToUpdate";
        String newName = "updatedPc";
        Pc pc = new Pc();

        pc.setName(oldName);
        Pc addedPc = PcService.add(pc);

        addedPc.setName(newName);
        PcService.update(addedPc);
        Pc foundPc = PcService.get(addedPc.getId());

        try {
            Assert.assertEquals(foundPc.getName(), newName);
        } finally {
            PcService.delete(foundPc.getId());
        }
    }

    @Test
    public void ShouldGetByNamePc() {
        Pc pc = new Pc("pcToGetByName");

        Pc addedPc = PcService.add(pc);
        Pc gottenPc = PcService.getByName(addedPc.getName());

        try {
            Assert.assertNotNull(gottenPc);
            Assert.assertEquals(pc.getName(), gottenPc.getName());
        } finally {
                PcService.delete(addedPc.getId());
        }
    }

    @Test
    public void ShouldGetAllPcs() {
        int expectedPcsCount = 3;
        String name1 = "pcOne";
        String name2 = "pcTwo";
        String name3 = "pcThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        Pc addedPc1 = PcService.add(new Pc(name1));
        Pc addedPc2 = PcService.add(new Pc(name2));
        Pc addedPc3 = PcService.add(new Pc(name3));

        int pcsFound = 0;
        List<Pc> PcList = PcService.getAll();
        for (Pc pc : PcList) {
            if (namesList.contains(pc.getName())) {
                pcsFound++;
            }
        }

        try {
            Assert.assertEquals(expectedPcsCount, pcsFound);
        } finally {
            PcService.delete(addedPc1.getId());
            PcService.delete(addedPc2.getId());
            PcService.delete(addedPc3.getId());
        }
    }

}