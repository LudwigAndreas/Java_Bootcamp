package edu.school21.orm.manager;

public interface Repository <T> {
    public void save(Object entity);

    public void update(Object entity);

    public <T> T findById(Long id, Class<T> aClass);
}
