package crud;


import objects.Department;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class DepartmentService {

    public EntityManager manager = Persistence.createEntityManagerFactory("UserList").createEntityManager();

    public Department add(Department department){
        manager.getTransaction().begin();
        Department departmentFromDB = manager.merge(department);
        manager.getTransaction().commit();
        return departmentFromDB;
    }

    public void delete(long id){
        manager.getTransaction().begin();
        manager.remove(get(id));
        manager.getTransaction().commit();
    }

    public Department get(long id){
        return manager.find(Department.class, id);
    }

    public void update(Department department){
        manager.getTransaction().begin();
        manager.merge(department);
        manager.getTransaction().commit();
    }

    public Department getByName(String name) {
        TypedQuery<Department> namedQuery = manager.createNamedQuery("Department.getByName", Department.class);
        namedQuery.setParameter("name", name);
        return namedQuery.getSingleResult();
    }

    public List<Department> getAll(){
        TypedQuery<Department> namedQuery = manager.createNamedQuery("Department.getAll", Department.class);
        return namedQuery.getResultList();
    }
}
