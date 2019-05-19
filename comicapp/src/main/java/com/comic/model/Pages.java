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
@Table(name = "pages")
public class Pages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pages_id")
    private int id;

    @Column(name = "comic_id")
    private int comicId;

}
