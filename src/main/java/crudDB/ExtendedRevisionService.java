package crudDB;

import objects.ExtendedRevisionEntity;
import objects.Model;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import javax.persistence.EntityManager;
import java.util.List;

public class ExtendedRevisionService {

    public static <T extends Model> T getLastRevision(Class<T> explicitClass, T t) {
        EntityManager entityManager = EntityManagerFactory.createEntityManager();
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revisionNumbers = reader.getRevisions(explicitClass, t.getId());
        T lastRevision = reader.find(
                explicitClass,
                t.getId(),
                revisionNumbers.get(revisionNumbers.size() - 1)
        );
        entityManager.close();
        return lastRevision;
    }

    public static <T extends Model> ExtendedRevisionEntity getLastRevisionEntity(Class<T> explicitClass, T t) {
        EntityManager entityManager = EntityManagerFactory.createEntityManager();
        AuditReader reader = AuditReaderFactory.get(entityManager);
        List<Number> revisionNumbers = reader.getRevisions(explicitClass, t.getId());
        Integer lastRevisionId = revisionNumbers.get(revisionNumbers.size() - 1).intValue();
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(explicitClass, false, false);
        query.add(AuditEntity.revisionNumber().eq(lastRevisionId));
        Object[] obj = (Object[]) query.getSingleResult();
        ExtendedRevisionEntity extendedRevisionEntity = (ExtendedRevisionEntity) obj[1];
        entityManager.close();
        return extendedRevisionEntity;
    }

}
