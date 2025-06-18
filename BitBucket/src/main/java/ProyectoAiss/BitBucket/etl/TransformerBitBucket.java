package ProyectoAiss.BitBucket.etl;

import ProyectoAiss.BitBucket.model.BitBucket.BComment;
import ProyectoAiss.BitBucket.model.BitBucket.BCommit;
import ProyectoAiss.BitBucket.model.BitBucket.BIssue;
import ProyectoAiss.BitBucket.model.BitBucket.CommitData.BCCommitData;
import ProyectoAiss.BitBucket.model.BitBucket.BUser;
import ProyectoAiss.BitBucket.model.BitBucket.IssueData.BIComments;
import ProyectoAiss.BitBucket.model.BitBucket.IssueData.BIssueData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component                          // a partir de los datos que llegan de bitbucket creamos los objetos que vamos a devolver
public class TransformerBitBucket {
    public BCommit transformCommit(BCCommitData bitBucketCommit) {
        BCommit commit = new BCommit();

        commit.setId(bitBucketCommit.hash);
        commit.setTitle(bitBucketCommit.message.split("\n")[0]);
        commit.setMessage(bitBucketCommit.message);
        commit.setIsMergeCommit(bitBucketCommit.message.toLowerCase().contains("merge"));
        commit.setAuthoredDate(bitBucketCommit.date);
        commit.setAuthorName(bitBucketCommit.author.user.displayName);
        String raw = bitBucketCommit.author.raw;
        String email = raw != null && raw.contains("<") ? raw.substring(raw.indexOf("<") + 1, raw.indexOf(">")) : "";
        commit.setAuthorEmail(email);
        commit.setWebUrl(bitBucketCommit.links.html.href);
        if (bitBucketCommit.repository != null) {
            commit.setRepositoryId(bitBucketCommit.repository.uuid);
            commit.setRepositoryName(bitBucketCommit.repository.name);

            if (bitBucketCommit.repository.links != null &&
                    bitBucketCommit.repository.links.html != null) {
                commit.setRepositoryUrl(bitBucketCommit.repository.links.html.href);
            } else {
                commit.setRepositoryUrl("https://bitbucket.org/" + bitBucketCommit.repository.fullName);
            }
        }

        commit.setRetrieved_at(LocalDateTime.now().toString());



        return commit;
    }

    public BComment transformComment(BIComments bitBucketIssueComment) {
        BUser user = null;

        if (bitBucketIssueComment.user != null) {
            user = new BUser();
            user.setId(bitBucketIssueComment.user.uuid);
            user.setUsername(bitBucketIssueComment.user.nickname);
            user.setName(bitBucketIssueComment.user.displayName);

            if (bitBucketIssueComment.user.links != null) {
                if (bitBucketIssueComment.user.links.avatar != null) {
                    user.setAvatarUrl(bitBucketIssueComment.user.links.avatar.href);
                }
                if (bitBucketIssueComment.user.links.html != null) {
                    user.setWebUrl(bitBucketIssueComment.user.links.html.href);
                }
            }
        }

        BComment comment = new BComment();
        comment.setId(bitBucketIssueComment.id.toString());
        comment.setAuthor(user);

        if (bitBucketIssueComment.content != null && bitBucketIssueComment.content.raw != null) {
            comment.setBody(bitBucketIssueComment.content.raw);
        } else {
            comment.setBody("(no content)");
        }

        comment.setCreatedAt(bitBucketIssueComment.createdOn);

        if (bitBucketIssueComment.updatedOn != null) {
            comment.setUpdatedAt(bitBucketIssueComment.updatedOn.toString());
        }

        boolean isCommentFromBot = false;

        if (bitBucketIssueComment.user != null) {
            user = new BUser();
            user.setId(bitBucketIssueComment.user.uuid);
            user.setUsername(bitBucketIssueComment.user.nickname);
            user.setName(bitBucketIssueComment.user.displayName);

            if (bitBucketIssueComment.user.links != null) {
                if (bitBucketIssueComment.user.links.avatar != null) {
                    user.setAvatarUrl(bitBucketIssueComment.user.links.avatar.href);
                }
                if (bitBucketIssueComment.user.links.html != null) {
                    user.setWebUrl(bitBucketIssueComment.user.links.html.href);
                }
            }
            if (user.getUsername() != null) {
                String username = user.getUsername().toLowerCase();
                if (username.endsWith("bot") || username.contains("automation") || username.contains("ci-cd")) {
                    isCommentFromBot = true;
                }
            }
        }
        comment.setIsBot(isCommentFromBot);

        comment.setRetrieved_at(LocalDateTime.now().toString());


        return comment;
    }


    public BIssue transformIssue(BIssueData issueData, List<BComment> comments) {
        BIssue issue = new BIssue();

        issue.setId(issueData.id != null ? issueData.id.toString() : null);
        issue.setTitle(issueData.title);
        issue.setDescription(issueData.content.raw);
        issue.setState(issueData.state);
        issue.setCreatedAt(issueData.createdOn);
        issue.setUpdatedAt(issueData.updatedOn);
        issue.setClosedAt("closed".equalsIgnoreCase(issueData.state) ? issueData.updatedOn : null);

        if (issueData.reporter != null) {
            BUser author = new BUser();
            author.setId(issueData.reporter.uuid);
            author.setUsername(issueData.reporter.nickname);
            author.setName(issueData.reporter.displayName);

            if (issueData.reporter.links != null) {
                if (issueData.reporter.links.avatar != null)
                    author.setAvatarUrl(issueData.reporter.links.avatar.href);
                if (issueData.reporter.links.html != null)
                    author.setWebUrl(issueData.reporter.links.html.href);
            }

            issue.setAuthor(author);
        } else {
            issue.setAuthor(null);
        }

        if (issueData.assignee != null) {
            BUser assignee = new BUser();
            assignee.setId(issueData.assignee.uuid);
            assignee.setUsername(issueData.assignee.nickname);
            assignee.setName(issueData.assignee.displayName);
            if (issueData.assignee.links != null) {
                assignee.setAvatarUrl(issueData.assignee.links.avatar.href);
                assignee.setWebUrl(issueData.assignee.links.html.href);
            }
            issue.setAssignee(assignee);
        }
        else{
            issue.setAssignee(null);
        }
        issue.setVotes(issueData.votes);
        issue.setWebUrl(issueData.links.html.href);
        issue.setLabels(new ArrayList<>());
        issue.setComments(comments);
        issue.setNumComments(comments != null ? comments.size() : 0);
        issue.setRetrieved_at(LocalDateTime.now().toString());


        return issue;
    }

}
