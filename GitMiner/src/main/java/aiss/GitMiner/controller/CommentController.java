package aiss.GitMiner.controller;

import aiss.GitMiner.model.Comment;
import aiss.GitMiner.model.SourcePlatform;
import aiss.GitMiner.repository.CommentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name = "Comment",description = "Comment management API")
@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Operation(summary="retrieve Comments",description="return a set of object Comments", tags={"Comment","get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class))},description = "Successfully retrieved Comments")
    @GetMapping
    public List<Comment> getAllComments() {return commentRepository.findAll();}


    @Operation(summary="retrieve Comment by id",description="return a object Comment by an id given", tags={"Comment","get","id"})
    @ApiResponses ({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class))}, description = "Successfully retrieved Comment with specified id"),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Comment getCommentById(@Parameter(description = "id of the Comment to be searched",required = true)@PathVariable String id) {
        if(!commentRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return  commentRepository.findById(id).get();
    }



    @Operation(summary="insert a Comment",description="add a new Comment whose content is passed in the body of the request in JSON format", tags={"Comment","post"})
    @ApiResponses ({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Comment.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment create(@Valid @RequestBody Comment comment) {       //cogemos las propiedades del body y lo metemos en el constructor
        Comment newComment = commentRepository.save(
                new Comment(comment.getId(),
                        comment.getBody(),
                        comment.getCreatedAt(),
                        comment.getUpdatedAt(),
                        comment.getRetrieved_at(),
                        comment.getSourcePlatform(),
                        comment.getAuthor(),
                        comment.getIsBot()));
        return newComment;
    }




    @Operation(summary="update a Comment",description="update a Comment whose content by specifying its id and the content in the body of the request in JSON format", tags={"Comment","put","id"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")         //buscamos el comment antiguo y cambiamos sus valores por los del body
    public void update(@Valid @RequestBody Comment updatedComment, @Parameter(description = "id of the Comment to be updated",required = true)@PathVariable String id) {
        if(!commentRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment with id "+ id+" not found");
        }
        Comment _comment = commentRepository.findById(id).get();
        _comment.setId(updatedComment.getId());
        _comment.setBody(updatedComment.getBody());
        _comment.setCreatedAt(updatedComment.getCreatedAt());
        _comment.setUpdatedAt(updatedComment.getUpdatedAt());
        _comment.setAuthor(updatedComment.getAuthor());
        _comment.setIsBot(updatedComment.getIsBot());
        _comment.setSourcePlatform(updatedComment.getSourcePlatform());
        commentRepository.save(_comment);
    }

    @Operation(summary="delete a Comment",description="delete a Comment whose id is specified", tags={"Comment","delete","id"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the Comment to be deleted",required = true) @PathVariable String id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment with id "+ id+" not found");
        }
    }


}
