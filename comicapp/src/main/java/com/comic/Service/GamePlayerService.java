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
    public GamePlayerService(GamePlayerRepository gamePlayerRepository){this.gamePlayerRepository = gamePlayerRepository;}

    public List<GamePlayer> findGamePlayersByGameId(int id){ return gamePlayerRepository.findAllByGameId(id);}

    public GamePlayer findGamePlayerById(int id){return gamePlayerRepository.findGamePlayerById(id);}

    public GamePlayer findGamePlayerByUserId(int id){return gamePlayerRepository.findGamePlayerByUserId(id);}

    public GamePlayer savePlayer(GamePlayer gamePlayer){ return this.gamePlayerRepository.save(gamePlayer);}
}
