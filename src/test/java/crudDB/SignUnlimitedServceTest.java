package crudDB;

import objects.SignUnlimited;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class SignUnlimitedServceTest {
    @Test
    public void ShouldAddSignUnlimited() {
        SignUnlimited signUnlimited = new SignUnlimited("SignUnlimitedToAdd");

        SignUnlimited addedSignUnlimited = SignUnlimitedService.add(signUnlimited);
        SignUnlimited foundSignUnlimited = SignUnlimitedService.get(addedSignUnlimited.getId());

        try {
            Assert.assertNotNull(addedSignUnlimited);
            Assert.assertNotNull(foundSignUnlimited);
            Assert.assertEquals(addedSignUnlimited.getId(), foundSignUnlimited.getId());
        } finally {
            SignUnlimitedService.delete(addedSignUnlimited.getId());
        }
    }

    @Test
    public void ShouldDeleteSignUnlimited() {
        SignUnlimited signUnlimited = new SignUnlimited("SignUnlimitedToDelete");

        SignUnlimited addedSignUnlimited = SignUnlimitedService.add(signUnlimited);
        long addedSignUnlimitedId = addedSignUnlimited.getId();
        SignUnlimitedService.delete(addedSignUnlimitedId);

        Assert.assertNull(SignUnlimitedService.get(addedSignUnlimitedId));
    }

    @Test
    public void ShouldUpdateSignUnlimited() {
        String oldName = "SignUnlimitedToUpdate";
        String newName = "updatedSignUnlimited";
        SignUnlimited signUnlimited = new SignUnlimited();

        signUnlimited.setName(oldName);
        SignUnlimited addedSignUnlimited = SignUnlimitedService.add(signUnlimited);

        addedSignUnlimited.setName(newName);
        SignUnlimitedService.update(addedSignUnlimited);
        SignUnlimited foundSignUnlimited = SignUnlimitedService.get(addedSignUnlimited.getId());

        try {
            Assert.assertEquals(foundSignUnlimited.getName(), newName);
        } finally {
            SignUnlimitedService.delete(foundSignUnlimited.getId());
        }
    }

    @Test
    public void ShouldGetByNameSignUnlimited() {
        SignUnlimited signUnlimited = new SignUnlimited("SignUnlimitedToGetByName");

        SignUnlimited addedSignUnlimited = SignUnlimitedService.add(signUnlimited);
        SignUnlimited gottenSignUnlimited = SignUnlimitedService.getByName(addedSignUnlimited.getName());

        try {
            Assert.assertNotNull(gottenSignUnlimited);
            Assert.assertEquals(signUnlimited.getName(), gottenSignUnlimited.getName());
        } finally {
            SignUnlimitedService.delete(addedSignUnlimited.getId());
        }
    }

    @Test
    public void ShouldGetAllSignUnlimiteds() {
        int expectedSignUnlimitedsCount = 3;
        String name1 = "SignUnlimitedOne";
        String name2 = "SignUnlimitedTwo";
        String name3 = "SignUnlimitedThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        SignUnlimited addedSignUnlimited1 = SignUnlimitedService.add(new SignUnlimited(name1));
        SignUnlimited addedSignUnlimited2 = SignUnlimitedService.add(new SignUnlimited(name2));
        SignUnlimited addedSignUnlimited3 = SignUnlimitedService.add(new SignUnlimited(name3));

        int SignUnlimitedsFound = 0;
        List<SignUnlimited> SignUnlimitedList = SignUnlimitedService.getAll();
        for (SignUnlimited SignUnlimited : SignUnlimitedList) {
            if (namesList.contains(SignUnlimited.getName())) {
                SignUnlimitedsFound++;
            }
        }

        try {
            Assert.assertEquals(expectedSignUnlimitedsCount, SignUnlimitedsFound);
        } finally {
            SignUnlimitedService.delete(addedSignUnlimited1.getId());
            SignUnlimitedService.delete(addedSignUnlimited2.getId());
            SignUnlimitedService.delete(addedSignUnlimited3.getId());
        }
    }
}
