package com.mephi.library.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@SequenceGenerator(name = "comment_sequence_generator", sequenceName = "comment_sequence", initialValue = 1, allocationSize = 1)
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator="comment_sequence_generator")
    @Column(name = "idComment")
    private Long idComment;

    @ManyToOne
    @JoinColumn(name = "idUser")
    User user;

    @ManyToOne
    @JoinColumn(name = "idBook")
    Book book;

    @Column(length = 11)
    String date;

    @Column(name = "text")
    @Size(min = 1, max = 255)
    String text;

    public void setCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        this.date = simpleDateFormat.format(new Date());
    }
}
