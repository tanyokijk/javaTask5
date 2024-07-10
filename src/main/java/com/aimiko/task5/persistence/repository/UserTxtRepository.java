package com.aimiko.task5.persistence.repository;


import static java.lang.StringTemplate.STR;

import com.aimiko.task5.persistence.entity.User;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class UserTxtRepository extends GenericRepository<User> implements UserRepository{

    public UserTxtRepository(){
        super(TxtPathFactory.USERS.getPath());
    }

    @Override
    protected String serialize(User user) {
        return STR."\{user.id()}|\{user.name()}|\{user.age()}\n";
    }

    @Override
    protected User deserialize(String l) {
        String[] parts = l.split("\\|");
        return new User(UUID.fromString(parts[0]), parts[1], Integer.parseInt(parts[2]));
    }

    @Override
    public Set<User> findByName(String name) {
        return findBy(e-> e.name().contains(name));
    }

    @Override
    public Set<User> findByAge(int age) {
        return findBy(e -> e.age()==age);
    }

    @Override
    public void saveChanges(){
        super.saveChanges();
    }

    @Override
    public User add(User entity) {
        // Перевіряємо, чи вже існує користувач з таким же id
        Optional<User> existingUser = findById(entity.id());
        if (existingUser.isPresent()) {
            // Якщо користувач існує, оновлюємо його дані
            entities.remove(existingUser.get()); // Видаляємо старий запис
            entities.add(entity); // Додаємо новий запис
        } else {
            // Якщо користувача з таким id немає, просто додаємо новий запис
            entities.add(entity);
        }
        return entity;
    }
}
