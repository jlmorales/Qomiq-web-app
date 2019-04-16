package com.comic.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_Id")
    private int commentId;

    @Column(name="profileUsername")
    private String profileUsername;

    @Column(name="commentorUsername")
    private  String commentorUsername;

    @Column(name="comicContent")
    private  String comicContent;

    @Column(name = "comic_Id")
    private int comicId;
}
