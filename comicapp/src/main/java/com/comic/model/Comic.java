package com.comic.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Comic")
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comic_id")
    private int id;

    @Column(name = "comic_title")
    private String comicTitle;

    @Column(name = "comic_seriesId")
    private int seriesId;

    //time is stored in an int and 24-hour format. For example, 1742 represents 17:42 or 5:42 pm
    @Column(name = "comic_creationTime")
    private int creationTime;

    //date is stored in an int format. For example, 20190801 represents August 1st, 2019.
    @Column(name = "comic_creationDate")
    private int creationDate;

    @Column(name = "comic_lastModTime")
    private int lastModTime;

    @Column(name = "comic_lastModDate")
    private int lastModDate;

    @Column(name = "comic_likes")
    private int likes;

    @Column(name = "comic_views")
    private int comicViews;

    @Column(name = "is_public")
    private boolean publicComic;

}
