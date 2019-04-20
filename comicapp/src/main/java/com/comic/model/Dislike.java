package com.comic.model;


import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Dislike")
public class Dislike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Dislike_Id")
    private int DislikeId;

    @Column(name = "Disliker_Username")
    private String liker_username;

    @Column(name = "Disliker_seriesId")
    private String seriesId;

    @Column(name = "Disliker_comicId")
    private String comicId;
}
