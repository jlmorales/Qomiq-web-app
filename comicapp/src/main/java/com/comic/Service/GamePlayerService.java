package com.comic.Service;

import com.comic.Repository.GamePlayerRepository;
import com.comic.model.GamePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GamePlayerService")
public class GamePlayerService {

    GamePlayerRepository gamePlayerRepository;

    @Autowired
    GamePlayerService(GamePlayerRepository gamePlayerRepository){this.gamePlayerRepository = gamePlayerRepository;}

    public List<GamePlayer> findGamePlayersByGameId(int id){ return gamePlayerRepository.findAllByGameId(id);}

    public GamePlayer savePlayer(GamePlayer gamePlayer){ return this.gamePlayerRepository.save(gamePlayer);}
}
