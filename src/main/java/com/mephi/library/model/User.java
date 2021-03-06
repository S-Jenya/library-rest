package com.mephi.library.model;

import lombok.Data;
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

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;
}
