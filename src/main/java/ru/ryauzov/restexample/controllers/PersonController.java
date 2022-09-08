package ru.ryauzov.restexample.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ryauzov.restexample.dto.PersonDTO;
import ru.ryauzov.restexample.entities.PersonEntity;
import ru.ryauzov.restexample.services.PersonService;

@RestController
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/send-person-json")
    public ResponseEntity send(@RequestBody PersonDTO personDTO) {
        try {
            personService.send(personDTO);
            return ResponseEntity.ok("Сервер работает!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
