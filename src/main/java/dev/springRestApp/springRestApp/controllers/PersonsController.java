package dev.springRestApp.springRestApp.controllers;

import dev.springRestApp.springRestApp.models.Person;
import dev.springRestApp.springRestApp.services.PersonsService;
import dev.springRestApp.springRestApp.util.PersonErrorResponse;
import dev.springRestApp.springRestApp.util.PersonNotCreatedException;
import dev.springRestApp.springRestApp.util.PersonNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid Person person,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            for (FieldError error : allErrors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PersonNotCreatedException(errorMsg.toString());
        }

        personsService.save(person);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person with id " + e.getPersonId() + " not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST); // 400
    }
}
