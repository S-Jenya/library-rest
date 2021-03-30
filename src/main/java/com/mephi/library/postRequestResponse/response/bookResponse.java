package com.mephi.library.postRequestResponse.response;

public class bookResponse {
    private Long id;
    private boolean fromData;
    private String name;
    private String description;
    private String author;
    private String genre;
    private String url;

    public bookResponse(Long id, boolean fromData, String name, String description, String author, String genre, String url) {
        this.id = id;
        this.fromData = fromData;
        this.name = name;
        this.description = description;
        this.author = author;
        this.genre = genre;
        this.url = url;
    }

    public boolean isFromData() {
        return fromData;
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

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getUrl() {
        return url;
    }
}
