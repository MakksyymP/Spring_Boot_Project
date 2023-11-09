package ua.library.example.LibraryBootApp.dto.people;

import ua.library.example.LibraryBootApp.models.Book;
import ua.library.example.LibraryBootApp.models.Person;

import java.util.List;

public class ResponsePersonByIdDto {

    Person person;
    List<Book> books;

    public ResponsePersonByIdDto(Person person, List<Book> books) {
        this.person = person;
        this.books = books;
    }

    public Person getPerson() {
        return person;
    }

    public List<Book> getBooks() {
        return books;
    }

}
