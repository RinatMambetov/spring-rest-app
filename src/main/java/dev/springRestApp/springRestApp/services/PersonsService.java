package dev.springRestApp.springRestApp.services;

import dev.springRestApp.springRestApp.models.Person;
import dev.springRestApp.springRestApp.repositories.PersonsRepository;
import dev.springRestApp.springRestApp.util.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonsService {

    private final PersonsRepository personsRepository;

    @Autowired
    public PersonsService(PersonsRepository personsRepository) {
        this.personsRepository = personsRepository;
    }

    public List<Person> findAll() {
        return personsRepository.findAll();
    }

    public Person findById(int id) {
        Optional<Person> person = personsRepository.findById(id);
        return person.orElseThrow(() -> new PersonNotFoundException(id));
    }

    @Transactional
    public void save(Person person) {
        personsRepository.save(person);
    }
}
