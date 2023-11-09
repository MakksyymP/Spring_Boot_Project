package ua.library.example.LibraryBootApp.controllers;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ua.library.example.LibraryBootApp.dto.books.TitleDto;
import ua.library.example.LibraryBootApp.dto.books.RequestBookDto;
import ua.library.example.LibraryBootApp.dto.books.ResponseBookDto;
import ua.library.example.LibraryBootApp.models.Book;
import ua.library.example.LibraryBootApp.service.BooksService;
import ua.library.example.LibraryBootApp.service.PeopleService;
import ua.library.example.LibraryBootApp.utils.validations.BindingResultParser;
import ua.library.example.LibraryBootApp.utils.validations.CreateValidation;
import ua.library.example.LibraryBootApp.utils.validations.UpdateValidation;

import java.util.stream.IntStream;


@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:9091")
public class BookController {

    private final BooksService booksService;

    @Autowired
    public BookController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction) {

        Page<Book> pageBooks;
        if (direction.equalsIgnoreCase("asc")) {
            pageBooks = booksService.index(PageRequest.of(page, size, Sort.Direction.ASC, sort));
        } else {
            pageBooks = booksService.index(PageRequest.of(page, size, Sort.Direction.DESC, sort));
        }

        ResponseBookDto bookPageDTO = new ResponseBookDto(pageBooks.getContent(), IntStream.range(0, pageBooks.getTotalPages()).toArray(), sort);
        return new ResponseEntity<>(bookPageDTO, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") int id) {
        return new ResponseEntity<>(booksService.show(id), HttpStatus.OK);
    }

    @PostMapping("/title")
    public ResponseEntity<?> showByTitle(@RequestBody TitleDto dto) {
        return new ResponseEntity<>(booksService.searchByTitle(dto.getTitle()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated(CreateValidation.class) RequestBookDto dto, BindingResult bindingResult) {
        checkValidation(bindingResult);

        booksService.add(dto);
        return new ResponseEntity<>("Books was created", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Validated(UpdateValidation.class) RequestBookDto dto, BindingResult bindingResult, @PathVariable("id") int id) {
        checkValidation(bindingResult);

        Book book = booksService.show(id);
        book.setName(dto.getName() == null ? book.getName() : dto.getName());
        book.setAuthor(dto.getAuthor() == null ? book.getAuthor() : dto.getAuthor());
        book.setYear(dto.getYear() == 0 ? book.getYear() : dto.getYear());

        booksService.update(book, id);
        return new ResponseEntity<>("Book was updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable("id") int id) {
        booksService.returnBook(id);
        return new ResponseEntity<>("Book was returned", HttpStatus.OK);
    }

    @PatchMapping("{id}/get")
    public ResponseEntity<?> getBook(@PathVariable("id") int bookId, @RequestParam("personId") int personId,
                                     @RequestParam(value = "loanPeriod", defaultValue = "1") int loanPeriod) {
        booksService.getBook(bookId, personId, loanPeriod);
        return new ResponseEntity<>("Book was got", HttpStatus.OK);
    }

    private void checkValidation (BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultParser.parse(bindingResult));
        }
    }
}
