
package ProyectoAiss.BitBucket.model.BitBucket.IssueData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "full_name",
    "BILinks",
    "name",
    "uuid"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class BIRepository {

    @JsonProperty("type")
    public String type;
    @JsonProperty("full_name")
    public String fullName;
    @JsonProperty("BILinks")
    public BILinks links;
    @JsonProperty("name")
    public String name;
    @JsonProperty("uuid")
    public String uuid;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BIRepository.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("fullName");
        sb.append('=');
        sb.append(((this.fullName == null)?"<null>":this.fullName));
        sb.append(',');
        sb.append("BILinks");
        sb.append('=');
        sb.append(((this.links == null)?"<null>":this.links));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("uuid");
        sb.append('=');
        sb.append(((this.uuid == null)?"<null>":this.uuid));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
