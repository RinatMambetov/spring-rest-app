package dev.springRestApp.springRestApp.repositories;

import dev.springRestApp.springRestApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonsRepository extends JpaRepository<Person, Integer> {
}
