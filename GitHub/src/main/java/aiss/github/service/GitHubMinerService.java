package aiss.github.service;

import aiss.github.etl.Transformer;
import aiss.github.model.Project;
import aiss.github.model.Commit;
import aiss.github.model.Issue;
import aiss.github.model.SourcePlatform;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GitHubMinerService {

    private final CommitService commitService;
    private final IssueService issueService;
    private final Transformer transformer;

    public GitHubMinerService(CommitService commitService,
                              IssueService issueService,
                              Transformer transformer) {
        this.commitService = commitService;
        this.issueService = issueService;
        this.transformer = transformer;
    }

    public Project fetchProject(String owner, String repo,
                                int nCommits, int nIssues, int maxPages) {

        List<Commit> commits = commitService.sinceCommits(owner, repo, nCommits, maxPages);
        List<Issue> issues = issueService.sinceIssues(owner, repo, nIssues, maxPages);

        Project project = new Project();

        String repoId = owner + "/" + repo;
        project.setId(repoId);
        project.setName(repo);
        project.setWebUrl("https://github.com/" + owner + "/" + repo);
        project.setRetrieved_at(LocalDateTime.now().toString());
        project.setSourcePlatform(SourcePlatform.GITHUB);

        project.setCommits(commits);
        project.setIssues(issues);


        return project;
    }
}