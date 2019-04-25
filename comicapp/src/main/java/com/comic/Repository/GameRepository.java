package com.comic.Repository;

import com.comic.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("gameRepository")
public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findGameById(int id);

    List<Game> findAllByUserId(int id);

}
