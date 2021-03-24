package com.mephi.library.controller;

import com.mephi.library.model.Author;
import com.mephi.library.postRequestResponse.request.AuthorAddRequest;
import com.mephi.library.postRequestResponse.response.MessageResponse;
import com.mephi.library.service.AuthorService;
import com.mephi.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @GetMapping("/admin/getAuthors")
    public List<Author> getAuthors() {
        return authorService.findAllAuthors();
    }

    @RequestMapping(value = "/admin/addAuthor", method = RequestMethod.POST)
    public ResponseEntity<?> addAuthors(@RequestBody AuthorAddRequest authorAddRequest) {
        try {
            Author author = new Author();
            author.setFirstName(authorAddRequest.getFirstName());
            author.setLastName(authorAddRequest.getLastName());
            author.setPatronymic(authorAddRequest.getPatronymic());
            authorService.createAuthor(author);
        } catch (Exception ex) {
            String strEx = ex.getCause().getCause().getMessage();
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(strEx));
        }
        return ResponseEntity.ok().body(new MessageResponse("Автор: \"" + authorAddRequest.getFirstName() + " "
                + authorAddRequest.getFirstName() + " "
                + authorAddRequest.getPatronymic() + "\" успешно добавлен"));
    }

    @RequestMapping(value = "/admin/editAuthor", method = RequestMethod.POST)
    public ResponseEntity<?> updateAuthor(@RequestBody AuthorAddRequest authorAddRequest) {
        try {
            authorService.updateAuthorName(authorAddRequest.getId(),
                    authorAddRequest.getFirstName(),
                    authorAddRequest.getLastName(),
                    authorAddRequest.getPatronymic());
        } catch (Exception ex) {
            String strEx = ex.getCause().getCause().getMessage();
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(strEx));
        }

        return ResponseEntity.ok().body(new MessageResponse("Автор: \"" + authorAddRequest.getFirstName() + " "
                + authorAddRequest.getFirstName() + " "
                + authorAddRequest.getPatronymic() + "\" успешно обновлён"));
    }

    @DeleteMapping(value = "/admin/deleteAuthor/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        Author author;
        try {
            author = authorService.getAuthorById(id);
            Long count = bookService.countBookByAuthor(author);
            if(count > 0) {
                return ResponseEntity.badRequest().body(new MessageResponse("Автор: \"" + author.getFirstName() + " "
                        + author.getFirstName() + " "
                        + author.getPatronymic() + "\" используется в книгах"));
            }
            authorService.deleteAuthorById(id);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка! Подробнее: " + ex.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("Автор: \"" + author.getFirstName() + " "
                + author.getFirstName() + " "
                + author.getPatronymic() + "\" успешно удалён"));
    }
}
