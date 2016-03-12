package crudDB;

import objects.Department;
import objects.Organization;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class QueryProviderTest {

    private static Organization organization;

    @BeforeClass
    public static void createOrganization() {
        Organization org = new Organization("TestOrgQueryProvider");
        organization = OrganizationService.add(org);
    }

    @AfterClass
    public static void deleteOrganization() {
        OrganizationService.delete(organization.getId());
    }

    @Test
    public void ShouldDoQueryInTransaction() {
        Department department = new Department("departmentToTestQuery", organization);
        Department addedDepartment = doQueryInTransaction(manager -> manager.merge(department));

        try {
            Assert.assertNotNull(department);
            Assert.assertNotNull(addedDepartment);
            Assert.assertEquals(department.getName(), addedDepartment.getName());
        } finally {
            DepartmentService.delete(addedDepartment.getId());
        }
    }

    @Test
    public void ShouldDoQueryCasual() {
        Department department = new Department("departmentToTestQuery", organization);
        Department addedDepartment = DepartmentService.add(department);
        Department foundDepartment = doQueryCasual(manager -> manager.find(Department.class, addedDepartment.getId()));

        try {
            Assert.assertNotNull(addedDepartment);
            Assert.assertNotNull(foundDepartment);
            Assert.assertEquals(addedDepartment.getId(), foundDepartment.getId());
        } finally {
            DepartmentService.delete(addedDepartment.getId());
        }
    }

}