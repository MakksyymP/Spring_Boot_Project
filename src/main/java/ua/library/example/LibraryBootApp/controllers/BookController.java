package ua.library.example.LibraryBootApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.library.example.LibraryBootApp.models.Book;
import ua.library.example.LibraryBootApp.service.BooksService;
import ua.library.example.LibraryBootApp.service.PeopleService;

import java.util.stream.IntStream;


@Controller
@RequestMapping("/books")
public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "3") int size,
                        @RequestParam(value = "sort", required = false, defaultValue = "id") String sort) {

        Page<Book> pageBooks = null;

        if (sort.equals("id") | sort.equals("year")){
            pageBooks = booksService.index(PageRequest.of(page, size, Sort.Direction.DESC, sort));
        } else {
            pageBooks = booksService.index(PageRequest.of(page, size, Sort.Direction.ASC, sort));
        }

        model.addAttribute("books", pageBooks);
        model.addAttribute("numbers", IntStream.range(0, pageBooks.getTotalPages()).toArray());
        model.addAttribute("sort", sort);
        return "books/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") int id , Model model) {
        model.addAttribute("book", booksService.show(id));
        model.addAttribute("owner", booksService.showOwner(id));
        model.addAttribute("people", peopleService.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String addBooks(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "books/new";

        booksService.add(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, @ModelAttribute("book") Book book,  Model model) {
        model.addAttribute(booksService.show(id));
        return "books/edit";
    }

    @PostMapping("{id}/update")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(book, id);
        return "redirect:/books/" + id;
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("{id}/return")
    public String returnBook(@PathVariable("id") int id) {
        booksService.returnBook(id);
        return "redirect:/books/" + id;
    }

    @PostMapping("{id}/get")
    public String getBook(@PathVariable("id") int bookId, @RequestParam("personId") int personId) {
        booksService.getBook(bookId, peopleService.show(personId));
        return "redirect:/books/" + bookId;
    }
}
