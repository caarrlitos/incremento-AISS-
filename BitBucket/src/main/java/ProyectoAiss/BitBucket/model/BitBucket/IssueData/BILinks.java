
package ProyectoAiss.BitBucket.model.BitBucket.IssueData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "comments",
    "self",
    "html",
    "avatar"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class BILinks {

    @JsonProperty("comments")
    public BIHtml comments;

    @JsonProperty("html")
    public BIHtml html;

    @JsonProperty("self")
    public BIHtml self;

    @JsonProperty("avatar")
    public BIAvatar avatar;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BILinks.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("html");
        sb.append('=');
        sb.append(((this.html == null)?"<null>":this.html));
        sb.append(',');
        sb.append("comments");
        sb.append('=');
        sb.append(((this.comments == null)?"<null>":this.comments));
        sb.append(',');
        sb.append("self");
        sb.append('=');
        sb.append(((this.self == null)?"<null>":this.self));
        sb.append(',');
        sb.append("avatar");
        sb.append('=');
        sb.append(((this.avatar == null)?"<null>":this.avatar));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
