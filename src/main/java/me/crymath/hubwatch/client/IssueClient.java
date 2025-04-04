package me.crymath.hubwatch.client;

import java.util.List;
import me.crymath.hubwatch.model.Comment;
import me.crymath.hubwatch.model.Issue;

public interface IssueClient {
    List<Issue> listIssues(String owner, String repo);

    Issue getIssue(String owner, String repo, int issueNumber);

    List<Comment> listIssueComments(String owner, String repo, int issueNumber);
}
