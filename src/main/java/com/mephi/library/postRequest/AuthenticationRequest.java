package com.mephi.library.postRequest;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {
    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //need default constructor for JSON Parsing
    public AuthenticationRequest()
    {

    }

    public AuthenticationRequest(String name, String password) {
        this.setName(name);
        this.setPassword(password);
    }
}
