package ua.library.example.LibraryBootApp.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IrresponsibleUserException extends ResponseStatusException {

    public IrresponsibleUserException(int maxLimit) {
        super(HttpStatus.BAD_REQUEST, "Due to multiple instances of overdue returns, your maximum borrowing period has been limited to " + maxLimit + " weeks.");
    }
}
