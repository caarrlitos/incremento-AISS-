
package aiss.github.etl;

import aiss.github.model.commitdata.*;
import aiss.github.model.issuedata.*;
import aiss.github.model.Comment;
import aiss.github.model.Commit;
import aiss.github.model.Issue;
import aiss.github.model.Project;
import aiss.github.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Transformer {

    public Commit transformCommit(CommitData githubCommit) {
        Commit commit = new Commit();

        commit.setId(githubCommit.sha != null ? githubCommit.sha : "");
        commit.setWebUrl(githubCommit.htmlUrl != null ? githubCommit.htmlUrl : "");

        if (githubCommit.commit != null) {
            String message = githubCommit.commit.message != null ? githubCommit.commit.message : "";
            commit.setTitle(message.length() < 20 ? message : message.substring(0, 20));
            commit.setMessage(message);

            if (githubCommit.commit.author != null) {
                commit.setAuthorName(githubCommit.commit.author.name != null ?
                        githubCommit.commit.author.name : "");
                commit.setAuthorEmail(githubCommit.commit.author.email != null ?
                        githubCommit.commit.author.email : "");
                commit.setAuthoredDate(githubCommit.commit.author.date != null ?
                        githubCommit.commit.author.date : "");
            } else {
                commit.setAuthorName("");
                commit.setAuthorEmail("");
                commit.setAuthoredDate("");
            }
        } else {
            commit.setTitle("");
            commit.setMessage("");
            commit.setAuthorName("");
            commit.setAuthorEmail("");
            commit.setAuthoredDate("");
        }

        return commit;
    }

    public Comment transformComment(aiss.github.model.issuedata.Comment githubComment) {
        if (githubComment == null) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId(githubComment.id != null ? githubComment.id.toString() : "");

        comment.setBody(githubComment.body != null ? githubComment.body.trim() : "(no content)");

        if (githubComment.user != null) {
            User author = new User();
            author.setId(githubComment.user.id != null ? githubComment.user.id.toString() : "");
            author.setUsername(githubComment.user.login != null ? githubComment.user.login : "");
            author.setName("");

            author.setAvatarUrl(githubComment.user.avatarUrl);
            author.setWebUrl(githubComment.user.url);

            comment.setAuthor(author);
        }

        comment.setCreatedAt(githubComment.createdAt != null ? githubComment.createdAt : "");
        comment.setUpdatedAt(githubComment.updatedAt != null ? githubComment.updatedAt : "");

        return comment;
    }

    public Issue transformIssue(IssueData githubIssue, List<Comment> comments) {
        Issue issue = new Issue();

        issue.setId(githubIssue.id != null ? githubIssue.id.toString() : null);
        issue.setTitle(githubIssue.title != null ? githubIssue.title : "");
        issue.setDescription(githubIssue.body != null ? githubIssue.body : "");
        issue.setState(githubIssue.state != null ? githubIssue.state : "open");

        issue.setCreatedAt(githubIssue.createdAt != null ? githubIssue.createdAt : "");
        issue.setUpdatedAt(githubIssue.updatedAt != null ? githubIssue.updatedAt : "");
        issue.setClosedAt(githubIssue.closedAt instanceof String ? (String) githubIssue.closedAt : null);

        if (githubIssue.user != null) {
            User author = new User();
            author.setId(githubIssue.user.id != null ? githubIssue.user.id.toString() : null);
            author.setUsername(githubIssue.user.login != null ? githubIssue.user.login : "");
            author.setName(""); // GitHub no proporciona name en el user básico
            author.setAvatarUrl(githubIssue.user.avatarUrl);
            author.setWebUrl(githubIssue.user.url);
            issue.setAuthor(author);
        }

        if (githubIssue.assignee != null) {
            User assignee = new User();
            assignee.setId(githubIssue.assignee.id != null ? githubIssue.assignee.id.toString() : null);
            assignee.setUsername(githubIssue.assignee.login != null ? githubIssue.assignee.login : "");
            assignee.setName(""); // GitHub no proporciona name en el assignee básico
            assignee.setAvatarUrl(githubIssue.assignee.avatarUrl);
            assignee.setWebUrl(githubIssue.assignee.url);
            issue.setAssignee(assignee);
        }

        issue.setVotes(githubIssue.reactions != null && githubIssue.reactions.totalCount != null ?
                githubIssue.reactions.totalCount : 0);

        if (githubIssue.labels != null) {
            List<String> labelStrings = githubIssue.labels.stream()
                    .map(label -> label.url != null ? label.url : "")
                    .collect(Collectors.toList());
            issue.setLabels(labelStrings);
        } else {
            issue.setLabels(new ArrayList<>());
        }

        issue.setComments(comments != null ? comments : new ArrayList<>());

        return issue;
    }


}
