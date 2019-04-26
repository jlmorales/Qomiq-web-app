package com.comic.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_Id")
    private int commentId;

    @Column(name="commentor_Id")
    private int commenterId;

    @Column(name="commentContent")
    private  String commentContent;

    @Column(name = "comic_Id")
    private int comicId;
}
