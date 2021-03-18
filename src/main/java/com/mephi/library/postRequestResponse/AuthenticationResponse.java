package com.mephi.library.postRequestResponse;

import java.io.Serializable;
import java.util.List;

public class AuthenticationResponse implements Serializable {

    private final Long idUser;
    private final String name;
    private final String login;
    private final String email;
    private final List<String> roles;
    private final String jwt;

    public AuthenticationResponse(Long idUser, String name, String login, String email, List<String> roles, String jwt) {
        this.idUser = idUser;
        this.name = name;
        this.login = login;
        this.email = email;
        this.roles = roles;
        this.jwt = jwt;
    }

    public String getEmail() {
        return email;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getJwt() {
        return jwt;
    }

}
