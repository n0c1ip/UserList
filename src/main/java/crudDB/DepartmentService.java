package crudDB;


import objects.Department;
import objects.Organization;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class DepartmentService {

    public static Department add(Department department){
        return doQueryInTransaction(manager -> manager.merge(department));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(Department.class, id));
            return null;
        });
    }

    public static Department get(long id){
        return doQueryCasual(manager -> manager.find(Department.class, id));
    }

    public static void update(Department department){
        doQueryInTransaction(manager -> {
            manager.merge(department);
            return null;
        });
    }

    public static Department getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<Department> namedQuery = manager.createNamedQuery("Department.getByName", Department.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<Department> getByOrganization(Organization organization){
        return doQueryCasual(manager -> {
            TypedQuery<Department> namedQuery = manager.createNamedQuery("Department.getByOrganization", Department.class);
            namedQuery.setParameter("organization", organization);
            return  namedQuery.getResultList();
        });
    }

    public static List<Department> getAll(){
        return doQueryCasual(manager -> {
            TypedQuery<Department> namedQuery = manager.createNamedQuery("Department.getAll", Department.class);
            return namedQuery.getResultList();
        });
    }
}
