package com.mephi.library.controller;

import com.mephi.library.model.Author;
import com.mephi.library.model.Book;
import com.mephi.library.model.Genre;
import com.mephi.library.postRequestResponse.response.bookResponse;
import com.mephi.library.service.AuthorService;
import com.mephi.library.service.BookService;
import com.mephi.library.service.GenreService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/cards/getListCards")
    public ResponseEntity<List<bookResponse>> getListCards() {
        List<bookResponse> files = bookService.getAllBook().map(book -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/cards/getImg/")
                    .path(book.getIdBook().toString())
                    .toUriString();

            return new bookResponse(
                    book.getIdBook(),
                    book.getName(),
                    book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName() + " " + book.getAuthor().getPatronymic(),
                    fileDownloadUri);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/cards/getImg/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
            Book book = bookService.getFile(Long.parseLong(id));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + book.getName() + ".jpg\"")
                    .body(book.getImage());
    }

}
