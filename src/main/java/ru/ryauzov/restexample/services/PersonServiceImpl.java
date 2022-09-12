package ru.ryauzov.restexample.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ryauzov.restexample.client.SoapConverterClient;
import ru.ryauzov.restexample.dao.PersonDAO;
import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.entities.PersonEntity;
import ru.ryauzov.restexample.wsdl.GetConvertedXmlResponse;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.text.SimpleDateFormat;


@Service
@Log4j2
public class PersonServiceImpl implements PersonService {
    private final PersonDAO personDAO;
    private final SoapConverterClient soapConverterClient;

    @Autowired
    public PersonServiceImpl(PersonDAO personDAO, SoapConverterClient soapConverterClient) {
        this.personDAO = personDAO;
        this.soapConverterClient = soapConverterClient;
    }

    @Override
    @Transactional
    public void send(PersonDTO personDTO) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        PersonEntity person = objectMapper.convertValue(personDTO, PersonEntity.class);

        personDAO.create(person);

        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            String xmlText = xmlMapper.writeValueAsString(personDTO);

            GetConvertedXmlResponse response = soapConverterClient.getConvertedXml(xmlText);

            JAXBContext jaxbContext = JAXBContext.newInstance(PersonDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            PersonDTO convertedPersonDTO = (PersonDTO) jaxbUnmarshaller.unmarshal(new StringReader(response.getConvertedXmlText()));
            PersonEntity convertedPersonEntity = objectMapper.convertValue(convertedPersonDTO, PersonEntity.class);
        } catch (JsonProcessingException | JAXBException e) {
            log.error("Exception is thrown", e);
            throw new Exception("Exception in PersonService");
        }
    }
}
