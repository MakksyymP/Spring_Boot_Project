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
import ua.library.example.LibraryBootApp.utils.exceptions.BookLimitExceededException;
import ua.library.example.LibraryBootApp.utils.exceptions.IrresponsibleUserException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepositories booksRepositories;
    private final PeopleService peopleService;
    private final SuccessfulReturnsService returnsService;

    @Autowired
    public BooksService(BooksRepositories booksRepositories, PeopleService peopleService, SuccessfulReturnsService returnsService) {
        this.booksRepositories = booksRepositories;
        this.peopleService = peopleService;
        this.returnsService = returnsService;
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

        checkOverdue(book, book.getPerson().getId());
        book.setPerson(null);
        book.setOrderTime(null);

        booksRepositories.save(book);
    }

    @Transactional
    public void getBook(int bookId, int personId, int loanPeriod){
        Book book = show(bookId);

        Person person = peopleService.show(personId);
        checkBooksLimit(person);
        validateUserResponsibility(person, loanPeriod);

        book.setLoanDurationWeeks(loanPeriod);
        book.setPerson(person);
        book.setOrderTime(LocalDateTime.now());
        booksRepositories.save(book);
    }

    public List<Book> searchByTitle(String title) {
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        return booksRepositories.findBookByNameStartingWith(title);
    }

    private void checkOverdue(Book book, int personId) {
        int bookLoanPeriod = book.getLoanDurationWeeks();
        LocalDateTime orderTime = book.getTime();
        LocalDateTime twoWeeksAgo = LocalDateTime.now().minusWeeks(bookLoanPeriod);

        if (orderTime.isBefore(twoWeeksAgo)) {
            returnsService.addToOverdue(personId);
        } else {
            returnsService.addToSuccessful(personId);
        }
    }

    private void checkBooksLimit(Person person) {
        List<Book> books = person.getBooks();
        int maxSize = 5;

        int overdueAmount = person.getReturns().getOverdue();
        int successfulAmount = person.getReturns().getSuccessful();

        if (successfulAmount > 20) {
            maxSize = 10;
        } else if (successfulAmount > 10) {
            maxSize = 8;
        } else if (successfulAmount > 5) {
            maxSize = 6;
        }

        if (overdueAmount > 20) {
            maxSize = 1;
        } else if (overdueAmount > 10) {
            maxSize = 3;
        } else if (overdueAmount > 5) {
            maxSize = 4;
        }

        if (books.size() >= maxSize) {
            throw new BookLimitExceededException(maxSize);
        }
    }

    private void validateUserResponsibility(Person person, int loanPeriod) {
        int overdueAmount = person.getReturns().getOverdue();
        int maxLimit = 4;

        if (overdueAmount > 20) {
            maxLimit = 1;
        } else if (overdueAmount > 10) {
            maxLimit = 2;
        } else if (overdueAmount > 5) {
            maxLimit = 3;
        }

        if (loanPeriod > maxLimit) {
            throw new IrresponsibleUserException(maxLimit);
        }

    }

}
