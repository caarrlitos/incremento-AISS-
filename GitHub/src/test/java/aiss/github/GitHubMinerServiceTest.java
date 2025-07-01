package aiss.github;


import aiss.github.model.Commit;
import aiss.github.model.Issue;
import aiss.github.model.Project;
import aiss.github.service.GitHubMinerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GitHubMinerServiceTest {
    @Autowired
    private GitHubMinerService service;

    @Test
    @DisplayName("Test fetchProject with octocat/Hello-World")
    public void testFetchProject() {
        String owner = "octocat";
        String repo = "Hello-World";
        int nCommits = 5;
        int nIssues = 5;
        int maxPages = 2;

        Project project = service.fetchProject(owner, repo, nCommits, nIssues, maxPages);

        assertNotNull(project, "El proyecto no debe ser null");
        assertEquals(owner + "/" + repo, project.getId(), "El ID del proyecto debe coincidir");
        assertEquals(repo, project.getName(), "El nombre del repositorio debe coincidir");
        assertEquals("https://github.com/" + owner + "/" + repo, project.getWeb_url(), "La URL del proyecto debe ser válida");
        assertNotNull(project.getRetrieved_at(), "El campo 'retrieved_at' no debe ser null");
        assertEquals("GITHUB", project.getSource_platform().toString(), "La plataforma debe ser GITHUB");

        List<Commit> commits = project.getCommits();
        assertNotNull(commits, "La lista de commits no debe ser null");
        assertFalse(commits.isEmpty(), "Debe haber al menos un commit");
        assertTrue(commits.size() <= nCommits, "No debe haber más commits que " + nCommits);

        List<Issue> issues = project.getIssues();
        assertNotNull(issues, "La lista de issues no debe ser null");
        assertFalse(issues.isEmpty(), "Debe haber al menos un issue");
        assertTrue(issues.size() <= nIssues, "No debe haber más issues que " + nIssues);
    }
}

