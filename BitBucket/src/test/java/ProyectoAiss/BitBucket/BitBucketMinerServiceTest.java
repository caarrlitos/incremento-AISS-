package ProyectoAiss.BitBucket;

import ProyectoAiss.BitBucket.model.BitBucket.BProject;
import ProyectoAiss.BitBucket.model.BitBucket.BCommit;
import ProyectoAiss.BitBucket.model.BitBucket.BIssue;
import ProyectoAiss.BitBucket.model.BitBucket.SourcePlatform;
import ProyectoAiss.BitBucket.service.BitBucketMinerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BitBucketMinerServiceTest {

    @Autowired
    private BitBucketMinerService minerService;

    @Test
    @DisplayName("Fetch complete project data from Bitbucket")
    public void testFetchProjectReturnsValidBProject() {
        String workspace = "gentlero";
        String repoSlug = "bitbucket-api";
        int nCommits = 3;
        int nIssues = 3;
        int maxPages = 2;

        BProject project = minerService.fetchProject(workspace, repoSlug, nCommits, nIssues, maxPages);

        assertNotNull(project, "El proyecto no debe ser null");
        assertNotNull(project.getId(), "El ID del proyecto no debe ser null");
        assertNotNull(project.getName(), "El nombre del proyecto no debe ser null");
        assertNotNull(project.getWebUrl(), "La URL del proyecto no debe ser null");
        assertEquals(SourcePlatform.BITBUCKET, project.getSourcePlatform(), "La plataforma debe ser BITBUCKET");
        assertNotNull(project.getRetrieved_at(), "La fecha de recuperación no debe ser null");

        List<BCommit> commits = project.getCommits();
        assertNotNull(commits, "La lista de commits no debe ser null");
        assertFalse(commits.isEmpty(), "La lista de commits no debe estar vacía");

        for (BCommit commit : commits) {
            assertEquals(SourcePlatform.BITBUCKET, commit.getSourcePlatform(), "La plataforma del commit debe ser BITBUCKET");
        }

        List<BIssue> issues = project.getIssues();
        assertNotNull(issues, "La lista de issues no debe ser null");

        for (BIssue issue : issues) {
            assertEquals(SourcePlatform.BITBUCKET, issue.getSourcePlatform(), "La plataforma del issue debe ser BITBUCKET");
            assertNotNull(issue.getTitle(), "El issue debe tener título");
            assertNotNull(issue.getAuthor(), "El issue debe tener autor");
        }
    }
}
