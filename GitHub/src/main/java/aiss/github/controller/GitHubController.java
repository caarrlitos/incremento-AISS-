package aiss.github.controller;

import aiss.github.model.Project;
import aiss.github.service.GitHubMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    private RestTemplate restTemplate;

    private final GitHubMinerService minerService;

    public GitHubController(GitHubMinerService minerService) {
        this.minerService = minerService;
    }

    @GetMapping("/{owner}/{repo}")
    public Project getProjectFromGitHub(
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(defaultValue = "5") int nCommits,
            @RequestParam(defaultValue = "5") int nIssues,
            @RequestParam(defaultValue = "2") int maxPages
    ) {
        return minerService.fetchProject(owner, repo, nCommits, nIssues, maxPages);
    }

    @PostMapping("/{owner}/{repo}")
    public Project postProjectFromGitHub(
            @PathVariable String owner,
            @PathVariable String repo,
            @RequestParam(defaultValue = "5") int nCommits,
            @RequestParam(defaultValue = "5") int nIssues,
            @RequestParam(defaultValue = "2") int maxPages
    ) {
        Project project = minerService.fetchProject(owner, repo, nCommits, nIssues, maxPages);

        HttpEntity<Project> request = new HttpEntity<>(project);
        ResponseEntity<Project> response = restTemplate.exchange(
                "http://localhost:8080/gitminer/projects",
                HttpMethod.POST,
                request,
                Project.class
        );

        return response.getBody();
    }
}