package ua.library.example.LibraryBootApp.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.library.example.LibraryBootApp.dto.people.RequestPersonDto;
import ua.library.example.LibraryBootApp.models.Person;
import ua.library.example.LibraryBootApp.repositories.PeopleRepositories;

import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepositories peopleRepositories;
    private final SuccessfulReturnsService returnsService;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories, SuccessfulReturnsService successfulReturnsService) {
        this.peopleRepositories = peopleRepositories;
        this.returnsService = successfulReturnsService;
    }

    public Page<Person> index(Pageable pageable) {
        return peopleRepositories.findAll(pageable);
    }

    @Transactional
    public Person show(int id) {
        Optional<Person> foundPerson = peopleRepositories.findById(id);

        if (foundPerson.isEmpty()) {
            throw new EntityNotFoundException("Book not found");
        }

        return foundPerson.get();
    }

    @Transactional
    public void add(RequestPersonDto dto) {
        Person person = new Person();
        person.setName(dto.getName());
        person.setYear(dto.getYear());

        returnsService.createReturn(person);
        peopleRepositories.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepositories.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepositories.deleteById(id);
    }

    public Optional<Person> getPersonByName(String name){
        return Optional.ofNullable(peopleRepositories.findPersonByName(name));
    }

    public List<Person> getPersonByNameStartWith(String name){
        name = Arrays.stream(name.split(" ")).map(val -> val = val.substring(0, 1).toUpperCase() + val.substring(1)).collect(Collectors.joining(" "));
        return peopleRepositories.findPersonByNameStartingWith(name);
    }

}
