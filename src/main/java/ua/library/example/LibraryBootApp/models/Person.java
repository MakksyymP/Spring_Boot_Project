package ua.library.example.LibraryBootApp.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "people")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fullname")
    private String name;

    @Column(name = "birthyear")
    private int year;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Book> books;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private SuccessfulReturns returns;

    public Person() {
    }

    public Person(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public SuccessfulReturns getReturns() {
        return returns;
    }

    public List<Book> getBooks() {
        books.forEach(this::checkOverdue);
        return books;
    }

    private void checkOverdue(Book book) {
        int bookLoanPeriod = book.getLoanDurationWeeks();
        LocalDateTime orderTime = book.getTime();
        LocalDateTime coupleWeeksAgo = LocalDateTime.now().minusWeeks(bookLoanPeriod);

        if (orderTime.isBefore(coupleWeeksAgo)) {
            book.setOverdue(true);
        }
    }
}
