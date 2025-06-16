package aiss.GitMiner.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Entity
@Table(name = "commits")
@JsonPropertyOrder({ "id", "title", "message", "author_name", "author_email", "authored_date", "web_url" })
public class Commit {

    @Id                          //id no es generado porque nos lo dan el post
    private String id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "message")
    private String message;

    @Column(name = "author_name")
    private String author_name;

    @Column(name = "author_email")
    private String authorEmail;

    @Column(name = "authored_date")
    private String authored_date;

    @Column(name = "web_url")
    private String web_url;

    public Commit() {}  //constructor vacio

    public Commit(String id, String title, String message, String author_name, String author_email, String authored_date, String web_url) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.author_name = author_name;
        this.authorEmail = author_email;
        this.authored_date = authored_date;
        this.web_url = web_url;
    }    //constructor con parametros

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
        return author_name;
    }

    public void setAuthorName(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String author_email) {
        this.authorEmail = author_email;
    }

    public String getAuthoredDate() {
        return authored_date;
    }

    public void setAuthoredDate(String authored_date) {
        this.authored_date = authored_date;
    }

    public String getWebUrl() {
        return web_url;
    }

    public void setWebUrl(String web_url) {
        this.web_url = web_url;
    }
}