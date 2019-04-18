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
    @Column(name = "comicLike_id")
    private int likeId;

    @Column(name = "liker_username")
    private String likerUsername;

//    @Column(name = "comicLike_seriesId")
//    private String seriesId;

    @Column(name = "comicId")
    private String comicId;

}


