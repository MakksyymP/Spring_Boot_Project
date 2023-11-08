package ua.library.example.LibraryBootApp.dto.books;

import ua.library.example.LibraryBootApp.models.Book;

import java.util.List;

public class ResponseBookDto {

    private List<Book> books;
    private int[] numbers;
    private String sort;

    public ResponseBookDto(List<Book> books, int[] numbers, String sort) {
        this.books = books;
        this.numbers = numbers;
        this.sort = sort;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public String getSort() {
        return sort;
    }
}
