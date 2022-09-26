package io.submanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class AbstractService<T, ID, R extends JpaRepository<T, ID>> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    protected R repository;

    public List<T> getAll() {
        return repository.findAll();
    }

    public Optional<T> get(ID id) {
        return repository.findById(id);
    }

    @Transactional
    public T create(T entity) {
        T savedEntity = repository.save(entity);
        entityManager.refresh(savedEntity);
        return savedEntity;
    }

    @Transactional
    public T update(T entity) {
        T updatedEntity = repository.saveAndFlush(entity);
//        entityManager.refresh(updatedEntity);
        return updatedEntity;
    }
}
