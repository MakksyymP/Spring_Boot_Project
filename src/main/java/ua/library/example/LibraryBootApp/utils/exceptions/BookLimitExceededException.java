package ua.library.example.LibraryBootApp.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookLimitExceededException extends ResponseStatusException {
    public BookLimitExceededException(int maxSize) {
        super(HttpStatus.BAD_REQUEST, "Considering your borrowing track record, the maximum number of books available for checkout is adjusted to " + maxSize + ".");
    }
}
