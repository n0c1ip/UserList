package crudDB;

import objects.ExtendedRevisionEntity;
import org.hibernate.envers.RevisionListener;

public class ExtendedRevisionListener implements RevisionListener {

    public void newRevision(Object revisionEntity) {
        ExtendedRevisionEntity extendedRevisionEntity = (ExtendedRevisionEntity) revisionEntity;
        extendedRevisionEntity.setUserName(System.getProperty("user.name"));
    }

}