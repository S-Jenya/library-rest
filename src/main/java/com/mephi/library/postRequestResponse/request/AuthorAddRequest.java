package com.mephi.library.postRequestResponse.request;

public class AuthorAddRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
