package ProyectoAiss.BitBucket.model.BitBucket;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "BIssue")
public class BIssue {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    @Column(columnDefinition = "TEXT")
    private String description;

    @JsonProperty("state")
    private String state;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("closed_at")
    private String closedAt;

    @JsonProperty("retrieved_at")
    private String retrieved_at;

    @JsonProperty("num_comments")
    private Integer numComments;

    @JsonProperty("labels")
    @ElementCollection
    private List<String> labels;

    @JsonProperty("author")
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private BUser author;

    @JsonProperty("assignee")
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private BUser assignee;

    @JsonProperty("votes")
    private Integer votes;

    @JsonProperty("web_url")
    private String webUrl;

    @JsonProperty("comments")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "issueId")
    private List<BComment> comments;


    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getRetrieved_at() {
        return retrieved_at;
    }

    public void setRetrieved_at(String retrieved_at) {
        this.retrieved_at = retrieved_at;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public BUser getAuthor() {
        return author;
    }

    public void setAuthor(BUser author) {
        this.author = author;
    }

    public BUser getAssignee() {
        return assignee;
    }

    public void setAssignee(BUser assignee) {
        this.assignee = assignee;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public List<BComment> getComments() {
        return comments;
    }

    public void setComments(List<BComment> comments) {
        this.comments = comments;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BIssue.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id=").append(id == null ? "<null>" : id).append(',');
        sb.append("title=").append(title == null ? "<null>" : title).append(',');
        sb.append("description=").append(description == null ? "<null>" : description).append(',');
        sb.append("state=").append(state == null ? "<null>" : state).append(',');
        sb.append("createdAt=").append(createdAt == null ? "<null>" : createdAt).append(',');
        sb.append("updatedAt=").append(updatedAt == null ? "<null>" : updatedAt).append(',');
        sb.append("closedAt=").append(closedAt == null ? "<null>" : closedAt).append(',');
        sb.append("retrieved_at=").append(retrieved_at == null ? "<null>" : retrieved_at).append(',');
        sb.append("labels=").append(labels == null ? "<null>" : labels).append(',');
        sb.append("author=").append(author == null ? "<null>" : author).append(',');
        sb.append("assignee=").append(assignee == null ? "<null>" : assignee).append(',');
        sb.append("votes=").append(votes == null ? "<null>" : votes).append(',');
        sb.append("comments=").append(comments == null ? "<null>" : comments).append(',');
        sb.append("numComments=").append(numComments == null ? "<null>" : numComments).append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
