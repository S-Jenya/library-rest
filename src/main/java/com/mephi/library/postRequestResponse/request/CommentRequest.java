package com.mephi.library.postRequestResponse.request;

public class CommentRequest {
    private Long idUser;
    private Long idBook;
    private String text;

    public Long getIdUser() {
        return idUser;
    }

    public Long getIdBook() {
        return idBook;
    }

    public String getText() {
        return text;
    }
}
