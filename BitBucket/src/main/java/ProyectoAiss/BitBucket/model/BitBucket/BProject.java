package ProyectoAiss.BitBucket.model.BitBucket;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BProject")
public class BProject {

    @Id
    @JsonProperty("id")
    public String id;

    @JsonProperty("name")
    @NotEmpty(message = "The name of the project cannot be empty")
    public String name;

    @JsonProperty("web_url")
    @NotEmpty(message = "The URL of the project cannot be empty")
    public String webUrl;

    @JsonProperty("retrieved_at")
    private String retrieved_at;

    @JsonProperty("source_platform")
    @Enumerated(EnumType.STRING)
    private SourcePlatform sourcePlatform;

    @JsonProperty("commits")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId")
    private List<BCommit> commits;

    @JsonProperty("issues")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "projectId")
    private List<BIssue> issues;

    public BProject() {
        commits = new ArrayList<>();
        issues = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public SourcePlatform getSourcePlatform() {
        return sourcePlatform;
    }

    public void setSourcePlatform(SourcePlatform sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

    public List<BCommit> getCommits() {
        return commits;
    }

    public void setCommits(List<BCommit> BCommits) {
        this.commits = BCommits;
    }

    public List<BIssue> getIssues() {
        return issues;
    }

    public void setIssues(List<BIssue> BIssues) {
        this.issues = BIssues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BProject.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id=").append(id == null ? "<null>" : id).append(',');
        sb.append("name=").append(name == null ? "<null>" : name).append(',');
        sb.append("webUrl=").append(webUrl == null ? "<null>" : webUrl).append(',');
        sb.append("retrieved_at=").append(retrieved_at == null ? "<null>" : retrieved_at).append(',');
        sb.append("sourcePlatform=").append(sourcePlatform == null ? "<null>" : sourcePlatform).append(',');
        sb.append("commits=").append(commits == null ? "<null>" : commits).append(',');
        sb.append("issues=").append(issues == null ? "<null>" : issues).append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
