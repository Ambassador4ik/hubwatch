package me.crymath.hubwatch.service;

import lombok.Getter;
import me.crymath.hubwatch.client.GistClient;
import me.crymath.hubwatch.client.IssueClient;
import me.crymath.hubwatch.client.NotificationClient;
import me.crymath.hubwatch.client.RepositoryClient;
import me.crymath.hubwatch.client.UserClient;
import me.crymath.hubwatch.client.impl.*;
import org.springframework.web.client.RestTemplate;

/** Aggregates various GitHub API clients for convenient use. */
@Getter
public class GitHubService {

    private final RepositoryClient repositoryClient;
    private final GistClient gistClient;
    private final IssueClient issueClient;
    private final UserClient userClient;
    private final NotificationClient notificationClient;

    public GitHubService(RestTemplate restTemplate) {
        this.repositoryClient = new RepositoryClientImpl(restTemplate);
        this.gistClient = new GistClientImpl(restTemplate);
        this.issueClient = new IssueClientImpl(restTemplate);
        this.userClient = new UserClientImpl(restTemplate);
        this.notificationClient = new NotificationClientImpl(restTemplate);
    }
}
