package com.mephi.library.postRequestResponse.response;

import com.mephi.library.model.Role;

public class UserInfo {
    private Long idUser;

    private String name;

    private String email;

    private String login;

    private Role role;

    public UserInfo(Long idUser, String name, String email, String login, Role role) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.login = login;
        this.role = role;
    }

    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public Role getRole() {
        return role;
    }
}
