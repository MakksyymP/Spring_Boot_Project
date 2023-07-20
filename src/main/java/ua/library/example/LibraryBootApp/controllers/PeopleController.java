package ua.library.example.LibraryBootApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.library.example.LibraryBootApp.models.Person;
import ua.library.example.LibraryBootApp.service.PeopleService;
import ua.library.example.LibraryBootApp.validators.PeopleValidator;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PeopleValidator peopleValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PeopleValidator peopleValidator) {
        this.peopleService = peopleService;
        this.peopleValidator = peopleValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", peopleService.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.show(id));
        model.addAttribute("books", peopleService.showAllPersonalBooks(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        peopleValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.add(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id ,@ModelAttribute("person") Person person, Model model) {
        model.addAttribute(peopleService.show(id));
        return "people/edit";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        peopleValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return "people/edit";

        peopleService.update(id, person);
        return "redirect:/people/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
