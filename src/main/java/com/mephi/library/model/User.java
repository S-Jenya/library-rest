package com.mephi.library.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "myUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long idUser;

    @Column(
            name = "name",
            unique = true
    )
    private String name;

    @Column(
            name = "email",
            unique = true
    )
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;

}
