package com.mephi.library.postRequestResponse.response;

import com.mephi.library.model.Comment;

import java.util.List;
import java.util.Set;

public class BookInfoResponse {
    private Long id;
    private boolean fromData;
    private String name;
    private String description;
    private String author;
    private String genre;
    private String url;
    private List<Comment> comments;

    public BookInfoResponse(Long id, boolean fromData, String name, String description, String author, String genre, String url, List<Comment> comments) {
        this.id = id;
        this.fromData = fromData;
        this.name = name;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.url = url;
        this.comments = comments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFromData(boolean fromData) {
        this.fromData = fromData;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
