package com.mephi.library.service;

import com.mephi.library.model.Book;
import com.mephi.library.model.Comment;
import com.mephi.library.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getCommentByBookId(Book book) {
        return commentRepository.findAllByBook(book);
    }

    public List<Comment> findListCommentsByIdBook(Long idBook) {
        return commentRepository.findListCommentsByIdBook(idBook);
    }

    public void deleteCommentById(Long idComment) {
        commentRepository.deleteById(idComment);
    }

    public Comment getCommentById(Long idComment) {
        return commentRepository.findById(idComment).get();
    }
}
