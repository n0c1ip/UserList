package interfaces;

import java.util.List;

public interface DaoInterface<T> {

    public void persist(T entity);
    public void update(T entity);
    public void delete(T entity);
    public List<T> findAll();
    public void deleteAll();

}
