package com.comic.Service;

import com.comic.Repository.SubmissionRepository;
import com.comic.model.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SubmissionService")
public class SubmissionService {

    SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository){this.submissionRepository = submissionRepository;}

    public Submission findSubmissionById(int id){return this.submissionRepository.findSubmissionById(id);}

    public List<Submission> findAllSubmissionsByGamePageId(int id){return this.submissionRepository.findAllByGamePageId(id);}

    public Submission saveSubmission(Submission submission){return submissionRepository.save(submission);}

    public Submission findSubmissionByGamePageIdAndPlayerId(int gamePageId, int playerId){return submissionRepository.findSubmissionByGamePageIdAndPlayerId(gamePageId, playerId);}
}
