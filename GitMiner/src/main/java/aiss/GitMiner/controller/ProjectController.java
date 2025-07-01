package aiss.GitMiner.controller;

import aiss.GitMiner.model.*;
import aiss.GitMiner.repository.ProjectRepository;
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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name="Project",description = "Project management API")
@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @Operation(summary="retrieve Projects",description="returns all existent Projects", tags={"Project","get"})
    @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Project.class))}, description = "Successfully retrieved Projects")
    @GetMapping
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Operation(summary="retrieve Project by id",description="returns the Project with the specified id", tags={"Project","get","id"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Project.class))}, description = "Successfully retrieved Project"),
            @ApiResponse(responseCode ="404" ,content = {@Content(schema = @Schema())},description = "Project not found")
    })
    @GetMapping("/{id}")
    public Project getProjectById(@Parameter(description = "id of the Project to be obtained",required = true) @PathVariable String id) {
        if(!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return projectRepository.findById(id).get();
    }


    @Operation(summary="insert a Project",description="add a new Project whose data is passed in the body of the request in JSON format", tags={"Project","post"})
    @ApiResponses ({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Project.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        Map<String, User> userMap = new HashMap<>();

        for (Issue issue : project.getIssues()) {
            if (issue.getAuthor() != null) {
                userMap.putIfAbsent(issue.getAuthor().getId(), issue.getAuthor());
                issue.setAuthor(userMap.get(issue.getAuthor().getId()));
            }
            if (issue.getAssignee() != null) {
                userMap.putIfAbsent(issue.getAssignee().getId(), issue.getAssignee());
                issue.setAssignee(userMap.get(issue.getAssignee().getId()));
            }

            for (Comment comment : issue.getComments()) {
                if (comment.getAuthor() != null) {
                    userMap.putIfAbsent(comment.getAuthor().getId(), comment.getAuthor());
                    comment.setAuthor(userMap.get(comment.getAuthor().getId()));
                }
            }
        }

        if (project.getSourcePlatform() == null && project.getWebUrl() != null) {
            String url = project.getWebUrl().toLowerCase();
            if (url.contains("github")) {
                project.setSourcePlatform(SourcePlatform.GITHUB);
            } else if (url.contains("bitbucket")) {
                project.setSourcePlatform(SourcePlatform.BITBUCKET);
            }
        }

        String now = LocalDateTime.now().toString();

        for (Commit commit : project.getCommits()) {
            commit.setRetrieved_at(now);
            commit.setSourcePlatform(project.getSourcePlatform());
        }

        for (Issue issue : project.getIssues()) {
            issue.setRetrieved_at(now);
            issue.setSourcePlatform(project.getSourcePlatform());
            issue.setNumComments(issue.getComments() != null ? issue.getComments().size() : 0);


            for (Comment comment : issue.getComments()) {
                comment.setRetrieved_at(now);
                comment.setSourcePlatform(project.getSourcePlatform());

            }
        }

        for (Commit commit : project.getCommits()) {
            commit.setRetrieved_at(now);
            String msg = commit.getMessage() != null ? commit.getMessage().toLowerCase() : "";
            commit.setIsMergeCommit(msg.contains("merge"));
        }

        Project newProject = projectRepository.save(new Project(project.getId(), project.getName(), project.getWebUrl(), LocalDateTime.now().toString(), project.getSourcePlatform(), project.getCommits(), project.getIssues()));
        return newProject;
    }

    @Operation(summary="update a Project",description="update a Project data by specifying its id and the new data in the body of the request in JSON format", tags={"Project","put"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Project update(@Valid @RequestBody Project project, @Parameter(description = "id of the Project to be modified", required = true)@PathVariable String id) {
        if(!projectRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Project with id "+id+" does not exist");
        }
        Project _project = projectRepository.findById(id).get();
        _project.setId(id);
        _project.setName(project.getName());
        _project.setWebUrl(project.getWebUrl());
        _project.setCommits(project.getCommits());
        _project.setIssues(project.getIssues());
        _project.setSourcePlatform(project.getSourcePlatform());
        return projectRepository.save(_project);
    }


    @Operation(summary="delete a Project",description="delete a Project whose id is specified", tags={"Project","delete"})
    @ApiResponses ({
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the Project to be deleted", required = true)@PathVariable String id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Project with id "+id+" does not exist");
        }
    }

}
