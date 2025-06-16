package ProyectoAiss.BitBucket.model.BitBucket.CommitData;

import ProyectoAiss.BitBucket.model.BitBucket.BCommit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitsPage {                            //necesario para la paginacion

    @JsonProperty("values")
    public List<BCCommitData> values;

}

