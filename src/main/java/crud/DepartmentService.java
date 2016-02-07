package crud;


import objects.Department;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentService {

    public static Department add(Department department){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        Department departmentFromDB = manager.merge(department);
        manager.getTransaction().commit();
        manager.close();
        return departmentFromDB;
    }

    public void delete(long id){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.find(Department.class,id));
        manager.getTransaction().commit();
        manager.close();
    }

    public Department get(long id){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        Department foundDepartment = manager.find(Department.class, id);
        manager.close();
        return foundDepartment;
    }

    public void update(Department department){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        manager.getTransaction().begin();
        manager.merge(department);
        manager.getTransaction().commit();
        manager.close();
    }

    public Department getByName(String name) {
        EntityManager manager = EntityManagerFactory.createEntityManager();
        TypedQuery<Department> namedQuery = manager.createNamedQuery("Department.getByName", Department.class);
        namedQuery.setParameter("name", name);
        Department department = namedQuery.getSingleResult();
        manager.close();
        return department;
    }

    public List<Department> getAll(){
        EntityManager manager = EntityManagerFactory.createEntityManager();
        TypedQuery<Department> namedQuery = manager.createNamedQuery("Department.getAll", Department.class);
        List<Department> resultList = namedQuery.getResultList();
        return resultList;
    }
}
