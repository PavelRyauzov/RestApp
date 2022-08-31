package ru.ryauzov.restexample.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ryauzov.restexample.dao.PersonDAO;
import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.entities.PersonEntity;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonDAO personDAO;
    private final ObjectMapper mapper;

    @Autowired
    public PersonServiceImpl(PersonDAO personDAO, ObjectMapper mapper) {
        this.personDAO = personDAO;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public void send(PersonDTO personDTO) {
        PersonEntity person = mapper.convertValue(personDTO, PersonEntity.class);
        personDAO.create(person);

        String xmlText;
        try {
            String json = mapper.writeValueAsString(personDTO);
            JSONObject jsonObject = new JSONObject(json);
            xmlText = XML.toString(jsonObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<PersonEntity> getPeople() {
        return personDAO.getPeople();
    }
}
