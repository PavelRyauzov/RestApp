package ru.ryauzov.restexample.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import ru.ryauzov.restexample.dao.PersonDAO;
import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.entities.PersonEntity;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

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

        String DF = "yyyy-MM-dd";

        ObjectMapper mapper1 = new ObjectMapper().
                configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).
                setDateFormat(new SimpleDateFormat(DF)).
                enable(SerializationFeature.WRAP_ROOT_VALUE).
                enable(DeserializationFeature.UNWRAP_ROOT_VALUE);

        String json;
        String xmlText;
        try {
            json = mapper1.writeValueAsString(personDTO);
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
