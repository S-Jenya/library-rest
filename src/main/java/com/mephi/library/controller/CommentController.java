package com.mephi.library.controller;

import com.mephi.library.model.Book;
import com.mephi.library.model.Comment;
import com.mephi.library.model.User;
import com.mephi.library.postRequestResponse.request.CommentEditRequest;
import com.mephi.library.postRequestResponse.request.CommentRequest;
import com.mephi.library.postRequestResponse.response.MessageResponse;
import com.mephi.library.service.BookService;
import com.mephi.library.service.CommentService;
import com.mephi.library.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final BookService bookService;

    public CommentController(CommentService commentService, UserService userService, BookService bookService) {
        this.commentService = commentService;
        this.userService = userService;
        this.bookService = bookService;
    }

    @RequestMapping(value = "/comment/addComment", method = RequestMethod.POST)
    public ResponseEntity<?> addComment(@RequestBody CommentRequest data) {
        User user = userService.findUserById(data.getIdUser());
        Book book = bookService.getBookById(data.getIdBook());

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setBook(book);
        comment.setCurrentDate();
        comment.setText(data.getText());

        try {
            commentService.saveComment(comment);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка! Подробнее: " + ex.getMessage()));
        }

        return ResponseEntity.ok().body(new MessageResponse("Комментарий успешно добавлен"));
    }

    @RequestMapping(value = "/comment/editComment", method = RequestMethod.POST)
    public ResponseEntity<?> editComment(@RequestBody CommentEditRequest data) {
        try {
            Comment comment = commentService.getCommentById(data.getIdComment());
            comment.setText(data.getText());
            commentService.saveComment(comment);
        } catch (Exception ex) {
            String strEx = ex.getCause().getCause().getMessage();
            return ResponseEntity.badRequest().body(new MessageResponse(strEx));
        }

        return ResponseEntity.ok().body(new MessageResponse("Комментарий успешно обновлён"));
    }

    @GetMapping("/comment/delComment/{id}")
    public ResponseEntity<?> getFile(@PathVariable Long id) {
        try {
            commentService.deleteCommentById(id);
        } catch (Exception ex) {
            String strEx = ex.getCause().getCause().getMessage();
            return ResponseEntity.badRequest().body(new MessageResponse(strEx));
        }

        return ResponseEntity.ok().body(new MessageResponse("Комментарий успешно удалён"));
    }
}
