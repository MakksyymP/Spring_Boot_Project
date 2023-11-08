package ua.library.example.LibraryBootApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.library.example.LibraryBootApp.models.Book;

import java.util.List;


@Repository
public interface BooksRepositories extends JpaRepository<Book, Integer> {
    List<Book> findBookByNameStartingWith(String name);
}
