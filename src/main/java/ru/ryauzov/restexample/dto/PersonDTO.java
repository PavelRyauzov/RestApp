package ru.ryauzov.restexample.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import ru.ryauzov.restexample.entities.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@JsonRootName("person")
public class PersonDTO {
    @NotBlank(message = "Name is blank")
    private String name;

    @NotBlank(message = "Surname is blank")
    private String surname;

    @NotBlank(message = "Patronymic is blank")
    private String patronymic;

    @NotNull(message = "Birth date is null")
    private Date birthDate;

    @NotNull(message = "Gender is null")
    private Gender gender;

    @NotNull(message = "Document is null")
    private DocumentDTO document;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }
}
