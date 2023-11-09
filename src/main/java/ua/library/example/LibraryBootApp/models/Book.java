package ua.library.example.LibraryBootApp.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Books")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;

    @Column(name = "issueyear")
    private int year;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "loan_duration_weeks")
    private int loanDurationWeeks;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Transient
    private boolean isOverdue = false;

    public Book() {
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return owner;
    }

    public void setPerson(Person owner) {
        this.owner = owner;
    }

    public LocalDateTime getTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public int getLoanDurationWeeks() {
        return loanDurationWeeks;
    }

    public void setLoanDurationWeeks(int loanDurationWeeks) {
        this.loanDurationWeeks = loanDurationWeeks;
    }
}
