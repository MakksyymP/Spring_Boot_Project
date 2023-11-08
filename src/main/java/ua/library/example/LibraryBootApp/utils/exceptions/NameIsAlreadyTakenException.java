package ua.library.example.LibraryBootApp.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NameIsAlreadyTakenException extends ResponseStatusException {

    public NameIsAlreadyTakenException() {
        super(HttpStatus.BAD_REQUEST, "This name is already taken");
    }
}
