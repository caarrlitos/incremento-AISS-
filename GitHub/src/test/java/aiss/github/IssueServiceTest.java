package aiss.github;


import aiss.github.model.Issue;
import aiss.github.service.IssueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IssueServiceTest {
    @Autowired
    private IssueService issueService;
    @Test
    @DisplayName("Get Issues since 'nCommits' Commits")
    public void testSinceIssues() {
        String owner = "octocat";
        String repo = "Hello-World";
        Integer pages= 2;
        int nIssues = 5;

        List<Issue> issues = issueService.sinceIssues(owner, repo, pages, nIssues);

        assertNotNull(issues, "La lista de Issues no debe ser null");
        assertFalse(issues.isEmpty(),"La lista de Issues no debe ser null");
        assertTrue(issues.size()<=nIssues,"La lista de Issues no puede ser mayor a "+nIssues+" Commits");

        for(Issue issue: issues) {
            assertNotNull(issue.getCreated_at(), "El Created_at no debe ser null");
            assertNotNull(issue.getAuthor(), "El Author no debe ser null");
            assertNotNull(issue.getId(), "El Id no debe ser null");
            assertNotNull(issue.getTitle(), "El Title no debe ser null");
            assertNotNull(issue.getSource_platform(), "El Source_platform no debe ser null");
            assertEquals("GITHUB", issue.getSource_platform().toString(), "La plataforma debe ser GITHUB");
            assertNotNull(issue.getState(), "El State no debe ser null");
        }
        System.out.println(issues);
    }
}
