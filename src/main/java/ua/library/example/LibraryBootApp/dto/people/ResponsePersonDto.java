package ua.library.example.LibraryBootApp.dto.people;

import ua.library.example.LibraryBootApp.models.Person;

import java.util.List;

public class ResponsePersonDto {

    private List<Person> people;
    private int[] numbers;
    private String sort;

    public ResponsePersonDto(List<Person> people, int[] numbers, String sort) {
        this.people = people;
        this.numbers = numbers;
        this.sort = sort;
    }

    public List<Person> getPeople() {
        return people;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public String getSort() {
        return sort;
    }
}
