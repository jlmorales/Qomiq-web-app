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
@Table(name = "GamePage")
public class GamePage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pageId")
    private int pageId;

    @Column(name = "roundId")
    private int roundId;

    @Column(name = "votes")
    private int votes;

    @Column(name = "content_ref")
    private String content_ref;

    @Column(name = "playerId")
    private String playerId;

}
