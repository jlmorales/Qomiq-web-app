package com.comic.Repository;

import com.comic.model.Series;
import com.comic.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

    List<Submission> findAllByGamePageId(int id);

    Submission findSubmissionByGamePageIdAndPlayerId(int gamePageId, int playerId);
}
