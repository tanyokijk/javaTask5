package com.aimiko.task5.persistence.repository;

import com.aimiko.task5.persistence.entity.Entity;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public interface Repository<E extends Entity> {

    Optional<E> findById(UUID id);

    Set<E> findAll();

    Set<E> findBy(Predicate<E> filter);

    E add(E entity);

    boolean remove(E entity);
}