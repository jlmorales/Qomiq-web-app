package com.comic.Service;

import com.comic.Repository.CommentRepository;
import com.comic.model.Comic;
import com.comic.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("commentService")
public class CommentService {

    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    public List<Comment> findCommentsByComicId(int id){
        return commentRepository.findAllByComicId(id);
    }

    public void saveComment(Comment comment){
        this.commentRepository.save(comment);
    }
}
