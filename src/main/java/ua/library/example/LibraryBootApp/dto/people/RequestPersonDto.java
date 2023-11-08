package ua.library.example.LibraryBootApp.dto.people;

import jakarta.validation.constraints.*;
import ua.library.example.LibraryBootApp.utils.validations.CreateValidation;
import ua.library.example.LibraryBootApp.utils.validations.UpdateValidation;

public class RequestPersonDto {

    @NotNull(message = "FullName should not be empty", groups = CreateValidation.class)
    @Size(min = 2, max = 50, message = "FullName should be between 8 and 50 characters", groups = {CreateValidation.class, UpdateValidation.class})
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "Your FullName should be in this format: Name Surname Patronymic", groups = {CreateValidation.class, UpdateValidation.class})
    private String name;

    @Min(value = 1900, message = "Year of Birth should be greater than 1900", groups = {CreateValidation.class, UpdateValidation.class})
    private int year;

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}
