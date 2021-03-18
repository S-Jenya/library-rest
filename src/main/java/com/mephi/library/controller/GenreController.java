package com.mephi.library.controller;

import com.mephi.library.model.Genre;
import com.mephi.library.postRequestResponse.Data;
import com.mephi.library.service.GenreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/admin/getGenres")
    public List<Genre> getGenres() {
        return genreService.getAllGenre();
    }

    @RequestMapping(value = "/admin/addGenre", method = RequestMethod.POST)
    public void addGenre(@RequestBody Data data) {
        try{
            Genre genre = new Genre();
            genre.setName(data.getName());
            genreService.createGenre(genre);
        } catch (Exception ex) {
            System.out.println("Ошибка! Подробнее: " + ex.getMessage());
        }
    }
}
