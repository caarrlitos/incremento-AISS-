package ProyectoAiss.BitBucket.model.BitBucket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "BCommit")
public class BCommit {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("message")
    @Column(columnDefinition = "TEXT")
    private String message;

    @JsonProperty("author_name")
    @NotEmpty(message = "BCAuthor name cannot be empty.")
    private String authorName;

    @JsonProperty("author_email")
    private String authorEmail;

    @JsonProperty("authored_date")
    @NotEmpty(message = "BCAuthor date cannot be empty.")
    private String authoredDate;

    @JsonProperty("web_url")
    @NotEmpty(message = "URL cannot be empty.")
    private String webUrl;

    @JsonProperty("retrieved_at")
    private String retrieved_at;

    @JsonProperty("is_merge_commit")
    private Boolean isMergeCommit;

    @JsonProperty("source_platform")
    @Enumerated(EnumType.STRING)
    private SourcePlatform sourcePlatform;

    @JsonIgnore
    private String repositoryId;

    @JsonIgnore
    private String repositoryName;

    @JsonIgnore
    private String repositoryUrl;

    // Getters y Setters

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthoredDate() {
        return authoredDate;
    }

    public void setAuthoredDate(String authoredDate) {
        this.authoredDate = authoredDate;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getRetrieved_at() {
        return retrieved_at;
    }

    public void setRetrieved_at(String retrieved_at) {
        this.retrieved_at = retrieved_at;
    }

    public Boolean getIsMergeCommit() {
        return isMergeCommit;
    }

    public void setIsMergeCommit(Boolean isMergeCommit) {
        this.isMergeCommit = isMergeCommit;
    }

    public SourcePlatform getSourcePlatform() {
        return sourcePlatform;
    }

    public void setSourcePlatform(SourcePlatform sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BCommit.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id=").append(id == null ? "<null>" : id).append(',');
        sb.append("title=").append(title == null ? "<null>" : title).append(',');
        sb.append("message=").append(message == null ? "<null>" : message).append(',');
        sb.append("authorName=").append(authorName == null ? "<null>" : authorName).append(',');
        sb.append("authorEmail=").append(authorEmail == null ? "<null>" : authorEmail).append(',');
        sb.append("authoredDate=").append(authoredDate == null ? "<null>" : authoredDate).append(',');
        sb.append("webUrl=").append(webUrl == null ? "<null>" : webUrl).append(',');
        sb.append("retrieved_at=").append(retrieved_at == null ? "<null>" : retrieved_at).append(',');
        sb.append("isMergeCommit=").append(isMergeCommit == null ? "<null>" : isMergeCommit).append(',');
        sb.append("sourcePlatform=").append(sourcePlatform == null ? "<null>" : sourcePlatform).append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
