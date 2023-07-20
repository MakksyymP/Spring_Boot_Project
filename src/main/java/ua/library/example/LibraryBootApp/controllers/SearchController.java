package ua.library.example.LibraryBootApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.library.example.LibraryBootApp.models.Book;
import ua.library.example.LibraryBootApp.service.BooksService;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final BooksService booksService;

    @Autowired
    public SearchController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping()
    public String index() {
        return "search/index";
    }

    @GetMapping("/show")
    public String search(Model model, @RequestParam(value = "title") String title) {
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        Book book = booksService.searchByTitle(title).orElse(null);

        if (book == null) {
            model.addAttribute("book", new Book(null, null, 0));
        } else {
            model.addAttribute("book", book);
            model.addAttribute("owner", (book.getPerson() != null) ? book.getPerson().getName() : null);
        }

        return "search/show";
    }
}
