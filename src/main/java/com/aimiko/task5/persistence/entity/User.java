package com.aimiko.task5.persistence.entity;

import java.util.Objects;
import java.util.UUID;

public record User(
        UUID id,
        String name,
        int age)  implements Entity{

    @Override
    public String toString() {
        return String.format("id = %s, ім'я = %s, вік = %d", id, name, age);
    }

}
