package dev.springRestApp.springRestApp.controllers;

import dev.springRestApp.springRestApp.models.Person;
import dev.springRestApp.springRestApp.services.PersonsService;
import dev.springRestApp.springRestApp.util.PersonErrorResponse;
import dev.springRestApp.springRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonsController {

    private final PersonsService personsService;

    @Autowired
    public PersonsController(PersonsService personsService) {
        this.personsService = personsService;
    }

    @GetMapping()
    public List<Person> getPersons() {
        return personsService.findAll(); // Jackson convert objects to json
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable("id") int id) {
        return personsService.findById(id);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person with id " + e.getPersonId() + " not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND); // 404
    }
}
