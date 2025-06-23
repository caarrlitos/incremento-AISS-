package aiss.GitMiner.controller;

import aiss.GitMiner.model.Comment;
import aiss.GitMiner.model.SourcePlatform;
import aiss.GitMiner.repository.CommentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<Comment> getAllComments() {return commentRepository.findAll();}

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment create(@Valid @RequestBody Comment comment) {       //cogemos las propiedades del body y lo metemos en el constructor
        Comment newComment = commentRepository.save(
                new Comment(comment.getId(), comment.getBody(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getRetrieved_at(), comment.getSourcePlatform(), comment.getAuthor(), comment.getIsBot()));
        return newComment;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")         //buscamos el comment antiguo y cambiamos sus valores por los del body
    public void update(@Valid @RequestBody Comment updatedComment, @PathVariable String id) {
        Optional<Comment> comment = commentRepository.findById(id);

        Comment _comment = comment.get();
        _comment.setId(updatedComment.getId());
        _comment.setBody(updatedComment.getBody());
        _comment.setCreatedAt(updatedComment.getCreatedAt());
        _comment.setUpdatedAt(updatedComment.getUpdatedAt());
        _comment.setAuthor(updatedComment.getAuthor());
        _comment.setIsBot(updatedComment.getIsBot());
        _comment.setSourcePlatform(updatedComment.getSourcePlatform());
        commentRepository.save(_comment);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        }
    }

}
