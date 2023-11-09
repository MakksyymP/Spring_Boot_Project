package ua.library.example.LibraryBootApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.library.example.LibraryBootApp.models.SuccessfulReturns;

@Repository
public interface SuccessfulReturnsRepository extends JpaRepository<SuccessfulReturns, Integer> {
}
