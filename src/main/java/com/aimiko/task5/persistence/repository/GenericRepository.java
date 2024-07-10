package com.aimiko.task5.persistence.repository;

import com.aimiko.task5.persistence.entity.Entity;
import com.aimiko.task5.persistence.exeption.FileIOException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GenericRepository <E extends Entity> implements Repository<E> {

protected final Set<E> entities;

private final Path path;

public GenericRepository(Path path) {
    this.path = path;
    entities = new HashSet<>(loadAll());
}

    @Override
    public Optional<E> findById(UUID id) {
        return entities.stream().filter(e -> e.id().equals(id)).findFirst();
    }

    @Override
    public Set<E> findAll() {
        return entities;
    }

    @Override
    public Set<E> findBy(Predicate<E> filter) {
        return entities.stream().filter(filter).collect(Collectors.toSet());
    }

    @Override
    public E add(E entity) {
        if(Objects.nonNull(entity.id())){
            Optional<E> entityRemove = findById(entity.id());
            entities.remove(entityRemove);
        }

        entities.add(entity);
        return entity;
    }

    @Override
    public boolean remove(E entity) {
        return entities.remove(entity);
    }

    protected abstract String serialize(E entity);
    protected abstract E deserialize(String l);

    private Set<E> loadAll() {
        try {
            fileNotFound();
        } catch (IOException e) {
            throw new FileIOException("Помилка при роботі із файлом %s."
                    .formatted(path.getFileName()));
        }

        try(Stream<String> lines = Files.lines(path)) {
            return lines.map(this::deserialize).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new FileIOException("Помилка при роботі із файлом %s."
                    .formatted(path.getFileName()));
        }
    }

    private void fileNotFound() throws IOException {
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
    }

    public void saveChanges(){

        try {
            String lines = entities.stream().map(e -> serialize(e)).collect(Collectors.joining());
            Files.writeString(path, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new FileIOException("Помилка при збереженні у файл: %s."
                    .formatted(path.getFileName()));
        }
    }
}
