package aiss.GitMiner.controller;

import aiss.GitMiner.model.Commit;
import aiss.GitMiner.repository.CommitRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {

    @Autowired
    CommitRepository commitRepository;

    @GetMapping
    public List<Commit> getAllCommits(@RequestParam(required = false) String author_email) {
        if (author_email != null) {
            return commitRepository.findByAuthorEmail(author_email);
        } else {
            return commitRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    public Commit getCommitById(@PathVariable String id) {
        Optional<Commit> commit = commitRepository.findById(id);
        return commit.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Commit create(@Valid @RequestBody Commit commit) {
        Commit newCommit = commitRepository.save(
                new Commit(commit.getId(), commit.getTitle(), commit.getMessage(), commit.getAuthorName(), commit.getAuthorEmail(), commit.getAuthoredDate(), commit.getWebUrl(), commit.getRetrieved_at(), commit.getIsMergeCommit()));
        return newCommit;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Commit updatedCommit, @PathVariable String id) {
        Optional<Commit> commit = commitRepository.findById(id);

        Commit _commit = commit.get();
        _commit.setId(updatedCommit.getId());
        _commit.setTitle(updatedCommit.getTitle());
        _commit.setMessage(updatedCommit.getMessage());
        _commit.setAuthorName(updatedCommit.getAuthorName());
        _commit.setAuthorEmail(updatedCommit.getAuthorEmail());
        _commit.setAuthoredDate(updatedCommit.getAuthoredDate());
        _commit.setWebUrl(updatedCommit.getWebUrl());
        commitRepository.save(_commit);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (commitRepository.existsById(id)) {
            commitRepository.deleteById(id);
        }
    }
}
