package crud;

import objects.Department;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;

import static crud.QueryProvider.doQueryCasual;
import static crud.QueryProvider.doQueryInTransaction;

public class QueryProviderTest {

    @Test
    public void ShouldDoQueryInTransaction() {
        Department department = new Department("departmentToTestQuery");
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
        Department department = new Department("departmentToTestQuery");
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