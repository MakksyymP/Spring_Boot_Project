package ua.library.example.LibraryBootApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.library.example.LibraryBootApp.models.Person;

import java.util.List;

@Repository
public interface PeopleRepositories extends JpaRepository<Person, Integer> {
    Person findPersonByName(String name);
    List<Person> findPersonByNameStartingWith(String name);
}
