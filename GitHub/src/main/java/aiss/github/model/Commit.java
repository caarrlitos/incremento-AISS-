package aiss.github.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "commits")
public class Commit {

    @Id
    private String id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "message")
    private String message;

    @Column(name = "author_name")
    private String author_name;

    @Column(name = "author_email")
    private String author_email;

    @Column(name = "authored_date")
    private String authored_date;

    @Column(name = "web_url")
    private String web_url;

    @Column(name = "retrieved_at")
    private String retrieved_at;

    @Column(name = "is_merge_commit")
    private Boolean is_merge_commit;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_platform")
    private SourcePlatform source_platform;

    public Commit() {}

    public Commit(String id, String title, String message, String author_name, String author_email,
                  String authored_date, String web_url, String retrieved_at,
                  Boolean is_merge_commit, SourcePlatform source_platform) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.author_name = author_name;
        this.author_email = author_email;
        this.authored_date = authored_date;
        this.web_url = web_url;
        this.retrieved_at = retrieved_at;
        this.is_merge_commit = is_merge_commit;
        this.source_platform = source_platform;
    }

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

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getAuthored_date() {
        return authored_date;
    }

    public void setAuthored_date(String authored_date) {
        this.authored_date = authored_date;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getRetrieved_at() {
        return retrieved_at;
    }

    public void setRetrieved_at(String retrieved_at) {
        this.retrieved_at = retrieved_at;
    }

    public Boolean getIs_merge_commit() {
        return is_merge_commit;
    }

    public void setIs_merge_commit(Boolean is_merge_commit) {
        this.is_merge_commit = is_merge_commit;
    }

    public SourcePlatform getSource_platform() {
        return source_platform;
    }

    public void setSource_platform(SourcePlatform source_platform) {
        this.source_platform = source_platform;
    }
}
