package com.mephi.library.controller;

import com.mephi.library.model.Author;
import com.mephi.library.model.Book;
import com.mephi.library.model.Genre;
import com.mephi.library.service.AuthorService;
import com.mephi.library.service.BookService;
import com.mephi.library.service.GenreService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BookController {

    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;

    public BookController(GenreService genreService, AuthorService authorService, BookService bookService) {
        this.genreService = genreService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

  /*  @Bean
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }

    @Bean
    public MultipartResolver multipartResolver() {
        org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }*/

    @PostMapping(value = "/admin/uploadBook")
    public void UploadBookOne(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "genre") Long idGenre,
            @RequestParam(name = "author") Long idAuthor,
            @RequestParam(name = "fileImage") MultipartFile img,
            @RequestParam(name = "fileContent") MultipartFile cont) {
        try {
            Book book = new Book();
            book.setName(name);
            book.setDescription(description);
            Genre genre = genreService.findGenreById(idGenre);
            book.setGenre(genre);
            Author author = authorService.getAuthorById(idAuthor);
            book.setAuthor(author);
            book.setImage(img.getBytes());
            book.setContent(cont.getBytes());
            bookService.saveBook(book);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
        }
    }

}
