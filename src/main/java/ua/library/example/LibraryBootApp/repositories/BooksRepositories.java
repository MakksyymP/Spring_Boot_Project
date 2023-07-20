package ua.library.example.LibraryBootApp.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.library.example.LibraryBootApp.models.Book;




@Repository
public interface BooksRepositories extends JpaRepository<Book, Integer> {
    Book findBookByNameStartingWith(String title);
}
