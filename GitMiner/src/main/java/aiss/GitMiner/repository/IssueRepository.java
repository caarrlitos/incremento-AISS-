package aiss.GitMiner.repository;

import aiss.GitMiner.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    List<Issue> findByAuthor_Id(String authorId);

    List<Issue> findByState(String state);
}
