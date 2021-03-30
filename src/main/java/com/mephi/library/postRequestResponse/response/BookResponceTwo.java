package com.mephi.library.postRequestResponse.response;

public class BookResponceTwo {

    private Long id;
    private String name;
    private String Author;

    public BookResponceTwo(Long id, String name, String author) {
        this.id = id;
        this.name = name;
        Author = author;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return Author;
    }
}
