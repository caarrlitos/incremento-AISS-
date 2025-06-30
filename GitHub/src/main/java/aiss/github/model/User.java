package aiss.github.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "GMUser") // "User" es reservado en H2
public class User {

    @Id
    private String id;

    @NotEmpty(message = "The username cannot be empty")
    private String username;

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

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(User.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id=").append(id).append(',');
        sb.append("username=").append(username).append(',');
        sb.append("name=").append(name).append(',');
        sb.append("avatar_url=").append(avatar_url).append(',');
        sb.append("web_url=").append(web_url).append(']');
        return sb.toString();
    }
}
