package aiss.GitMiner.controller;

import aiss.GitMiner.model.Commit;
import aiss.GitMiner.repository.CommitRepository;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name="Commit",description="Commit management API")
@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {


    @Autowired
    CommitRepository commitRepository;
    @Operation(summary="retrieve Commits",description="return a set of object Commit (if you dont specify the author email) or returns a set of object Commit done by the user with the author email specified", tags={"Commit","get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Commit.class))},description = "Successfully retrieved Commits"),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @GetMapping
    public List<Commit> getAllCommits(@Parameter(description = "email of the author whose commits are being get") @RequestParam(required = false) String author_email) {
        if (author_email != null && commitRepository.findByAuthorEmail(author_email) != null) {
            return commitRepository.findByAuthorEmail(author_email);
        } else if(author_email != null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else {
            return commitRepository.findAll();
        }
    }


    @Operation(summary="retrieve commit by specified id",description="return a object Commit with the specified id", tags={"Commit","get","id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Commit.class))},description = "Successfully retrieved Commit"),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Commit getCommitById(@Parameter(description = "id of the Commit to be obtained",required = true)@PathVariable String id) {
        if(!commitRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Commit with id "+id+" does not exist");
        }
        return commitRepository.findById(id).get();
    }

    @Operation(summary="insert a Commit",description="add a new Commit whose data is passed in the body of the request in JSON format", tags={"Commit","post"})
    @ApiResponses ({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Commit.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Commit create(@Valid @RequestBody Commit commit) {
        Commit newCommit = commitRepository.save(
                new Commit(commit.getId(),
                        commit.getTitle(),
                        commit.getMessage(),
                        commit.getAuthorName(),
                        commit.getAuthorEmail(),
                        commit.getAuthoredDate(),
                        commit.getWebUrl(),
                        commit.getRetrieved_at(),
                        commit.getIsMergeCommit(),
                        commit.getSourcePlatform()));
        return newCommit;
    }

    @Operation(summary="update a commit",description="update a commit data by specifying its id and the new data in the body of the request in JSON format", tags={"commit","put"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Commit updatedCommit, @Parameter(description ="id of the commit whose data is going to be updated" ,required = true)@PathVariable String id) {
        if(!commitRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Commit with id "+id+" does not exist");
        }

        Commit _commit =  commitRepository.findById(id).get();
        _commit.setId(updatedCommit.getId());
        _commit.setTitle(updatedCommit.getTitle());
        _commit.setMessage(updatedCommit.getMessage());
        _commit.setAuthorName(updatedCommit.getAuthorName());
        _commit.setAuthorEmail(updatedCommit.getAuthorEmail());
        _commit.setAuthoredDate(updatedCommit.getAuthoredDate());
        _commit.setWebUrl(updatedCommit.getWebUrl());
        _commit.setSourcePlatform(updatedCommit.getSourcePlatform());
        commitRepository.save(_commit);
    }


    @Operation(summary="delete a Commit",description="delete a Commit whose id is specified", tags={"Commit","delete"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the Commit to be deleted",required = true)@PathVariable String id) {
        if (commitRepository.existsById(id)) {
            commitRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Commit with id "+id+" does not exist");
        }
    }
}
