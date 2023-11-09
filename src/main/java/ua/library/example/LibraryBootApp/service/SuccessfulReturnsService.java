package ua.library.example.LibraryBootApp.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.library.example.LibraryBootApp.models.Person;
import ua.library.example.LibraryBootApp.models.SuccessfulReturns;
import ua.library.example.LibraryBootApp.repositories.SuccessfulReturnsRepository;

@Service
@Transactional(readOnly = true)
public class SuccessfulReturnsService {

    private final SuccessfulReturnsRepository repository;

    @Autowired
    public SuccessfulReturnsService(SuccessfulReturnsRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void addToOverdue(int person_id) {
        SuccessfulReturns returns = repository.findById(person_id)
                .orElseThrow(() -> new EntityNotFoundException("Return not found"));

        returns.setOverdue(returns.getOverdue() + 1);
        repository.save(returns);
    }

    @Transactional
    public void addToSuccessful(int person_id) {
        SuccessfulReturns returns = repository.findById(person_id)
                .orElseThrow(() -> new EntityNotFoundException("Return not found"));

        returns.setSuccessful(returns.getSuccessful() + 1);
        repository.save(returns);
    }

    @Transactional
    public void createReturn(Person person) {
       SuccessfulReturns returns = new SuccessfulReturns();
       returns.setPerson(person);
       returns.setSuccessful(0);
       returns.setOverdue(0);

       repository.save(returns);
    }

}
