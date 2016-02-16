package crudDB;

import objects.Organization;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class OrganizationServceTest {
    @Test
    public void ShouldAddOrganization() {
        Organization organization = new Organization("OrganizationToAdd");

        Organization addedOrganization = OrganizationService.add(organization);
        Organization foundOrganization = OrganizationService.get(addedOrganization.getId());

        try {
            Assert.assertNotNull(addedOrganization);
            Assert.assertNotNull(foundOrganization);
            Assert.assertEquals(addedOrganization.getId(), foundOrganization.getId());
        } finally {
            OrganizationService.delete(addedOrganization.getId());
        }
    }

    @Test
    public void ShouldDeleteOrganization() {
        Organization organization = new Organization("organizationToDelete");

        Organization addedOrganization = OrganizationService.add(organization);
        long addedOrganizationId = addedOrganization.getId();
        OrganizationService.delete(addedOrganizationId);

        Assert.assertNull(OrganizationService.get(addedOrganizationId));
    }

    @Test
    public void ShouldUpdateOrganization() {
        String oldName = "organizationToUpdate";
        String newName = "updatedOrganization";
        Organization organization = new Organization();

        organization.setName(oldName);
        Organization addedOrganization = OrganizationService.add(organization);

        addedOrganization.setName(newName);
        OrganizationService.update(addedOrganization);
        Organization foundOrganization = OrganizationService.get(addedOrganization.getId());

        try {
            Assert.assertEquals(foundOrganization.getName(), newName);
        } finally {
            OrganizationService.delete(foundOrganization.getId());
        }
    }

    @Test
    public void ShouldGetByNameOrganization() {
        Organization organization = new Organization("organizationToGetByName");

        Organization addedOrganization = OrganizationService.add(organization);
        Organization gottenOrganization = OrganizationService.getByName(addedOrganization.getName());

        try {
            Assert.assertNotNull(gottenOrganization);
            Assert.assertEquals(organization.getName(), gottenOrganization.getName());
        } finally {
            OrganizationService.delete(addedOrganization.getId());
        }
    }

    @Test
    public void ShouldGetAllOrganizations() {
        int expectedOrganizationsCount = 3;
        String name1 = "organizationOne";
        String name2 = "organizationTwo";
        String name3 = "organizationThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        Organization addedOrganization1 = OrganizationService.add(new Organization(name1));
        Organization addedOrganization2 = OrganizationService.add(new Organization(name2));
        Organization addedOrganization3 = OrganizationService.add(new Organization(name3));

        int organizationsFound = 0;
        List<Organization> organizationList = OrganizationService.getAll();
        for (Organization organization : organizationList) {
            if (namesList.contains(organization.getName())) {
                organizationsFound++;
            }
        }

        try {
            Assert.assertEquals(expectedOrganizationsCount, organizationsFound);
        } finally {
            OrganizationService.delete(addedOrganization1.getId());
            OrganizationService.delete(addedOrganization2.getId());
            OrganizationService.delete(addedOrganization3.getId());
        }
    }
}
