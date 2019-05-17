package com.comic.Repository;

import com.comic.model.Series;
import com.comic.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
}
