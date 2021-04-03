package com.mephi.library.model;

import lombok.Data;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
@SequenceGenerator(name = "comment_sequence_generator", sequenceName = "comment_sequence", initialValue = 1, allocationSize = 1)
@Table(name = "comment")
public class Comment {
//    @EmbeddedId
//    UserCommetKey id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,  generator="comment_sequence_generator")
    @Column(name = "idComment")
    private Long idComment;

    @ManyToOne
//    @MapsId("idUser")
    @JoinColumn(name = "idUser")
    User user;

    @ManyToOne
//    @MapsId("idBook")
    @JoinColumn(name = "idBook")
    Book book;

    //    @MapsId("date")
    String date;

    @Column(name = "text")
    String text;

    public void setCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        this.date = simpleDateFormat.format(new Date());
    }
}
