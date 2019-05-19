package com.comic.Controllers;

import com.amazonaws.services.s3.model.S3Object;
import com.comic.Bean.BASE64DecodedMultipartFile;
import com.comic.Service.*;
import com.comic.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class FileController {

    @Autowired
    S3Services s3Services;

    @Autowired
    SeriesService seriesService;

    @Autowired
    ComicService comicService;

    @Autowired
    UserService userService;

    @Autowired
    GamePageService gamePageService;

    @Autowired
    GameService gameService;

    @Autowired
    GamePlayerService gamePlayerService;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    PagesService pagesService;

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file ,
                             @RequestParam("pngFile") String pngFile,
                             @RequestParam("seriesName") String seriesName,
                             @RequestParam("comicName") String comicName,
                             @RequestParam("makePublic") boolean makePublic,
                             @RequestParam("enableComments") boolean enableComments)
                             {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        Series series = new Series();
        series.setAuthorUsername(user.getUsername());
        series.setCategory("Action");
        Date date = new Date();
        series.setCreationDate(date);
        series.setLastModDate(date);
        series.setSeriesViews(0);
        series.setTitle(seriesName);
        List<Series> seriesList = seriesService.findAllSeriesByAuthorUsername(user.getUsername());
        boolean seriesExists= false;
        for(Series s : seriesList){
            if(s.getTitle().equalsIgnoreCase(seriesName)){
                series = s;
                seriesExists = true;
            }
        }
        series = seriesService.saveSeries(series);
        System.out.println(series);
        Comic comic = new Comic();
        comic.setCommentsEnabled(enableComments);
        comic.setPublicComic(makePublic);
        comic.setCreationDate(date);
        comic.setLastModDate(date);
        comic.setComicViews(0);
        comic.setLikes(0);
        comic.setComicTitle(comicName);
        comic.setComicViews(0);
        comic.setSeriesId(series.getId());
        comic = comicService.saveComic(comic);
        System.out.println(comic);
        Pages page = new Pages();
        page.setComicId(comic.getId());
        pagesService.savePage(page);
        String keyName = "series"+series.getId()+"comic"+comic.getId()+ "page" + page.getId() + ".json";
        s3Services.uploadFile(keyName, file);
        keyName = "series"+series.getId()+"comic"+comic.getId()+ "page" + page.getId() + ".png";
        System.out.println(pngFile);
        byte[] imagedata = DatatypeConverter.parseBase64Binary(pngFile.substring(pngFile.indexOf(",")+1));
        BASE64DecodedMultipartFile realFile = new BASE64DecodedMultipartFile(imagedata);
        s3Services.uploadFile(keyName, realFile);
        if(!seriesExists){
            keyName = "seriesCover"+series.getId()+"."+"png";
            s3Services.uploadFile(keyName,realFile);
        }
        keyName = "comicCover" + comic.getId() + ".png";
        s3Services.uploadFile(keyName,realFile);
        return "Upload Successfully -> KeyName = " + keyName;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file ,
                         @RequestParam("pngFile") String pngFile,
                         @RequestParam("comicId") int comicId)
    {
        Comic comic = comicService.findComicById(comicId);
        Pages page = new Pages();
        page.setComicId(comicId);
        pagesService.savePage(page);
        String keyName = "series" + comic.getSeriesId() +"comic"+comic.getId()+ "page" + page.getId() + ".json";
        s3Services.uploadFile(keyName, file);
        keyName = "series"+comic.getSeriesId()+"comic"+comic.getId()+ "page" + page.getId() + ".png";
        System.out.println(pngFile);
        byte[] imagedata = DatatypeConverter.parseBase64Binary(pngFile.substring(pngFile.indexOf(",")+1));
        BASE64DecodedMultipartFile realFile = new BASE64DecodedMultipartFile(imagedata);
        s3Services.uploadFile(keyName, realFile);
        return "Upload Successfully -> KeyName = " + keyName;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/profileImage")
    @ResponseBody
    public String uploadFile(@RequestParam("image") StringBuilder image){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        byte[] imagedata = DatatypeConverter.parseBase64Binary(image.substring(image.indexOf(",")+1));
        BASE64DecodedMultipartFile realFile = new BASE64DecodedMultipartFile(imagedata);
        s3Services.uploadFile("profileImage" + user.getId() + ".png", realFile);
        return "uploaded";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/uploadToGame")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file ,
                             @RequestParam("pngFile") String pngFile,
                             @RequestParam("gameTitle") String gameTitle,
                             @RequestParam("gamePageId") int gamePageId)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        GamePage gamePage = gamePageService.findGamePageById(gamePageId);
        Game game = gameService.findGameById(gamePage.getGameId());
        GamePlayer gamePlayer = gamePlayerService.findGamePlayerByUserId(user.getId());
        Submission submission = new Submission();
        submission.setPlayerId(gamePlayer.getId());
        submission.setGamePageId(gamePage.getId());
        submission.setVotes(0);
        submission = submissionService.saveSubmission(submission);
//        String keyName1 = "gamePage"+gamePage.getId()+".json";
//        String keyName2 = "gamePage"+gamePage.getId()+".png";
//        S3Object output1 = s3Services.downloadFile(keyName1);
//        S3Object output2 = s3Services.downloadFile(keyName2);
        String keyName1 = "submission"+submission.getId()+".json";
        String keyName2 = "submission"+submission.getId()+".png";
        s3Services.uploadFile(keyName1,file);
        byte[] imagedata = DatatypeConverter.parseBase64Binary(pngFile.substring(pngFile.indexOf(",")+1));
        BASE64DecodedMultipartFile realFile = new BASE64DecodedMultipartFile(imagedata);
        s3Services.uploadFile(keyName2,realFile);
        System.out.println(gameTitle);
        System.out.println(gamePageId);
        return "not implemented yet";
    }

}
