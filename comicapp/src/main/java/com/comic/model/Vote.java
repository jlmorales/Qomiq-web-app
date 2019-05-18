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
    @Column(name = "VoteId")
    private int voteId;

    @Column(name = "submissionId")
    private int submissionId;

    @Column(name = "voterUsername")
    private String voterUsername;
}
