package ProyectoAiss.BitBucket;

import ProyectoAiss.BitBucket.model.BitBucket.BIssue;
import ProyectoAiss.BitBucket.model.BitBucket.BComment;
import ProyectoAiss.BitBucket.model.BitBucket.SourcePlatform;
import ProyectoAiss.BitBucket.service.BitBucketIssueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BitBucketIssueServiceTest {

    @Autowired
    private BitBucketIssueService issueService;

    @Test
    @DisplayName("Get Issues with Comments from a Bitbucket Repository")
    public void testFetchIssuesReturnsIssuesWithComments() {
        String workspace = "gentlero";
        String repoSlug = "bitbucket-api";
        int nIssues = 5;
        int maxPages = 2;

        List<BIssue> issues = issueService.fetchIssues(workspace, repoSlug, nIssues, maxPages);

        assertNotNull(issues, "La lista de issues no debe ser null");
        assertFalse(issues.isEmpty(), "La lista de issues no debe estar vacía");

        for (BIssue issue : issues) {
            assertNotNull(issue.getId(), "El issue debe tener un ID");
            assertNotNull(issue.getTitle(), "El issue debe tener un título");
            assertNotNull(issue.getAuthor(), "El issue debe tener un autor");
            assertNotNull(issue.getSourcePlatform(), "El issue debe tener una plataforma de origen");
            assertEquals(SourcePlatform.BITBUCKET, issue.getSourcePlatform(), "La plataforma debe ser BITBUCKET");

            // Comprobamos comentarios si los hay
            if (issue.getComments() != null) {
                for (BComment comment : issue.getComments()) {
                    assertNotNull(comment.getId(), "El comentario debe tener un ID");
                    assertNotNull(comment.getBody(), "El comentario debe tener un cuerpo");
                    assertNotNull(comment.getAuthor(), "El comentario debe tener un autor");
                    assertEquals(SourcePlatform.BITBUCKET, comment.getSourcePlatform(), "La plataforma del comentario debe ser BITBUCKET");
                }
            }
        }

        System.out.println("Issues: " + issues);
    }
}
