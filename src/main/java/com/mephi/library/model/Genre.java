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
        name = "Genre",
        uniqueConstraints = {
                @UniqueConstraint(name = "genre_name_unique", columnNames = "name")}
)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenre")
    private Long idGenre;

    @NotBlank(message = "Поле \"name\" не может быть пустым")
    @Column(name = "name", length = 30)
    @Size(min = 1, max = 30)
    private String name;

    @OneToMany(
            mappedBy = "genre",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private List<Book> Book;
}
