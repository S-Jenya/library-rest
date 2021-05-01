package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(
        name = "myUser"
        , uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "user_login_unique", columnNames = "login")
        }
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long idUser;

    @NotBlank(message = "Поле \"name\" не может быть пустым")
    @Column(name = "name", length = 30)
    @Size(min = 1, max = 30)
    private String name;

    @NotBlank(message = "Поле \"email\" не может быть пустым")
    @Column(name = "email", length = 60)
    @Size(min = 1, max = 60)
    private String email;

    @NotBlank(message = "Поле \"login\" не может быть пустым")
    @Column(name = "login", length = 30)
    @Size(min = 1, max = 30)
    private String login;

    @NotBlank(message = "Поле \"password\" не может быть пустым")
    @Column(name = "password", length = 255)
    @Size(min = 4, max = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "downloads",
            joinColumns = { @JoinColumn(name = "idUser") },
            inverseJoinColumns = { @JoinColumn(name = "idBook") }
    )
    private Set<Book> books;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comments;

    public void setRole(Role role) {
        this.role = role;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
