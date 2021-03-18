package com.mephi.library.controller;

import com.mephi.library.model.Author;
import com.mephi.library.postRequestResponse.request.AuthorAddRequest;
import com.mephi.library.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/admin/getAuthors")
    public List<Author> getAuthors() {
        return authorService.findAllAuthors();
    }

    @RequestMapping(value = "/admin/addAuthor", method = RequestMethod.POST)
    public void addAuthors(@RequestBody AuthorAddRequest authorAddRequest) {
        try {
            Author author = new Author();
            author.setFirstName(authorAddRequest.getFirstName());
            author.setLastName(authorAddRequest.getLastName());
            author.setPatronymic(authorAddRequest.getPatronymic());
            authorService.createAuthor(author);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
        }
    }
}
