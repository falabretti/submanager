package io.submanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class AbstractService<T, ID, R extends JpaRepository<T, ID>> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected R repository;

    public List<T> getAll() {
        return repository.findAll();
    }

    @Transactional
    public T create(T entity) {
        T savedEntity = repository.save(entity);
        entityManager.refresh(savedEntity);
        return savedEntity;
    }
}
