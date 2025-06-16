package ProyectoAiss.BitBucket.service;

import ProyectoAiss.BitBucket.etl.TransformerBitBucket;
import ProyectoAiss.BitBucket.model.BitBucket.BComment;
import ProyectoAiss.BitBucket.model.BitBucket.IssueData.BIComments;
import ProyectoAiss.BitBucket.model.BitBucket.IssueData.CommentsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class BitBucketCommentService {

    @Autowired
    RestTemplate restTemplate;

    private final TransformerBitBucket transformer;

    public BitBucketCommentService(TransformerBitBucket transformer) {
        this.transformer = transformer;
    }

    public List<BComment> fetchComments(String commentsUrl, int maxPages) {
        List<BComment> comments = new ArrayList<>();

        for (int page = 1; page <= maxPages; page++) {
            String url = commentsUrl + "?pagelen=10&page=" + page;

            CommentsPage response = restTemplate.getForObject(url, CommentsPage.class);

            if (response != null && response.values != null) {
                for (BIComments rawComment : response.values) {
                    comments.add(transformer.transformComment(rawComment));
                }

                if (response.values.size() < 10) {
                    break;
                }
            } else {
                break;
            }
        }

        return comments;
    }

}
