package com.comic.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comicLike")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "like_id")
    private int likeId;

    @Column(name = "liker_username")
    private String likerUsername;

    @Column(name = "comicId")
    private int comicId;

}


