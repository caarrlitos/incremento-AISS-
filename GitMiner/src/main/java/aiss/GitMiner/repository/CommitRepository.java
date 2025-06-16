package aiss.GitMiner.repository;

import aiss.GitMiner.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitRepository extends JpaRepository<Commit, String> {
    List<Commit> findByAuthorEmail(String author_email);   }
