package ProyectoAiss.BitBucket.service;

import ProyectoAiss.BitBucket.etl.TransformerBitBucket;
import ProyectoAiss.BitBucket.model.BitBucket.BCommit;
import ProyectoAiss.BitBucket.model.BitBucket.CommitData.BCCommitData;
import ProyectoAiss.BitBucket.model.BitBucket.CommitData.CommitsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BitBucketCommitService {

    @Autowired
    RestTemplate restTemplate;

    private final TransformerBitBucket transformer;

    public BitBucketCommitService(TransformerBitBucket transformer) {
        this.transformer = transformer;
    }

    public List<BCommit> fetchCommits(String workspace, String repoSlug, int nCommits, int maxPages) {
        List<BCommit> commits = new ArrayList<>();

        for (int page = 1; page <= maxPages; page++) {
            String url = "https://api.bitbucket.org/2.0/repositories/" + workspace + "/" + repoSlug
                    + "/commits?pagelen=" + nCommits + "&page=" + page;

            CommitsPage response = restTemplate.getForObject(url, CommitsPage.class);

            if (response != null && response.values != null) {
                for (BCCommitData rawCommit : response.values) {
                    commits.add(transformer.transformCommit(rawCommit));
                }

                if (response.values.size() < nCommits) {
                    break;
                }
            } else {
                break;
            }
        }

        return commits;
    }
}
