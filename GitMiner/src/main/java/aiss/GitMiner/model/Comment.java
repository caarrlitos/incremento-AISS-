package aiss.GitMiner.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@JsonPropertyOrder({ "id", "body", "author", "created_at", "updated_at", "retrieved_at", "isBot", "source_platform" })
public class Comment {

    @Id
    private String id;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "created_at")
    private String created_at;

    @Column(name = "updated_at")
    private String updated_at;

    @Column(name = "retrieved_at")
    private String retrieved_at;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_platform")
    private SourcePlatform sourcePlatform;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    private User author;

    @Column(name = "isBot")
    private boolean isBot;

    public Comment() {}

    public Comment(String id, String body, String created_at, String updated_at, String retrieved_at, SourcePlatform sourcePlatform, User author, boolean isBot) {
        this.id = id;
        this.body = body;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.retrieved_at = retrieved_at;
        this.sourcePlatform = sourcePlatform;
        this.author = author;
        this.isBot = isBot;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean getIsBot() {return isBot; }

    public void setIsBot(boolean isBot) {
        this.isBot = isBot;
    }

}
