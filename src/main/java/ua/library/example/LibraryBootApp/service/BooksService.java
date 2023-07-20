package ua.library.example.LibraryBootApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.library.example.LibraryBootApp.models.Book;
import ua.library.example.LibraryBootApp.models.Person;
import ua.library.example.LibraryBootApp.repositories.BooksRepositories;
import ua.library.example.LibraryBootApp.repositories.PeopleRepositories;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepositories booksRepositories;

    @Autowired
    public BooksService(BooksRepositories booksRepositories) {
        this.booksRepositories = booksRepositories;
    }

    public Page<Book> index(Pageable pageable) {
        return booksRepositories.findAll(pageable);
    }

    public Book show(int id) {
        Optional<Book> foundBook = booksRepositories.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void add(Book book) {
        booksRepositories.save(book);
    }

    @Transactional
    public void update(Book updatedBook, int id) {
        updatedBook.setId(id);
        booksRepositories.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepositories.deleteById(id);
    }

    @Transactional
    public void returnBook(int id) {
        Book book = booksRepositories.findById(id).orElse(null);
        book.setPerson(null);
        book.setOrderTime(null);
        booksRepositories.save(book);
    }

    @Transactional
    public void getBook(int bookId, Person person){
        Book book = booksRepositories.findById(bookId).orElse(null);
        book.setPerson(person);
        book.setOrderTime(LocalDateTime.now());
        booksRepositories.save(book);
    }

    public Person showOwner(int bookId) {
        return booksRepositories.findById(bookId).get().getPerson();
    }

    public Optional<Book> searchByTitle(String title) {
        Optional<Book> searchedBook = Optional.ofNullable(booksRepositories.findBookByNameStartingWith(title));
        return searchedBook;
    }
}
