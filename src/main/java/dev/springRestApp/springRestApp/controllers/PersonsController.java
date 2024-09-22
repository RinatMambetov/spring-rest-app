package dev.springRestApp.springRestApp.controllers;

import dev.springRestApp.springRestApp.dto.PersonDTO;
import dev.springRestApp.springRestApp.models.Person;
import dev.springRestApp.springRestApp.services.PersonsService;
import dev.springRestApp.springRestApp.util.PersonErrorResponse;
import dev.springRestApp.springRestApp.util.PersonNotCreatedException;
import dev.springRestApp.springRestApp.util.PersonNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    @Autowired
    public PersonsController(PersonsService personsService, ModelMapper modelMapper) {
        this.personsService = personsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPersons() {
        return personsService.findAll().stream().map(this::convertToPersonDTO).toList();
        // Jackson convert objects to json
    }

    @GetMapping("/{id}")
    public PersonDTO getPersonById(@PathVariable("id") int id) {
        return convertToPersonDTO(personsService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
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

        personsService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Person convertToPerson(PersonDTO personDTO) {
//        Person person = new Person();
//        person.setName(personDTO.getName());
//        person.setAge(personDTO.getAge());
//        person.setEmail(personDTO.getEmail());
//        return person;

//        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
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
