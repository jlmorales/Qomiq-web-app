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
@Table(name = "gamePlayer")
public class GamePlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "player_id")
    private int id;

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "user_id")
    private int userId;
}
