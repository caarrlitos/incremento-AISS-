package ProyectoAiss.BitBucket;

import ProyectoAiss.BitBucket.model.BitBucket.BCommit;
import ProyectoAiss.BitBucket.model.BitBucket.SourcePlatform;
import ProyectoAiss.BitBucket.service.BitBucketCommitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BitBucketCommitServiceTest {

    @Autowired
    private BitBucketCommitService commitService;

    @Test
    @DisplayName("Fetch commits from gentlero/bitbucket-api")
    public void testFetchCommitsFromPublicRepo() {
        String workspace = "gentlero";
        String repoSlug = "bitbucket-api";
        int nCommits = 5;
        int maxPages = 2;

        List<BCommit> commits = commitService.fetchCommits(workspace, repoSlug, nCommits, maxPages);

        assertNotNull(commits, "La lista de commits no debe ser null");
        assertFalse(commits.isEmpty(), "La lista de commits no debe estar vacía");

        for (BCommit commit : commits) {
            assertNotNull(commit.getId(), "El commit debe tener ID");
            assertNotNull(commit.getMessage(), "El commit debe tener mensaje");
            assertNotNull(commit.getAuthorName(), "El commit debe tener autor");
            assertEquals(SourcePlatform.BITBUCKET, commit.getSourcePlatform(), "La plataforma debe ser BITBUCKET");
        }

        System.out.println("Commits obtenidos: " + commits.size());
    }
}
