/*
package com.mephi.library.repository;

import com.mephi.library.postRequestResponse.DB.BookDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookDataRepository extends JpaRepository<BookDB, Long> {

    @Query("select b.idBook, b.name, b.description, b.author, b.genre from Book b where b.idBook = ?1")
    BookDB findBookByIdCustomQuery(Long idBook);
}
*/
