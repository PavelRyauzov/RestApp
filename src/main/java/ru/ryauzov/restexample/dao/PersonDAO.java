package ru.ryauzov.restexample.dao;

import ru.ryauzov.restexample.entities.PersonEntity;

import java.util.List;

public interface PersonDAO {
    void create(PersonEntity person);
    List<PersonEntity> getPeople();
}
