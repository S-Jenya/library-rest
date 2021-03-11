package com.mephi.library.postRequest;

import java.io.Serializable;
import java.util.List;

public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final String name;
    private final Long idUser;
    private final List<String> roles;

    public AuthenticationResponse(Long idUser, String name, List<String> roles, String jwt) {
        this.idUser = idUser;
        this.name = name;
        this.jwt = jwt;
        this.roles = roles;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public List<String> getRoles() {
        return roles;
    }

    public String getJwt() {
        return jwt;
    }

}
