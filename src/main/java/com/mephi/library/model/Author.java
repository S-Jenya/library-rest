package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(
        name = "Author",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "author_name_unique",
                        columnNames = {"lastName", "firstName", "patronymic"}
                )
        }
)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAuthor")
    private Long idAuthor;

    @NotBlank(message = "Поле \"lastName\" не может быть пустым")
    @Column(name = "lastName", length = 30)
    @Size(min = 1, max = 30)
    private String lastName;

    @NotBlank(message = "Поле \"firstName\" не может быть пустым")
    @Column(name = "firstName", length = 30)
    @Size(min = 1, max = 30)
    private String firstName;

    @NotBlank(message = "Поле \"patronymic\" не может быть пустым")
    @Column(name = "patronymic", length = 30)
    @Size(min = 1, max = 30)
    private String patronymic;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private List<Book> books;
}
