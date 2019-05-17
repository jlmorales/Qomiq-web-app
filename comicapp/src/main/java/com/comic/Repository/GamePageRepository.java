package com.comic.Repository;

import com.comic.model.GamePage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("gamePageRepository")
public interface GamePageRepository extends JpaRepository<GamePage, Integer> {

    List<GamePage> findAllByGameId(int id);

    GamePage findGamePageById(int id);
}
