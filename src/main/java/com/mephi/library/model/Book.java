package com.mephi.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(
        name = "Book",
        uniqueConstraints = {
                @UniqueConstraint(name = "book_name_unique", columnNames = "name")}
)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBook")
    private Long idBook;

    @Column(name = "name")
    private String name;

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

    public boolean imageIsExist() {
        if(this.getImage() == null) {
            return false;
        } else {
            return true;
        }
    }

    /*@ManyToMany(cascade = { CascadeType.ALL },
            fetch = FetchType.EAGER,
            mappedBy = "myUser")*/
//    @JsonIgnore
//    private User user;
}
