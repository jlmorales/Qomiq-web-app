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
@Table(name = "series")
public class series {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "series_id")
    private int id;

    @Column(name = "series_title")
    private String title;

    @Column(name = "series_category")
    private String category;

    @Column(name = "series_authorUsername")
    private String authorUsername;

    //time is stored in an int and 24-hour format. For example, 1742 represents 17:42 or 5:42 pm
    @Column(name = "series_creationTime")
    private int creationTime;

    //date is stored in an int format. For example, 20190801 represents August 1st, 2019.
    @Column(name = "series_creationDate")
    private int creationDate;

    @Column(name = "series_lastModTime")
    private int lastModTime;

    @Column(name = "series_lastModDate")
    private int lastModDate;


}
