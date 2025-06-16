package aiss.GitMiner.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@JsonPropertyOrder({ "id", "username", "name", "web_url", "avatar_url" })
public class User {
    @Id
    private String id;

    @Column(name = "username")
    @NotEmpty(message = "User username is required")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar_url")
    private String avatar_url;

    @Column(name = "web_url")
    private String web_url;

    public User() {}

    public User(String id, String username, String name, String avatar_url, String web_url) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.avatar_url = avatar_url;
        this.web_url = web_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatar_url;
    }

    public void setAvatarUrl(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getWebUrl() {
        return web_url;
    }

    public void setWebUrl(String web_url) {
        this.web_url = web_url;
    }

}
