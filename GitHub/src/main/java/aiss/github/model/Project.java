package aiss.github.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    private String id;

    @Column(name = "name")
    @NotEmpty(message = "Project name is required")
    private String name;

    @Column(name = "web_url")
    private String web_url;

    @Column(name = "retrieved_at")
    private String retrieved_at;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_platform")
    private SourcePlatform source_platform;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Commit> commits;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Issue> issues;

    public Project() {}

    public Project(String id, String name, String web_url, String retrieved_at,
                   SourcePlatform source_platform, List<Commit> commits, List<Issue> issues) {
        this.id = id;
        this.name = name;
        this.web_url = web_url;
        this.retrieved_at = retrieved_at;
        this.source_platform = source_platform;
        this.commits = commits;
        this.issues = issues;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getWeb_url() { return web_url; }

    public void setWeb_url(String web_url) { this.web_url = web_url; }

    public String getRetrieved_at() { return retrieved_at; }

    public void setRetrieved_at(String retrieved_at) { this.retrieved_at = retrieved_at; }

    public SourcePlatform getSource_platform() { return source_platform; }

    public void setSource_platform(SourcePlatform source_platform) { this.source_platform = source_platform; }

    public List<Commit> getCommits() { return commits; }

    public void setCommits(List<Commit> commits) { this.commits = commits; }

    public List<Issue> getIssues() { return issues; }

    public void setIssues(List<Issue> issues) { this.issues = issues; }
}
