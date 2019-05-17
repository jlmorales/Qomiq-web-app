package com.comic.Service;

import com.comic.Repository.SubmissionRepository;
import com.comic.model.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("SubmissionService")
public class SubmissionService {

    SubmissionRepository submissionRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository){this.submissionRepository = submissionRepository;}

    public Submission saveSubmission(Submission submission){return submissionRepository.save(submission);}
}
