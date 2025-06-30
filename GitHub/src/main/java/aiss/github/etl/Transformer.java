package aiss.github.etl;

import aiss.github.model.*;
import aiss.github.model.Comment;
import aiss.github.model.User;
import aiss.github.model.commitdata.CommitData;
import aiss.github.model.issuedata.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static aiss.github.model.SourcePlatform.GITHUB;

@Component
public class Transformer {

    public Commit transformCommit(CommitData githubCommit) {
        Commit commit = new Commit();

        commit.setId(githubCommit.sha != null ? githubCommit.sha : "");
        commit.setWeb_url(githubCommit.htmlUrl != null ? githubCommit.htmlUrl : "");

        if (githubCommit.commit != null) {
            String message = githubCommit.commit.message != null ? githubCommit.commit.message : "";
            commit.setTitle(message.length() < 20 ? message : message.substring(0, 20));
            commit.setMessage(message);
            commit.setIs_merge_commit(message.toLowerCase().contains("merge"));

            if (githubCommit.commit.author != null) {
                commit.setAuthor_name(githubCommit.commit.author.name != null ? githubCommit.commit.author.name : "");
                commit.setAuthor_email(githubCommit.commit.author.email != null ? githubCommit.commit.author.email : "");
                commit.setAuthored_date(githubCommit.commit.author.date != null ? githubCommit.commit.author.date : "");
            } else {
                commit.setAuthor_name("");
                commit.setAuthor_email("");
                commit.setAuthored_date("");
            }
        } else {
            commit.setTitle("");
            commit.setMessage("");
            commit.setAuthor_name("");
            commit.setAuthor_email("");
            commit.setAuthored_date("");
        }

        commit.setRetrieved_at(LocalDateTime.now().toString());
        commit.setSource_platform(GITHUB);

        return commit;
    }

    public Comment transformComment(aiss.github.model.issuedata.Comment githubComment) {
        if (githubComment == null) return null;

        Comment comment = new Comment();
        comment.setId(githubComment.id != null ? githubComment.id.toString() : "");
        comment.setBody(githubComment.body != null ? githubComment.body.trim() : "(no content)");

        if (githubComment.user != null) {
            User author = new User();
            author.setId(githubComment.user.id != null ? githubComment.user.id.toString() : "");
            author.setUsername(githubComment.user.login != null ? githubComment.user.login : "");
            author.setName("");
            author.setAvatar_url(githubComment.user.avatarUrl);
            author.setWeb_url(githubComment.user.url);
            comment.setAuthor(author);
        }

        comment.setCreated_at(githubComment.createdAt != null ? githubComment.createdAt : "");
        comment.setUpdated_at(githubComment.updatedAt != null ? githubComment.updatedAt : "");
        comment.setRetrieved_at(LocalDateTime.now().toString());

        boolean isBot = githubComment.user != null && githubComment.user.login != null &&
                (githubComment.user.login.toLowerCase().contains("[bot]") ||
                        githubComment.user.login.equalsIgnoreCase("github-actions"));

        comment.setIs_bot(isBot);
        comment.setSource_platform(GITHUB);

        return comment;
    }

    public Issue transformIssue(IssueData githubIssue, List<Comment> comments) {
        Issue issue = new Issue();

        issue.setId(githubIssue.id != null ? githubIssue.id.toString() : null);
        issue.setTitle(githubIssue.title != null ? githubIssue.title : "");
        issue.setDescription(githubIssue.body != null ? githubIssue.body : "");
        issue.setState(githubIssue.state != null ? githubIssue.state : "open");

        issue.setCreated_at(githubIssue.createdAt != null ? githubIssue.createdAt : "");
        issue.setUpdated_at(githubIssue.updatedAt != null ? githubIssue.updatedAt : "");
        issue.setClosed_at(githubIssue.closedAt instanceof String ? (String) githubIssue.closedAt : null);

        if (githubIssue.user != null) {
            User author = new User();
            author.setId(githubIssue.user.id != null ? githubIssue.user.id.toString() : null);
            author.setUsername(githubIssue.user.login != null ? githubIssue.user.login : "");
            author.setName("");
            author.setAvatar_url(githubIssue.user.avatarUrl);
            author.setWeb_url(githubIssue.user.url);
            issue.setAuthor(author);
        }

        if (githubIssue.assignee != null) {
            User assignee = new User();
            assignee.setId(githubIssue.assignee.id != null ? githubIssue.assignee.id.toString() : null);
            assignee.setUsername(githubIssue.assignee.login != null ? githubIssue.assignee.login : "");
            assignee.setName("");
            assignee.setAvatar_url(githubIssue.assignee.avatarUrl);
            assignee.setWeb_url(githubIssue.assignee.url);
            issue.setAssignee(assignee);
        }

        issue.setVotes(githubIssue.reactions != null && githubIssue.reactions.totalCount != null
                ? githubIssue.reactions.totalCount : 0);

        List<String> labelStrings = githubIssue.labels != null
                ? githubIssue.labels.stream()
                .map(label -> label.url != null ? label.url : "")
                .collect(Collectors.toList())
                : new ArrayList<>();

        issue.setLabels(labelStrings);
        issue.setComments(comments != null ? comments : new ArrayList<>());
        issue.setNum_comments(comments != null ? comments.size() : 0);
        issue.setRetrieved_at(LocalDateTime.now().toString());
        issue.setSource_platform(GITHUB);

        return issue;
    }
}
