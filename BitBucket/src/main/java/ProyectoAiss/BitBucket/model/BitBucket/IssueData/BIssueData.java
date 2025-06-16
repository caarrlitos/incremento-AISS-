
package ProyectoAiss.BitBucket.model.BitBucket.IssueData;

import ProyectoAiss.BitBucket.model.BitBucket.CommitData.BCLinks;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "id",
    "BIRepository",
    "BILinks",
    "title",
    "BIContent",
    "BIReporter",
    "assignee",
    "created_on",
    "edited_on",
    "updated_on",
    "state",
    "kind",
    "milestone",
    "component",
    "priority",
    "version",
    "votes",
    "watches"
})

@JsonIgnoreProperties(ignoreUnknown = true)
public class BIssueData {

    @JsonProperty("type")
    public String type;
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("repository")
    public BIRepository repository;
    @JsonProperty("links")
    public BILinks links;
    @JsonProperty("title")
    public String title;
    @JsonProperty("content")
    public BIContent content;
    @JsonProperty("reporter")
    public BIReporter reporter;
    @JsonProperty("assignee")
    public BIUser assignee;
    @JsonProperty("created_on")
    public String createdOn;
    @JsonProperty("edited_on")
    public Object editedOn;
    @JsonProperty("updated_on")
    public String updatedOn;
    @JsonProperty("state")
    public String state;
    @JsonProperty("votes")
    public Integer votes;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(BIssueData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("repository");
        sb.append('=');
        sb.append(((this.repository == null)?"<null>":this.repository));
        sb.append(',');
        sb.append("links");
        sb.append('=');
        sb.append(((this.links == null)?"<null>":this.links));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null)?"<null>":this.content));
        sb.append(',');
        sb.append("reporter");
        sb.append('=');
        sb.append(((this.reporter == null)?"<null>":this.reporter));
        sb.append(',');
        sb.append("assignee");
        sb.append('=');
        sb.append(((this.assignee == null)?"<null>":this.assignee));
        sb.append(',');
        sb.append("createdOn");
        sb.append('=');
        sb.append(((this.createdOn == null)?"<null>":this.createdOn));
        sb.append(',');
        sb.append("editedOn");
        sb.append('=');
        sb.append(((this.editedOn == null)?"<null>":this.editedOn));
        sb.append(',');
        sb.append("updatedOn");
        sb.append('=');
        sb.append(((this.updatedOn == null)?"<null>":this.updatedOn));
        sb.append(',');
        sb.append("votes");
        sb.append('=');
        sb.append(((this.votes == null)?"<null>":this.votes));
        sb.append(',');
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));

        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
