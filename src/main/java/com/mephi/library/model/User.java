package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
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

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "UserBook",
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

    public void clearBookList() {
        this.books.clear();
    }
}
