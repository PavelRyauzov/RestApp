package ru.ryauzov.restexample.controllers;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.services.PersonService;

import javax.validation.Valid;

@RestController
@Log4j2
public class PersonController {

    Logger logger = LogManager.getLogger(PersonController.class);

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/send-person-json")
    public ResponseEntity send(@RequestBody @Valid PersonDTO personDTO) {
        try {
            log.info("Начало работы...");
            personService.send(personDTO);
            log.info("Конвертация прошла успешно!");
            return ResponseEntity.ok("Сервер работает!");
        } catch (Exception e) {
            log.error("Exception is thrown", e);
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
