package aiss.GitMiner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Entity
@Table(name = "projects")
@JsonPropertyOrder({ "id", "name", "web_url", "commits", "issues" })
public class Project {

    @Id
    private String id;

    @Column(name = "name")
    @NotEmpty(message = "Project name is required")
    private String name;

    @Column(name = "web_url")
    private String web_url;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Commit> commits;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private List<Issue> issues;

    public Project() {}

    public Project(String id, String name, String web_url, List<Commit> commits, List<Issue> issues) {
        this.id = id;
        this.name = name;
        this.web_url = web_url;
        this.commits = commits;
        this.issues = issues;
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
        return web_url;
    }

    public void setWebUrl(String web_url) {
        this.web_url = web_url;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
