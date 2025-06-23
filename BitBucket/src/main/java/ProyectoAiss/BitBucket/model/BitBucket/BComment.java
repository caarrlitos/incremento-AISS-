package ProyectoAiss.BitBucket.model.BitBucket;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "BComment")
public class BComment {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("body")
    @NotEmpty(message = "The message cannot be empty.")
    @Column(columnDefinition = "TEXT")
    private String body;

    @JsonProperty("Author")
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private BUser author;

    @JsonProperty("created_at")
    @NotEmpty(message = "The field created_at cannot be empty.")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("retrieved_at")
    private String retrieved_at;

    @JsonProperty("isBot")
    private boolean isBot;

    @JsonProperty("source_platform")
    @Enumerated(EnumType.STRING)
    private SourcePlatform sourcePlatform;

    // Getters and setters
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

    public BUser getAuthor() {
        return author;
    }

    public void setAuthor(BUser author) {
        this.author = author;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRetrieved_at() {
        return retrieved_at;
    }

    public void setRetrieved_at(String retrieved_at) {
        this.retrieved_at = retrieved_at;
    }

    public boolean getIsBot() {
        return isBot;
    }

    public void setIsBot(boolean isBot) {
        this.isBot = isBot;
    }

    public SourcePlatform getSourcePlatform() {
        return sourcePlatform;
    }

    public void setSourcePlatform(SourcePlatform sourcePlatform) {
        this.sourcePlatform = sourcePlatform;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BComment.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id=").append(id == null ? "<null>" : id).append(',');
        sb.append("body=").append(body == null ? "<null>" : body).append(',');
        sb.append("author=").append(author == null ? "<null>" : author).append(',');
        sb.append("createdAt=").append(createdAt == null ? "<null>" : createdAt).append(',');
        sb.append("updatedAt=").append(updatedAt == null ? "<null>" : updatedAt).append(',');
        sb.append("retrieved_at=").append(retrieved_at == null ? "<null>" : retrieved_at).append(',');
        sb.append("isBot=").append(isBot).append(',');
        sb.append("sourcePlatform=").append(sourcePlatform == null ? "<null>" : sourcePlatform);
        sb.append(']');
        return sb.toString();
    }
}
