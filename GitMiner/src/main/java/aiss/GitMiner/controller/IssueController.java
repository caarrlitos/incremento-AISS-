package aiss.GitMiner.controller;

import aiss.GitMiner.model.Comment;
import aiss.GitMiner.model.Issue;
import aiss.GitMiner.repository.IssueRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository issueRepository;

    @GetMapping
    public List<Issue> getAllIssues(@RequestParam(required = false) String authorId,
                                    @RequestParam(required = false) String state) {
        if (authorId != null) {
            return issueRepository.findByAuthor_Id(authorId);
        } else if (state != null) {
            return issueRepository.findByState(state);
        } else {
            return issueRepository.findAll();
        }
    }

    @GetMapping("/{id}")
    public Issue getIssueById(@PathVariable String id) {
        Optional<Issue> issue = issueRepository.findById(id);
        return issue.get();
    }
    @GetMapping("/{id}/comments")
    public List<Comment> getIssueComments(@PathVariable String id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return issue.get().getComments();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Issue create(@Valid @RequestBody Issue issue) {
        Issue newIssue = issueRepository.save(new Issue(issue.getId(), issue.getTitle(), issue.getDescription(), issue.getState(), issue.getCreatedAt(), issue.getUpdatedAt(), issue.getClosedAt(), issue.getRetrieved_at(), issue.getNumComments(), issue.getSourcePlatform(), issue.getLabels(), issue.getVotes(), issue.getAuthor(), issue.getAssignee(), issue.getComments()));
        return newIssue;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Issue update(@Valid @RequestBody Issue issue, @PathVariable String id) {
        Optional<Issue> issueOptional = issueRepository.findById(id);

        Issue _issue = issueOptional.get();
        _issue.setId(id);
        _issue.setTitle(issue.getTitle());
        _issue.setDescription(issue.getDescription());
        _issue.setState(issue.getState());
        _issue.setCreatedAt(issue.getCreatedAt());
        _issue.setUpdatedAt(issue.getUpdatedAt());
        _issue.setClosedAt(issue.getClosedAt());
        _issue.setLabels(issue.getLabels());
        _issue.setVotes(issue.getVotes());
        _issue.setAuthor(issue.getAuthor());
        _issue.setAssignee(issue.getAssignee());
        _issue.setComments(issue.getComments());
        _issue.setSourcePlatform(issue.getSourcePlatform());
        return issueRepository.save(_issue);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);
        }
    }
}
