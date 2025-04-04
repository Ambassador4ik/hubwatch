package me.crymath.hubwatch.client.impl;

import java.util.List;
import me.crymath.hubwatch.client.AbstractGitHubClient;
import me.crymath.hubwatch.client.RepositoryClient;
import me.crymath.hubwatch.model.Branch;
import me.crymath.hubwatch.model.Comment;
import me.crymath.hubwatch.model.Commit;
import me.crymath.hubwatch.model.Release;
import me.crymath.hubwatch.model.Repository;
import me.crymath.hubwatch.model.Tag;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class RepositoryClientImpl extends AbstractGitHubClient implements RepositoryClient {

    public RepositoryClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Repository> listUserRepositories(String username) {
        return execute(() -> restTemplate
                .exchange(
                        "/users/{username}/repos?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Repository>>() {},
                        username)
                .getBody());
    }

    @Override
    public List<Repository> listAuthenticatedUserRepositories() {
        return execute(() -> restTemplate
                .exchange(
                        "/user/repos?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Repository>>() {})
                .getBody());
    }

    @Override
    public List<Repository> listOrgRepositories(String organization) {
        return execute(() -> restTemplate
                .exchange(
                        "/orgs/{org}/repos?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Repository>>() {},
                        organization)
                .getBody());
    }

    @Override
    public Repository getRepository(String owner, String repo) {
        return execute(() -> restTemplate.getForObject("/repos/{owner}/{repo}", Repository.class, owner, repo));
    }

    @Override
    public List<Branch> listBranches(String owner, String repo) {
        return execute(() -> restTemplate
                .exchange(
                        "/repos/{owner}/{repo}/branches?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Branch>>() {},
                        owner,
                        repo)
                .getBody());
    }

    @Override
    public Branch getBranch(String owner, String repo, String branch) {
        return execute(() -> restTemplate.getForObject(
                "/repos/{owner}/{repo}/branches/{branch}", Branch.class, owner, repo, branch));
    }

    @Override
    public List<Tag> listTags(String owner, String repo) {
        return execute(() -> restTemplate
                .exchange(
                        "/repos/{owner}/{repo}/tags",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Tag>>() {},
                        owner,
                        repo)
                .getBody());
    }

    @Override
    public List<Release> listReleases(String owner, String repo) {
        return execute(() -> restTemplate
                .exchange(
                        "/repos/{owner}/{repo}/releases",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Release>>() {},
                        owner,
                        repo)
                .getBody());
    }

    @Override
    public Release getLatestRelease(String owner, String repo) {
        return execute(
                () -> restTemplate.getForObject("/repos/{owner}/{repo}/releases/latest", Release.class, owner, repo));
    }

    @Override
    public List<Commit> listCommits(String owner, String repo, String shaOrBranch) {
        String url = (shaOrBranch == null || shaOrBranch.isEmpty())
                ? "/repos/{owner}/{repo}/commits"
                : "/repos/{owner}/{repo}/commits?sha={shaOrBranch}";
        return execute(() -> restTemplate
                .exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Commit>>() {},
                        owner,
                        repo,
                        shaOrBranch)
                .getBody());
    }

    @Override
    public Commit getCommit(String owner, String repo, String sha) {
        return execute(
                () -> restTemplate.getForObject("/repos/{owner}/{repo}/commits/{sha}", Commit.class, owner, repo, sha));
    }

    @Override
    public List<Comment> listCommitComments(String owner, String repo, String commitSha) {
        return execute(() -> restTemplate
                .exchange(
                        "/repos/{owner}/{repo}/commits/{sha}/comments",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {},
                        owner,
                        repo,
                        commitSha)
                .getBody());
    }
}
