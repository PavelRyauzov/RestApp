package ru.ryauzov.restexample.services;

import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.entities.PersonEntity;

import java.util.List;

public interface PersonService {
    void send(PersonDTO personDTO);
    List<PersonEntity> getPeople();
}
