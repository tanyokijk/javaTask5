package com.aimiko.task5.persistence.repository;

import com.aimiko.task5.persistence.entity.User;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends Repository<User> {

    Set<User> findByName(String name);

    Set<User> findByAge(int age);

    void saveChanges();

}
