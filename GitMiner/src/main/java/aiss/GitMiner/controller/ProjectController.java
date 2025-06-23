package aiss.GitMiner.controller;

import aiss.GitMiner.model.*;
import aiss.GitMiner.repository.ProjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping
    public List<Project> getAllProjects() {return projectRepository.findAll();}

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable String id) {
        Optional<Project> project = projectRepository.findById(id);
        return project.get();
    }

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

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Project update(@Valid @RequestBody Project project, @PathVariable String id) {
        Optional<Project> projectOptional = projectRepository.findById(id);

        Project _project = projectOptional.get();
        _project.setId(id);
        _project.setName(project.getName());
        _project.setWebUrl(project.getWebUrl());
        _project.setCommits(project.getCommits());
        _project.setIssues(project.getIssues());
        _project.setSourcePlatform(project.getSourcePlatform());

        return projectRepository.save(_project);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
    }

}
