package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(
        name = "Author",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "author_name_unique",
                        columnNames = {"firstName", "lastName", "patronymic"}
                )
        }
)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAuthor")
    private Long idAuthor;

    @NotBlank(message = "Поле \"firstName\" не может быть пустым")
    @Column(name = "firstName")
    private String firstName;

    @NotBlank(message = "Поле \"lastName\" не может быть пустым")
    @Column(name = "lastName")
    private String lastName;

    @NotBlank(message = "Поле \"patronymic\" не может быть пустым")
    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private List<Book> books;
}
