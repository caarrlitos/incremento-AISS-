package aiss.github.service;

import aiss.github.etl.Transformer;
import aiss.github.model.Commit;
import aiss.github.model.commitdata.CommitData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Transformer transformer;

    @Value("${github.token}")
    private String token;

    @Value("${github.baseuri}")
    private String baseUri;

    public List<Commit> sinceCommits(String owner, String repo, Integer pages, Integer nCommits) {
        int currentPage = 1;
        int commitsPerPage = Math.min(nCommits, 100); // GitHub máximo por página

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        List<Commit> allCommits = new ArrayList<>();

        while (currentPage <= pages && allCommits.size() < nCommits) {
            String uri = baseUri + owner + "/" + repo + "/commits?per_page=" + commitsPerPage + "&page=" + currentPage;
            System.out.println("Fetching: " + uri);

            ResponseEntity<CommitData[]> response = restTemplate.exchange(
                    uri, HttpMethod.GET, request, CommitData[].class);

            if (response.getBody() != null) {
                for (CommitData commitData : response.getBody()) {
                    if (allCommits.size() >= nCommits)
                        break;
                    Commit commit = transformer.transformCommit(commitData);
                    allCommits.add(commit);
                }
            } else {
                break;
            }

            currentPage++;
        }

        return allCommits;
    }
}
