package aiss.GitMiner.controller;

import aiss.GitMiner.model.Comment;
import aiss.GitMiner.model.Issue;
import aiss.GitMiner.repository.IssueRepository;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Optional;

@Tag(name="Issue", description="Issue management API")
@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository issueRepository;


    @Operation(summary="retrieve Issues",description="return a set of object Issue (if you dont specify the author id or state) if you specify them it returns a set of Issues based on the params specified", tags={"Issue","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Issue.class))}, description = "Successfully retrieved Issues"),
            @ApiResponse(responseCode ="404" ,content = {@Content(schema = @Schema())},description = "Issue not found")
    })
    @GetMapping
    public List<Issue> getAllIssues(@Parameter(description = "id of the author whose Issues you want to obtain") @RequestParam(required = false) String authorId,
                                    @Parameter(description = "state of the Issues you want to obtain")@RequestParam(required = false) String state) {
        if (authorId != null&&state!=null) {
            List<Issue> lista = issueRepository.findByAuthor_Id(authorId);
            lista.removeIf(issue -> !issue.getState().equals(state));
            return lista;
        }
        else if((issueRepository.findByState(state).isEmpty()&&state!=null) || (issueRepository.findByAuthor_Id(authorId).isEmpty()&&authorId!=null)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else if (state != null ) {
            return issueRepository.findByState(state);
        }
        else if (authorId != null) {
            return issueRepository.findByAuthor_Id(authorId);
        }
        else {
            return issueRepository.findAll();
        }
    }


    @Operation(summary="retrieve Issue by id",description="return a object Issue based on the specified id", tags={"Issue","get","id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Issue.class))}, description = "Successfully retrieved Issue"),
            @ApiResponse(responseCode ="404" ,content = {@Content(schema = @Schema())},description = "Issue not found")
    })
    @GetMapping("/{id}")
    public Issue getIssueById(@Parameter(description = "id of the Issue you want to obtain", required = true)@PathVariable String id) {
        if(issueRepository.findById(id).isPresent()) {
            return issueRepository.findById(id).get();
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary="get the comments of a Issue",description="get the comments of the Issue that has the specified id", tags={"Issue","get","id"})
    @ApiResponses ({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Comment.class))}, description = "Successfully retrieved"),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())},description = "Issue not found")
    })
    @GetMapping("/{id}/comments")
    public List<Comment> getIssueComments(@Parameter(description = "id of the Issue whose comments you want to obtain")@PathVariable String id) {
        if(issueRepository.findById(id).isPresent()) {
            return issueRepository.findById(id).get().getComments();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary="insert a Issue",description="add a new Issue whose data is passed in the body of the request in JSON format", tags={"Issue","post"})
    @ApiResponses ({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Issue.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Issue create(@Valid @RequestBody Issue issue) {
        Issue newIssue = issueRepository.save(
                new Issue(
                        issue.getId(),
                        issue.getTitle(),
                        issue.getDescription(),
                        issue.getState(),
                        issue.getCreatedAt(),
                        issue.getUpdatedAt(),
                        issue.getClosedAt(),
                        issue.getRetrieved_at(),
                        issue.getNumComments(),
                        issue.getSourcePlatform(),
                        issue.getLabels(),
                        issue.getVotes(),
                        issue.getAuthor(),
                        issue.getAssignee(),
                        issue.getComments()
                )
        );
        return newIssue;
    }



    @Operation(summary="update a Issue",description="update a Issue data by specifying its id and the new data in the body of the request in JSON format", tags={"Issue","put"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Issue update(@Valid @RequestBody Issue issue, @Parameter(description = "Id of the Issue whose data you want to update")@PathVariable String id) {
        if(!issueRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Issue _issue = issueRepository.findById(id).get();
        _issue.setId(id);
        _issue.setTitle(issue.getTitle());
        _issue.setDescription(issue.getDescription());
        _issue.setState(issue.getState());
        _issue.setCreatedAt(issue.getCreatedAt());
        _issue.setUpdatedAt(issue.getUpdatedAt());
        _issue.setClosedAt(issue.getClosedAt());
        _issue.setLabels(issue.getLabels());
        _issue.setVotes(issue.getVotes());
        _issue.setAuthor(issue.getAuthor());
        _issue.setAssignee(issue.getAssignee());
        _issue.setComments(issue.getComments());
        _issue.setSourcePlatform(issue.getSourcePlatform());
        return issueRepository.save(_issue);
    }


    @Operation(summary="delete a Issue",description="delete a Issue whose id is specified", tags={"Issue","delete"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete( @Parameter(description = "Id of the Issue you want to delete")@PathVariable String id) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
