package ProyectoAiss.BitBucket.service;

import ProyectoAiss.BitBucket.etl.TransformerBitBucket;
import ProyectoAiss.BitBucket.model.BitBucket.BIssue;
import ProyectoAiss.BitBucket.model.BitBucket.BComment;
import ProyectoAiss.BitBucket.model.BitBucket.IssueData.BIssueData;
import ProyectoAiss.BitBucket.model.BitBucket.IssueData.IssuesPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BitBucketIssueService {

    @Autowired
    RestTemplate restTemplate;
    private final TransformerBitBucket transformer;
    private final BitBucketCommentService commentService;

    public BitBucketIssueService(TransformerBitBucket transformer,
                                 BitBucketCommentService commentService) {
        this.transformer = transformer;
        this.commentService = commentService;
    }

    public List<BIssue> fetchIssues(String workspace, String repoSlug, int nIssues, int maxPages) {
        List<BIssue> issues = new ArrayList<>();

        for (int page = 1; page <= maxPages; page++) {
            String url = "https://api.bitbucket.org/2.0/repositories/" + workspace + "/" + repoSlug
                    + "/issues?pagelen=" + nIssues + "&page=" + page;

            IssuesPage response = restTemplate.getForObject(url, IssuesPage.class);

            if (response != null && response.values != null) {
                for (BIssueData rawIssue : response.values) {

                    List<BComment> comments = new ArrayList<>();
                    if (rawIssue.links != null &&
                            rawIssue.links.comments != null &&
                            rawIssue.links.comments.href != null) {
                        comments = commentService.fetchComments(rawIssue.links.comments.href, maxPages);
                    }

                    BIssue issue = transformer.transformIssue(rawIssue, comments);
                    issues.add(issue);
                }

                if (response.values.size() < nIssues) {
                    break;
                }
            } else {
                break;
            }
        }

        return issues;
    }

}
