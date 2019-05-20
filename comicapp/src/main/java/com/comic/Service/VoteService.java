package com.comic.Service;

import com.comic.Repository.VoteRepository;
import com.comic.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("voteService")
public class VoteService {
    VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository){this.voteRepository = voteRepository;}

    public Vote saveVote(Vote vote){return  this.voteRepository.save(vote);}

    public void deleteVote(Vote vote){this.voteRepository.delete(vote);}

    public Vote findVoteBySubmissionIdAndVoterUsername(int submissionId, String voterUsername){
        return this.voteRepository.findVoteBySubmissionIdAndVoterUsername(submissionId,voterUsername);}

    public Vote findVoteByGamePageIdAndVoterUsername(int gamePageId, String voterUsername){
        return this.voteRepository.findVoteByGamePageIdAndVoterUsername(gamePageId, voterUsername);
    }
}
