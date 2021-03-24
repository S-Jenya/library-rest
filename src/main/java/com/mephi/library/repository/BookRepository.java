package com.mephi.library.repository;

import com.mephi.library.model.Author;
import com.mephi.library.model.Book;
import com.mephi.library.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Long countBookByGenre(Genre genre);
    Long countBookByAuthor(Author author);
}
