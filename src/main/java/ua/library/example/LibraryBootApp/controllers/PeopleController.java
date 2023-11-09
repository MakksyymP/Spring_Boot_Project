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
import ua.library.example.LibraryBootApp.dto.people.NameDto;
import ua.library.example.LibraryBootApp.dto.people.RequestPersonDto;
import ua.library.example.LibraryBootApp.dto.people.ResponsePersonByIdDto;
import ua.library.example.LibraryBootApp.dto.people.ResponsePersonDto;
import ua.library.example.LibraryBootApp.models.Person;
import ua.library.example.LibraryBootApp.service.PeopleService;
import ua.library.example.LibraryBootApp.utils.exceptions.NameIsAlreadyTakenException;
import ua.library.example.LibraryBootApp.utils.validations.BindingResultParser;
import ua.library.example.LibraryBootApp.utils.validations.CreateValidation;
import ua.library.example.LibraryBootApp.utils.validations.UpdateValidation;

import java.util.stream.IntStream;


@RestController
@RequestMapping("/people")
@CrossOrigin(origins = "http://localhost:9091")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public ResponseEntity<?> index(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "3") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction) {

        Page<Person> pagePerson;
        if (direction.equalsIgnoreCase("asc")) {
            pagePerson = peopleService.index(PageRequest.of(page, size, Sort.Direction.ASC, sort));
        } else {
            pagePerson = peopleService.index(PageRequest.of(page, size, Sort.Direction.DESC, sort));
        }

        ResponsePersonDto dto = new ResponsePersonDto(pagePerson.getContent(), IntStream.range(0, pagePerson.getTotalPages()).toArray(), sort);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable("id") int id) {
        Person person = peopleService.show(id);

        ResponsePersonByIdDto dto = new ResponsePersonByIdDto(person, person.getBooks());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/name")
    public ResponseEntity<?> showByName(@RequestBody NameDto dto) {
        return new ResponseEntity<>(peopleService.getPersonByNameStartWith(dto.getName()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated(CreateValidation.class) RequestPersonDto dto,
                         BindingResult bindingResult) {
        checkValidation(bindingResult, dto.getName());

        peopleService.add(dto);
        return new ResponseEntity<>("Person " + dto.getName() + " was created", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody @Validated(UpdateValidation.class) RequestPersonDto dto, BindingResult bindingResult) {
        checkValidation(bindingResult, dto.getName());

        Person person = new Person();
        person.setName(dto.getName() == null ? person.getName() : dto.getName());
        person.setYear(dto.getYear() == 0 ? person.getYear() : dto.getYear());

        peopleService.update(id, person);
        return new ResponseEntity<>("Person " + dto.getName() + " was updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return new ResponseEntity<>("Person was deleted", HttpStatus.OK);
    }

    private void checkValidation (BindingResult bindingResult, String name) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultParser.parse(bindingResult));
        }
        if (peopleService.getPersonByName(name).isPresent()){
            throw new NameIsAlreadyTakenException();
        }
    }
}
