package ua.library.example.LibraryBootApp.utils;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.library.example.LibraryBootApp.utils.exceptions.BookLimitExceededException;
import ua.library.example.LibraryBootApp.utils.exceptions.EntityNotFoundException;
import ua.library.example.LibraryBootApp.utils.exceptions.IrresponsibleUserException;


@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handle(ValidationException e) {
        return new ResponseError(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handle(EntityNotFoundException e) {
        return new ResponseError(HttpStatus.NOT_FOUND, e.getReason());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handle(BookLimitExceededException e) {
        return new ResponseError(HttpStatus.BAD_REQUEST, e.getReason());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handle(IrresponsibleUserException e) {
        return new ResponseError(HttpStatus.BAD_REQUEST, e.getReason());
    }

}
