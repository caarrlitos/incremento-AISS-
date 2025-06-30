package aiss.github;

import aiss.github.model.Commit;
import aiss.github.service.CommitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CommitServiceTest {
    @Autowired
    private CommitService commitService;

    @Test
    @DisplayName("Get since 'nCommits' Commits")
    public void testSinceCommits(){
        String owner = "octocat";
        String repo = "Hello-World";
        Integer pages= 2;
        int nCommits = 5;

        List<Commit> commits = commitService.sinceCommits(owner, repo, pages, nCommits);

        assertNotNull(commits, "La lista de Commits no debe ser null");
        assertFalse(commits.isEmpty(),"La lista de Commits no debe estar vacía");
        assertTrue(commits.size()<= nCommits,"La lista de Commits no puede contener más de "+nCommits+" Commits");

        for(Commit commit : commits){
            assertNotNull(commit.getId(), "El Id del Commit no debe ser null");
            assertNotNull(commit.getAuthor_name(), "El Author no debe ser null");
            assertNotNull(commit.getTitle(), "El Title no debe ser null");
            assertNotNull(commit.getSource_platform(), "El Source platform no debe ser null");
            assertNotNull(commit.getRetrieved_at(), "El Retrieved at no debe ser null");
        }
        System.out.println(commits);
    }

}
