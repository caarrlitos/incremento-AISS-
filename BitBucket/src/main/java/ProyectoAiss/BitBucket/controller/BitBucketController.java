package ProyectoAiss.BitBucket.controller;

import ProyectoAiss.BitBucket.model.BitBucket.BProject;
import ProyectoAiss.BitBucket.service.BitBucketMinerService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/bitbucket")
public class BitBucketController {

    @Autowired
    private RestTemplate restTemplate;

    private final BitBucketMinerService minerService;

    public BitBucketController(BitBucketMinerService minerService) {
        this.minerService = minerService;
    }

    @GetMapping("/{workspace}/{repoSlug}")
    public BProject getProjectFromBitBucket(
            @PathVariable String workspace,
            @PathVariable String repoSlug,
            @RequestParam(defaultValue = "5") int nCommits,
            @RequestParam(defaultValue = "5") int nIssues,
            @RequestParam(defaultValue = "2") int maxPages
    ) {
        return minerService.fetchProject(workspace, repoSlug, nCommits, nIssues, maxPages);
    }

    @PostMapping("/{workspace}/{repoSlug}")
    public BProject postProjectFromBitBucket(
            @PathVariable String workspace,
            @PathVariable String repoSlug,
            @RequestParam(defaultValue = "5") int nCommits,
            @RequestParam(defaultValue = "5") int nIssues,
            @RequestParam(defaultValue = "2") int maxPages
    ) {
        BProject project = minerService.fetchProject(workspace, repoSlug, nCommits, nIssues, maxPages);

        BProject createdProject = restTemplate.postForObject("http://localhost:8080/gitminer/projects", project, BProject.class);

        return createdProject;
    }


}
