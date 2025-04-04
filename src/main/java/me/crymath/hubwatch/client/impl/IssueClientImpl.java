package me.crymath.hubwatch.client.impl;

import java.util.List;
import me.crymath.hubwatch.client.AbstractGitHubClient;
import me.crymath.hubwatch.client.IssueClient;
import me.crymath.hubwatch.model.Comment;
import me.crymath.hubwatch.model.Issue;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class IssueClientImpl extends AbstractGitHubClient implements IssueClient {

    public IssueClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Issue> listIssues(String owner, String repo) {
        return execute(() -> restTemplate
                .exchange(
                        "/repos/{owner}/{repo}/issues?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Issue>>() {},
                        owner,
                        repo)
                .getBody());
    }

    @Override
    public Issue getIssue(String owner, String repo, int issueNumber) {
        return execute(() -> restTemplate.getForObject(
                "/repos/{owner}/{repo}/issues/{issueNumber}", Issue.class, owner, repo, issueNumber));
    }

    @Override
    public List<Comment> listIssueComments(String owner, String repo, int issueNumber) {
        return execute(() -> restTemplate
                .exchange(
                        "/repos/{owner}/{repo}/issues/{issueNumber}/comments?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {},
                        owner,
                        repo,
                        issueNumber)
                .getBody());
    }
}
