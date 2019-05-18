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
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "voteId")
    private int voteId;

    @Column(name = "pageId")
    private int pageId;

    @Column(name = "voterUsername")
    private int voterUsername;
}
