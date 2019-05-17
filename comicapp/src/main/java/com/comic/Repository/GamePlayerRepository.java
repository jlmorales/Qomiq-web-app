package com.comic.Repository;

import com.comic.model.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("gamePlayerRepository")
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Integer> {

    List<GamePlayer> findAllByGameId(int id);

    GamePlayer findGamePlayerById(int id);

    GamePlayer findGamePlayerByUserId(int id);
}
