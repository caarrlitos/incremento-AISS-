package ProyectoAiss.BitBucket;

import ProyectoAiss.BitBucket.model.BitBucket.BComment;
import ProyectoAiss.BitBucket.service.BitBucketCommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BitBucketCommentServiceTest {

    @Autowired
    private BitBucketCommentService commentService;

    @Test
    @DisplayName("Fetch comments from a Bitbucket issue")
    public void testFetchCommentsReturnsValidList() {
        String commentsUrl = "https://api.bitbucket.org/2.0/repositories/gentlero/bitbucket-api/issues/1/comments";
        int maxPages = 1;

        List<BComment> comments = commentService.fetchComments(commentsUrl, maxPages);

        assertNotNull(comments, "La lista de comentarios no debe ser null");
        assertFalse(comments.isEmpty(), "La lista de comentarios no debe estar vacía");

        for (BComment comment : comments) {
            assertNotNull(comment.getId(), "El comentario debe tener un ID");
            assertNotNull(comment.getBody(), "El comentario debe tener contenido");
            assertNotNull(comment.getAuthor(), "El comentario debe tener autor");
            assertNotNull(comment.getSourcePlatform(), "El comentario debe tener plataforma de origen");
        }
    }
}
