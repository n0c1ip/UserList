package crudDB;

import objects.Department;
import objects.Organization;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class DepartmentServiceTest {


    private static Organization organization;

    @BeforeClass
    public static void createOrganization() {
        Organization org = new Organization("TestOrgDepartmentService");
        organization = OrganizationService.add(org);
    }

    @AfterClass
    public static void deleteOrganization() {
        OrganizationService.delete(organization.getId());
    }

    @Test
    public void ShouldAddDepartment() {
        Department department = new Department("departmentToAdd", organization);

        Department addedDepartment = DepartmentService.add(department);
        Department foundDepartment = DepartmentService.get(addedDepartment.getId());

        try {
            Assert.assertNotNull(addedDepartment);
            Assert.assertNotNull(foundDepartment);
            Assert.assertEquals(addedDepartment.getId(), foundDepartment.getId());
        } finally {
            DepartmentService.delete(addedDepartment.getId());
        }
    }

    @Test
    public void ShouldDeleteDepartment(){
        Department department = new Department("departmentToDel", organization);

        Department departmentInDb = DepartmentService.add(department);
        long departmentID = departmentInDb.getId();
        DepartmentService.delete(departmentID);
        Assert.assertNull(DepartmentService.get(departmentID));
    }

    @Test
    public void ShouldUpdateDepartment(){
        String oldName = "oldDepartment";
        String newName = "newDepartment";

        Department department = new Department(oldName, organization);
        Department addedDepartment = DepartmentService.add(department);
        addedDepartment.setName(newName);
        DepartmentService.update(addedDepartment);
        try {
            Assert.assertEquals(newName,addedDepartment.getName());
        } finally {
            DepartmentService.delete(addedDepartment.getId());
        }

    }

    @Test
    public void ShouldGetByNameDepartment(){
        String departmentName = "FindThisName";
        Department department = new Department(departmentName, organization);
        department = DepartmentService.add(department);
        Department getByName = DepartmentService.getByName(departmentName);
        try{
            Assert.assertEquals(department.getName(),getByName.getName());
        } finally {
            DepartmentService.delete(department.getId());
        }
    }

    @Test
    public void ShouldGetAllDepartments(){
        int expectedDepartmentsCount = 3;
        String name1 = "departmentOne";
        String name2 = "departmentTwo";
        String name3 = "departmentThree";

        List<String> namesList = Arrays.asList(name1, name2, name3);

        Department addedDepartment1 = DepartmentService.add(new Department(name1, organization));
        Department addedDepartment2 = DepartmentService.add(new Department(name2, organization));
        Department addedDepartment3 = DepartmentService.add(new Department(name3, organization));

        int departmentFound = 0;
        List<Department> departmentList = DepartmentService.getAll();
        for (Department department : departmentList) {
            if (namesList.contains(department.getName())) {
                departmentFound++;
            }
        }

        try {
            Assert.assertEquals(expectedDepartmentsCount, departmentFound);
        } finally {
            DepartmentService.delete(addedDepartment1.getId());
            DepartmentService.delete(addedDepartment2.getId());
            DepartmentService.delete(addedDepartment3.getId());
        }
    }


}
