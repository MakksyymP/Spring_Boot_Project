package ua.library.example.LibraryBootApp.utils.exceptions;

import org.springframework.http.HttpStatus;
import ua.library.example.LibraryBootApp.utils.ResponseError;

public class ValidationException extends ResponseError {
    public ValidationException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
