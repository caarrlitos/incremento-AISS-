package aiss.github;

import aiss.github.model.Comment;
import aiss.github.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Test
    @DisplayName("Get Comments of a Issue")
    public void testGetNotesReturnsComments() {
        String owner = "octocat";
        String repo = "Hello-World";
        String issueNumber = "348";
        List<Comment> comments = commentService.getNotes(owner, repo, issueNumber);

        assertNotNull(comments, "La lista de Comments no debe ser null");
        assertFalse(comments.isEmpty(), "La lista de Comments no debe estar vacía");
        for(Comment comment : comments) {
            assertNotNull(comment.getId(), "El Comment debe tener un ID");
            assertNotNull(comment.getBody(), "El Comment debe tener un cuerpo");
            assertNotNull(comment.getAuthor(), "El Comment debe tener un author");
            assertNotNull(comment.getSource_platform(), "El Comment debe tener un source platform");
        }
        System.out.println(comments);
    }
}