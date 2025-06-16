
package ProyectoAiss.BitBucket.model.BitBucket.IssueData;

import ProyectoAiss.BitBucket.model.BitBucket.CommitData.BCLinks;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "display_name",
    "BILinks",
    "type",
    "uuid",
    "account_id",
    "nickname",
    "avatar"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class BIUser {

    @JsonProperty("display_name")
    public String displayName;
    @JsonProperty("links")
    public BILinks links;
    @JsonProperty("type")
    public String type;
    @JsonProperty("uuid")
    public String uuid;
    @JsonProperty("account_id")
    public String accountId;
    @JsonProperty("nickname")
    public String nickname;
    @JsonProperty("avatar")
    public BIAvatar avatar;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BIUser.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("displayName");
        sb.append('=');
        sb.append(((this.displayName == null)?"<null>":this.displayName));
        sb.append(',');
        sb.append("links");
        sb.append('=');
        sb.append(((this.links == null)?"<null>":this.links));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("uuid");
        sb.append('=');
        sb.append(((this.uuid == null)?"<null>":this.uuid));
        sb.append(',');
        sb.append("accountId");
        sb.append('=');
        sb.append(((this.accountId == null)?"<null>":this.accountId));
        sb.append(',');
        sb.append("nickname");
        sb.append('=');
        sb.append(((this.nickname == null)?"<null>":this.nickname));
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
