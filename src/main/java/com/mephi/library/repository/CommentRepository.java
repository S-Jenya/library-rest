package com.mephi.library.repository;

import com.mephi.library.model.Book;
import com.mephi.library.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBook(Book book);

    @Query(value = "select c.*\n" +
            "         from book b\n" +
            "                  join comment c on b.id_book = c.id_book\n" +
            "         where b.id_book = ?1",
    nativeQuery = true)
    List<Comment> findListCommentsByIdBook(Long idBook);
}
