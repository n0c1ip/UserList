package crudDB;

import objects.Organization;

import javax.persistence.TypedQuery;
import java.util.List;

import static crudDB.QueryProvider.doQueryCasual;
import static crudDB.QueryProvider.doQueryInTransaction;

public class OrganizationService{

    public static Organization add(Organization organization){
        return doQueryInTransaction(manager -> manager.merge(organization));
    }

    public static void delete(long id){
        doQueryInTransaction(manager -> {
            manager.remove(manager.find(Organization.class, id));
            return null;
        });
    }

    public static Organization get(long id){
        return doQueryCasual(manager -> manager.find(Organization.class, id));
    }

    public static void update(Organization organization){
        doQueryInTransaction(manager -> {
            manager.merge(organization);
            return null;
        });
    }

    public static Organization getByName(String name) {
        return doQueryCasual(manager -> {
            TypedQuery<Organization> namedQuery = manager.createNamedQuery("Organization.getByName", Organization.class);
            namedQuery.setParameter("name", name);
            return namedQuery.getSingleResult();
        });
    }

    public static List<Organization> getAll() {
        return doQueryCasual(manager -> {
            TypedQuery<Organization> namedQuery = manager.createNamedQuery("Organization.getAll", Organization.class);
            return namedQuery.getResultList();
        });
    }

}

