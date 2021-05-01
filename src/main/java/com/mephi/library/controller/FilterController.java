package com.mephi.library.controller;

import com.mephi.library.postRequestResponse.response.BookInfoResponse;
import com.mephi.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FilterController {
    private final BookService bookService;

    public FilterController(BookService bookService) {
        this.bookService = bookService;
    }

    // mode -
    //      all             - все книги
    //      byName          - фильр книги по имени
    //      byGenreName     - фильтр книг по жанру
    @GetMapping(value = "/book/searchBook/{mode}/{strSearch}")
    public  ResponseEntity<List<BookInfoResponse>> filterBook(@PathVariable String mode, @PathVariable String strSearch) {
        List<BookInfoResponse> files = bookService.FilterBook(mode, strSearch).map(book -> {
            boolean flag = book.imageIsExist();
            if (flag) {
                String fileDownloadUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/cards/getImg/")
                        .path(book.getIdBook().toString())
                        .toUriString();

                return new BookInfoResponse(
                        book.getIdBook(),
                        true,
                        book.getName(),
                        book.getYear(),
                        book.getDescription(),
                        book.getAuthor().getLastName() + " " + book.getAuthor().getFirstName() + " " + book.getAuthor().getPatronymic(),
                        book.getGenre().getName(),
                        fileDownloadUri,
                        book.getComments()
                );
            } else {
                return new BookInfoResponse(
                        book.getIdBook(),
                        false,
                        book.getName(),
                        book.getYear(),
                        book.getDescription(),
                        book.getAuthor().getFirstName() + " " + book.getAuthor().getLastName() + " " + book.getAuthor().getPatronymic(),
                        book.getGenre().getName(),
                        "https://www.googleapis.com/books/v1/volumes?q=" + book.getName(),
                        book.getComments()
                );
            }

        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }
}
