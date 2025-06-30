package aiss.github.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "issues")
public class Issue {

    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "state")
    @NotNull(message = "Issue state cannot be null")
    private String state;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;

    @Column(name = "closed_at")
    private String closed_at;

    @Column(name = "retrieved_at")
    private String retrieved_at;

    @Column(name = "num_comments")
    private Integer num_comments;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_platform")
    private SourcePlatform source_platform;

    @ElementCollection
    @CollectionTable(name = "issueLabels", joinColumns = @JoinColumn(name = "issue_id"))
    @Column(name = "labels")
    private List<String> labels;

    @Column(name = "votes")
    private Integer votes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "issue_id")
    private List<Comment> comments;

    public Issue() {}

    public Issue(String id, String title, String description, String state,
                 String created_at, String updated_at, String closed_at, String retrieved_at,
                 Integer num_comments, SourcePlatform source_platform, List<String> labels,
                 Integer votes, User author, User assignee, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.closed_at = closed_at;
        this.retrieved_at = retrieved_at;
        this.num_comments = num_comments;
        this.source_platform = source_platform;
        this.labels = labels;
        this.votes = votes;
        this.author = author;
        this.assignee = assignee;
        this.comments = comments;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getCreated_at() { return created_at; }

    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public String getUpdated_at() { return updated_at; }

    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }

    public String getClosed_at() { return closed_at; }

    public void setClosed_at(String closed_at) { this.closed_at = closed_at; }

    public String getRetrieved_at() { return retrieved_at; }

    public void setRetrieved_at(String retrieved_at) { this.retrieved_at = retrieved_at; }

    public Integer getNum_comments() { return num_comments; }

    public void setNum_comments(Integer num_comments) { this.num_comments = num_comments; }

    public SourcePlatform getSource_platform() { return source_platform; }

    public void setSource_platform(SourcePlatform source_platform) { this.source_platform = source_platform; }

    public List<String> getLabels() { return labels; }

    public void setLabels(List<String> labels) { this.labels = labels; }

    public Integer getVotes() { return votes; }

    public void setVotes(Integer votes) { this.votes = votes; }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public User getAssignee() { return assignee; }

    public void setAssignee(User assignee) { this.assignee = assignee; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }
}
