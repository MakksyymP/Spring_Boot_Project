package ua.library.example.LibraryBootApp.dto.books;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.library.example.LibraryBootApp.utils.validations.CreateValidation;
import ua.library.example.LibraryBootApp.utils.validations.UpdateValidation;

public class RequestBookDto {

    @NotNull(message = "Name should not be empty", groups = CreateValidation.class)
    @Size(min = 2, max = 50, message = "Name should be between 8 and 50 characters", groups = {CreateValidation.class, UpdateValidation.class})
    private String name;

    @NotNull(message = "Author should not be empty", groups = CreateValidation.class)
    @Size(min = 2, max = 50, message = "Author should be between 8 and 50 characters", groups = {CreateValidation.class, UpdateValidation.class})
    private String author;

    @NotNull(message = "Issue year should not be empty", groups = CreateValidation.class)
    @Min(value = 1700, message = "Year of Issue should be greater than 1700", groups = {CreateValidation.class, UpdateValidation.class})
    private int year;

    public RequestBookDto(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}
