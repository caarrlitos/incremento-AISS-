package ProyectoAiss.BitBucket.service;

import ProyectoAiss.BitBucket.model.BitBucket.BProject;
import ProyectoAiss.BitBucket.model.BitBucket.BCommit;
import ProyectoAiss.BitBucket.model.BitBucket.BIssue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BitBucketMinerService {

    private final BitBucketCommitService commitService;
    private final BitBucketIssueService issueService;

    public BitBucketMinerService(BitBucketCommitService commitService,
                                 BitBucketIssueService issueService) {
        this.commitService = commitService;
        this.issueService = issueService;
    }

    public BProject fetchProject(String workspace, String repoSlug,
                                 int nCommits, int nIssues, int maxPages) {

        List<BCommit> commits = commitService.fetchCommits(workspace, repoSlug, nCommits, maxPages);
        List<BIssue> issues = issueService.fetchIssues(workspace, repoSlug, nIssues, maxPages);

        BProject project = new BProject();
        BCommit first = commits.get(0);
        project.setId(first.getRepositoryId());
        project.setName(first.getRepositoryName());
        project.setWebUrl(first.getRepositoryUrl());
        project.setCommits(commits);
        project.setIssues(issues);

        return project;
    }
}
