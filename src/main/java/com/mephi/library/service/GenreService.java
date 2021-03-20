package com.mephi.library.service;

import com.mephi.library.model.Genre;
import com.mephi.library.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<Genre> getAllGenre() {
        return genreRepository.findAll();
    }

    public void createGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public void updateNameGenre(Long idGenre, String name) {
        genreRepository.updGenreName(name, idGenre);
    }

    public Genre findGenreById(Long idGenre) {
        return genreRepository.findGenreByIdCustomQuery(idGenre);
    }

    public Genre findGenreByName(String name) {
        return genreRepository.findGenreByName(name);
    }
}
