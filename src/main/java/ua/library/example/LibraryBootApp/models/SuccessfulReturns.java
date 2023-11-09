package ua.library.example.LibraryBootApp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "successful_returns")
public class SuccessfulReturns {

    @Id
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonIgnore
    private Person person;

    @Column(name = "successful_returns")
    private int successful;

    @Column(name = "overdue_returns")
    private int overdue;

    public SuccessfulReturns(int id, int successful, int overdue) {
        this.id = id;
        this.successful = successful;
        this.overdue = overdue;
    }

    public SuccessfulReturns() {

    }

    public int getId() {
        return id;
    }

    public int getSuccessful() {
        return successful;
    }

    public int getOverdue() {
        return overdue;
    }

    public void setSuccessful(int successful) {
        this.successful = successful;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
