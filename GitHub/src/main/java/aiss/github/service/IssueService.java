package aiss.github.service;

import aiss.github.etl.Transformer;
import aiss.github.model.Issue;
import aiss.github.model.issuedata.IssueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class IssueService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Transformer transformer;

    @Autowired
    private CommentService commentService;

    @Value("${github.token}")
    private String token;

    @Value("${github.baseuri}")
    private String baseUri;

    public List<Issue> sinceIssues(String owner, String repo, Integer pages, Integer nIssues) {
        String uri = baseUri + owner + "/" + repo + "/issues?state=all&page=1";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        List<Issue> allIssues = new ArrayList<>();
        int currentPage = 1;

        while (uri != null && currentPage <= pages && allIssues.size() < nIssues) {
            System.out.println("Fetching: " + uri);
            ResponseEntity<IssueData[]> response = restTemplate.exchange(
                    uri, HttpMethod.GET, request, IssueData[].class);

            if (response.getBody() != null) {
                for (IssueData issueData : response.getBody()) {
                    if (allIssues.size() >= nIssues)
                        break;

                    List<aiss.github.model.Comment> comments = commentService.getNotes(
                            owner, repo, issueData.number.toString());

                    Issue transformedIssue = transformer.transformIssue(issueData, comments);
                    allIssues.add(transformedIssue);
                }
            }

            uri = getNextPageUrl(response.getHeaders());
            currentPage++;
        }

        return allIssues;
    }

    private String getNextPageUrl(HttpHeaders headers) {
        List<String> linkHeaders = headers.get("Link");
        if (linkHeaders == null || linkHeaders.isEmpty()) return null;

        for (String linkHeader : linkHeaders) {
            String[] parts = linkHeader.split(", ");
            for (String part : parts) {
                String[] section = part.split("; ");
                if (section.length == 2 && section[1].equals("rel=\"next\"")) {
                    return section[0].substring(1, section[0].length() - 1); // Quita < >
                }
            }
        }

        return null;
    }
}
