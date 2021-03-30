package com.mephi.library.repository;

import com.mephi.library.model.Author;
import com.mephi.library.model.Book;
import com.mephi.library.model.Genre;
import com.mephi.library.postRequestResponse.DB.BookDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Long countBookByGenre(Genre genre);
    Long countBookByAuthor(Author author);

    @Query("select b.image from Book b where b.idBook = ?1")
    byte findBookImageByIdBook(Long idBook);

    @Query("select new com.mephi.library.postRequestResponse.DB.BookDB(b.idBook, b.name, b.description, b.author, b.genre) from Book b where b.idBook = ?1")
    BookDB findBookByIdCustomQuery(Long idBook);

//    byte findBookImageByIdBook(Long idBook);
}
