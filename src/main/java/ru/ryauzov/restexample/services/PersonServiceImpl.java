package ru.ryauzov.restexample.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ryauzov.restexample.client.SoapConverterClient;
import ru.ryauzov.restexample.dao.PersonDAO;
import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.entities.PersonEntity;
import ru.ryauzov.restexample.wsdl.GetConvertedXmlResponse;

import javax.transaction.Transactional;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonDAO personDAO;
    private final ObjectMapper mapper;

    private final SoapConverterClient soapConverterClient;

    @Autowired
    public PersonServiceImpl(PersonDAO personDAO, ObjectMapper mapper, SoapConverterClient soapConverterClient) {
        this.personDAO = personDAO;
        this.mapper = mapper;
        this.soapConverterClient = soapConverterClient;
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

            GetConvertedXmlResponse response = soapConverterClient.getConvertedXml(xmlText);

            System.out.println("Result: " + response.getConvertedXmlText());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
