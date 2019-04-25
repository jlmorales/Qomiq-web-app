package com.comic.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dislike")
public class Dislike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "dislike_Id")
    private int dislikeId;

    @Column(name = "disliker_Username")
    private String dislikerUsername;

    @Column(name = "disliker_comicId")
    private int comicId;
}
