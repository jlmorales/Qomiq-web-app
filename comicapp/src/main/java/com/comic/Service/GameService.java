package com.comic.Service;

import com.comic.Repository.GameRepository;
import com.comic.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GameService")
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    GameService(GameRepository gameRepository){this.gameRepository = gameRepository;}

    public Game saveGame(Game game){ return this.gameRepository.save(game);}

    public Game findGameById(int id){return this.gameRepository.findGameById(id);}

    public List<Game> findAllGamesByUserId(int id){return this.gameRepository.findAllByUserId(id);}

    public List<Game> findAllGames(){return this.gameRepository.findAll();}

}
