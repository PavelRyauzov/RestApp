package ru.ryauzov.restexample.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ru.ryauzov.restexample.dao.PersonDAO;
import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.entities.PersonEntity;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonDAO personDAO;
    private final ObjectMapper mapper;

    public PersonServiceImpl(PersonDAO personDAO, ObjectMapper mapper) {
        this.personDAO = personDAO;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void send(PersonDTO personDTO) {
        PersonEntity person = mapper.convertValue(personDTO, PersonEntity.class);
        personDAO.create(person);
    }

    @Override
    @Transactional
    public List<PersonEntity> getPeople() {
        return personDAO.getPeople();
    }
}
