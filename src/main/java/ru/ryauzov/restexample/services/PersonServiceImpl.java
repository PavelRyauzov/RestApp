package ru.ryauzov.restexample.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
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
import java.io.File;
import java.io.StringReader;
import java.text.SimpleDateFormat;


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

        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        String xmlText;
        try {
            xmlText = xmlMapper.writeValueAsString(personDTO);
            GetConvertedXmlResponse response = soapConverterClient.getConvertedXml(xmlText);

            JAXBContext jaxbContext = JAXBContext.newInstance(PersonDTO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            PersonDTO testDTO = (PersonDTO) jaxbUnmarshaller.unmarshal(new StringReader(response.getConvertedXmlText()));
            PersonEntity testEntity = mapper.convertValue(testDTO, PersonEntity.class);
            personDAO.create(testEntity);
        } catch (JsonProcessingException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
