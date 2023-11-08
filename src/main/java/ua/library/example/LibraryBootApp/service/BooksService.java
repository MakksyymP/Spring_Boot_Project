package ua.library.example.LibraryBootApp.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.library.example.LibraryBootApp.dto.books.RequestBookDto;
import ua.library.example.LibraryBootApp.models.Book;
import ua.library.example.LibraryBootApp.models.Person;
import ua.library.example.LibraryBootApp.repositories.BooksRepositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepositories booksRepositories;
    private final PeopleService peopleService;

    @Autowired
    public BooksService(BooksRepositories booksRepositories, PeopleService peopleService) {
        this.booksRepositories = booksRepositories;
        this.peopleService = peopleService;
    }

    public Page<Book> index(Pageable pageable) {
        return booksRepositories.findAll(pageable);
    }

    public Book show(int id) {
        Optional<Book> foundBook = booksRepositories.findById(id);

        if (foundBook.isEmpty()) {
            throw new EntityNotFoundException("Book not found");
        }

        return foundBook.get();
    }

    @Transactional
    public void add(RequestBookDto dto) {
        Book book = new Book(dto.getName(), dto.getAuthor(), dto.getYear());
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
        Book book = show(id);
        book.setPerson(null);
        book.setOrderTime(null);
        booksRepositories.save(book);
    }

    @Transactional
    public void getBook(int bookId, int personId){
        Book book = show(bookId);
        book.setPerson(peopleService.show(personId));
        book.setOrderTime(LocalDateTime.now());
        booksRepositories.save(book);
    }

    public Person showOwner(int bookId) {
        return show(bookId).getPerson();
    }

    private void checkOverdue(Book book) {
        int bookLoanPeriod = 2;
        LocalDateTime orderTime = book.getTime();
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(bookLoanPeriod);

        if (orderTime.isBefore(twoWeeksAgo)) {
            book.setOverdue(true); //TODO Добавити функцію записання не повернтутих книжок
        }
    }

    public List<Book> searchByTitle(String title) {
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        return booksRepositories.findBookByNameStartingWith(title);
    }
}
