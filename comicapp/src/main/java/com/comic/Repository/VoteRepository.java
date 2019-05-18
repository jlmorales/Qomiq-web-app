package com.comic.Repository;

import com.comic.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("voteRepository")
public interface VoteRepository extends JpaRepository<Vote, Long> {

    public Vote findVoteBySubmissionIdAndVoterUsername(int submissionId, String voterUsername);

    public Vote findVoteByGamePageIdAndVoterUsername(int gamePageId, String voterUsername);
}
