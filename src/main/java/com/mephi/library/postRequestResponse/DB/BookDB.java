package com.mephi.library.postRequestResponse.DB;

import com.mephi.library.model.Author;
import com.mephi.library.model.Genre;

public class BookDB {
    private Long id;
    private String name;
    private String description;
    private Author author;
    private Genre genre;

    public BookDB(Long id, String name, String description, Author author, Genre genre) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.author = author;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Author getAuthor() {
        return author;
    }

    public Genre getGenre() {
        return genre;
    }
}
