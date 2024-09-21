package dev.springRestApp.springRestApp.controllers;

import dev.springRestApp.springRestApp.models.Person;
import dev.springRestApp.springRestApp.services.PersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
