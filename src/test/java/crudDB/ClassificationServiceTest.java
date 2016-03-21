package crudDB;

import objects.Classification;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class ClassificationServiceTest {

    @Test
    public void ShouldAddClassification() {
        Classification classification = new Classification("classificationToAdd");

        Classification addedClassification = ClassificationService.add(classification);
        Classification foundClassification = ClassificationService.get(addedClassification.getId());

        try {
            Assert.assertNotNull(addedClassification);
            Assert.assertNotNull(foundClassification);
            Assert.assertEquals(addedClassification.getId(), foundClassification.getId());
        } finally {
            ClassificationService.delete(addedClassification.getId());
        }
    }

    @Test
    public void ShouldDeleteClassification() {
        Classification classification = new Classification("classificationToDelete");

        Classification addedClassification = ClassificationService.add(classification);
        long addedClassificationId = addedClassification.getId();
        ClassificationService.delete(addedClassificationId);

        Assert.assertNull(ClassificationService.get(addedClassificationId));
    }

    @Test
    public void ShouldUpdateClassification() {
        String oldName = "classificationToUpdate";
        String newName = "updatedClassification";
        Classification classification = new Classification();

        classification.setName(oldName);
        Classification addedClassification = ClassificationService.add(classification);

        addedClassification.setName(newName);
        ClassificationService.update(addedClassification);
        Classification foundClassification = ClassificationService.get(addedClassification.getId());

        try {
            Assert.assertEquals(foundClassification.getName(), newName);
        } finally {
            ClassificationService.delete(foundClassification.getId());
        }
    }

    @Test
    public void ShouldGetByNameClassification() {
        Classification classification = new Classification("classificationToGetByName");

        Classification addedClassification = ClassificationService.add(classification);
        Classification gottenClassification = ClassificationService.getByName(addedClassification.getName());

        try {
            Assert.assertNotNull(gottenClassification);
            Assert.assertEquals(classification.getName(), gottenClassification.getName());
        } finally {
                ClassificationService.delete(addedClassification.getId());
        }
    }

    @Test
    public void ShouldGetAllClassifications() {
        int expectedClassificationsCount = 3;
        String name1 = "classificationOne";
        String name2 = "classificationTwo";
        String name3 = "classificationThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        Classification addedClassification1 = ClassificationService.add(new Classification(name1));
        Classification addedClassification2 = ClassificationService.add(new Classification(name2));
        Classification addedClassification3 = ClassificationService.add(new Classification(name3));

        int classificationsFound = 0;
        List<Classification> classificationList = ClassificationService.getAll();
        for (Classification classification : classificationList) {
            if (namesList.contains(classification.getName())) {
                classificationsFound++;
            }
        }

        try {
            Assert.assertEquals(expectedClassificationsCount, classificationsFound);
        } finally {
            ClassificationService.delete(addedClassification1.getId());
            ClassificationService.delete(addedClassification2.getId());
            ClassificationService.delete(addedClassification3.getId());
        }
    }

}