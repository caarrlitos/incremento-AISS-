package aiss.github.service;

import aiss.github.etl.Transformer;
import aiss.github.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Transformer transformer;

    @Value("${github.baseuri}")
    private String baseUri;

    @Value("${github.token}")
    private String token;

    public List<Comment> getNotes(String owner, String repo, String issueNumber) {
        String uri = baseUri + owner + "/" + repo + "/issues/" + issueNumber + "/comments";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<aiss.github.model.issuedata.Comment[]> response = restTemplate.exchange(
                uri, HttpMethod.GET, request, aiss.github.model.issuedata.Comment[].class);

        aiss.github.model.issuedata.Comment[] githubComments = response.getBody();

        return Arrays.stream(githubComments)
                .map(transformer::transformComment)
                .collect(Collectors.toList());
    }
}