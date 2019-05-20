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
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private int id;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "current_page")
    private int currentPage;

    @Column(name = "game_finished")
    private boolean finished;

    @Column(name = "winner")
    private int winner;

    @Column(name= "comicId")
    private int comicId;

}
