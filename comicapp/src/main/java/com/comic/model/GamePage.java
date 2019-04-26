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
@Table(name = "gamePage")
public class GamePage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_page_id")
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "page_number")
    private int pageNumber;

    @Column(name = "page_key")
    private String pageKey;


}
