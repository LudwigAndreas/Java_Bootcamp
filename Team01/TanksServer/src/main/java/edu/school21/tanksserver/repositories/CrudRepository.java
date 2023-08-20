package edu.school21.tanksserver.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<ID, T> {
    Optional<T> findById(ID id);
    List<T> findAll();
    void save(T model);
    void update(T model);
    void delete(ID id);
}

