package com.mephi.library.repository;

import com.mephi.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Author author set author.firstName = ?2, author.lastName = ?3, author.patronymic = ?4 where author.idAuthor = ?1")
    void updAuthorName(Long authorId, String firstName, String lastName, String patronymic);

    Author getAuthorByIdAuthor(Long idAuthor);
}
