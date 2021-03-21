package com.mephi.library.controller;

import com.mephi.library.model.Genre;
import com.mephi.library.postRequestResponse.Data;
import com.mephi.library.postRequestResponse.response.MessageResponse;
import com.mephi.library.service.BookService;
import com.mephi.library.service.GenreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;
    private final BookService bookService;

    public GenreController(GenreService genreService, BookService bookService) {
        this.genreService = genreService;
        this.bookService = bookService;
    }

    @GetMapping("/getGenres")
    public List<Genre> getGenres() {
        return genreService.getAllGenre();
    }

    @RequestMapping(value = "/admin/addGenre", method = RequestMethod.POST)
    public ResponseEntity<?> addGenre(@RequestBody Data data) {
        try{
            Genre isGenre = genreService.findGenreByName(data.getName());
            if(isGenre != null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Жанр с именем: \"" + data.getName() + "\" уже существует"));
            }
            Genre genre = new Genre();
            genre.setName(data.getName());
            genreService.createGenre(genre);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка! Подробнее: " + ex.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("Жанр с именем: \"" + data.getName() + "\" успешно создан"));
    }

    @RequestMapping(value = "/admin/updateGenre", method = RequestMethod.POST)
    public ResponseEntity<?> updateGenre(@RequestBody Data data) {
        try {
            Genre genre = genreService.findGenreByName(data.getName());
            if((genre != null) && (genre.getIdGenre() != data.getId())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Жанр с именем: \"" + data.getName() + "\" уже существует"));
            }
            genreService.updateNameGenre(data.getId(), data.getName());
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка! Подробнее: " + ex.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("Жанр с именем: \"" + data.getName() + "\" успешно обновлён"));
    }

    @DeleteMapping(value = "/admin/deleteGenre/{id}")
    public ResponseEntity<?> deleteGenre(@PathVariable Long id) {
        Genre genre;
        try {
            genre = genreService.findGenreById(id);
            Long count = bookService.countBookByGenre(genre);
            if(count > 0) {
                return ResponseEntity.badRequest().body(new MessageResponse("Жанр с именем: \"" + genre.getName() + "\" используется!"));
            }
            genreService.deleteGenreById(id);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Ошибка! Подробнее: " + ex.getMessage()));
        }
        return ResponseEntity.ok().body(new MessageResponse("Жанр с именем: \"" + genre.getName() + "\" успешно удалён"));
    }

}
