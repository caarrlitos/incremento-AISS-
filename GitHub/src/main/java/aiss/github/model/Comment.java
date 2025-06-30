package aiss.github.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "comments")
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
    private SourcePlatform source_platform;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorId")
    private User author;

    @Column(name = "is_bot")
    private boolean is_bot;

    public Comment() {}

    public Comment(String id, String body, String created_at, String updated_at, String retrieved_at,
                   SourcePlatform source_platform, User author, boolean is_bot) {
        this.id = id;
        this.body = body;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.retrieved_at = retrieved_at;
        this.source_platform = source_platform;
        this.author = author;
        this.is_bot = is_bot;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getRetrieved_at() {
        return retrieved_at;
    }

    public void setRetrieved_at(String retrieved_at) {
        this.retrieved_at = retrieved_at;
    }

    public SourcePlatform getSource_platform() {
        return source_platform;
    }

    public void setSource_platform(SourcePlatform source_platform) {
        this.source_platform = source_platform;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean getIs_bot() {
        return is_bot;
    }

    public void setIs_bot(boolean is_bot) {
        this.is_bot = is_bot;
    }
}
