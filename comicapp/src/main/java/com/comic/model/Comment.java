package com.comic.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_Id")
    private int commentId;

    @Column(name="profileUsername")
    private String profileUsername;

    @Column(name="commentorUsername")
    private String commentorUsername;

    @Column(name="commnent_content")
    private String comment_content;
}
