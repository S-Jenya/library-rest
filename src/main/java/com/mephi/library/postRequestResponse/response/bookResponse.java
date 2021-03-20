package com.mephi.library.postRequestResponse.response;

public class bookResponse {
    private Long id;
    private String name;
    private String Author;
    private String url;

    public bookResponse(Long id, String name, String author, String url) {
        this.id = id;
        this.name = name;
        Author = author;
        this.url = url;
    }

    public String getAuthor() {
        return Author;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
