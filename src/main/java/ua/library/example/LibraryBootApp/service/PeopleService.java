package ua.library.example.LibraryBootApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.library.example.LibraryBootApp.models.Book;
import ua.library.example.LibraryBootApp.models.Person;
import ua.library.example.LibraryBootApp.repositories.PeopleRepositories;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepositories peopleRepositories;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<Person> index() {
        return peopleRepositories.findAll();
    }

    public Person show(int id) {
        return (Person) peopleRepositories.findById(id).orElse(null);
    }

    @Transactional
    public void add(Person person) {
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

    public List<Book> showAllPersonalBooks(int personId) {
        List<Book> allBooks = peopleRepositories.findById(personId).get().getBooks();

        for (Book book : allBooks){
            if (LocalDateTime.now().minusDays(10).isAfter(book.getTime())){
                book.setOverdue(true);
            }
        }

        return allBooks;
    }

    public Optional<Person> getPersonByName(String name){
        return Optional.ofNullable(peopleRepositories.findPersonByName(name));
    }
}
