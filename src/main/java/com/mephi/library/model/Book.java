package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(
        name = "Book",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "book_name_year_unique",
                        columnNames = {"name", "year"}
                )
        }
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBook")
    private Long idBook;

    @NotBlank(message = "Поле \"name\" не может быть пустым")
    @Column(name = "name", length = 150)
    @Size(min = 1, max = 150)
    private String name;

    @NotNull(message = "Поле \"year\" не может быть пустым")
    @Column(name = "year")
    @Range(min=0, max=3000)
    private Integer year;

    @Size(min = 1, max = 255)
    private String description;

    @Lob
    private byte[] image;

    @Lob
    private byte[] content;

    @ManyToOne
    @JoinColumn(name = "idGenre", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "idAuthor", nullable = false)
    private Author author;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "books", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<User> user;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comments;

    public boolean imageIsExist() {
        if (this.getImage() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void clearUserList() {
        this.user.clear();
    }

}
