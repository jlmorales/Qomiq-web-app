package com.comic.Service;

import com.comic.Repository.GamePageRepository;
import com.comic.model.GamePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GamePageService")
public class GamePageService {

    GamePageRepository gamePageRepository;

    @Autowired
    GamePageService(GamePageRepository gamePageRepository){this.gamePageRepository = gamePageRepository;}

    public List<GamePage> findGamePagesByGameId(int id){return gamePageRepository.findAllByGameId(id);}

    public GamePage saveGamePage(GamePage gamePage){return gamePageRepository.save(gamePage);}
}
