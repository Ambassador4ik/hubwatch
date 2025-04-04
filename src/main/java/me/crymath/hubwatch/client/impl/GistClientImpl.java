package me.crymath.hubwatch.client.impl;

import java.util.List;
import me.crymath.hubwatch.client.AbstractGitHubClient;
import me.crymath.hubwatch.client.GistClient;
import me.crymath.hubwatch.model.Comment;
import me.crymath.hubwatch.model.Gist;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class GistClientImpl extends AbstractGitHubClient implements GistClient {

    public GistClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Gist> listUserGists(String username) {
        return execute(() -> restTemplate
                .exchange(
                        "/users/{username}/gists?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Gist>>() {},
                        username)
                .getBody());
    }

    @Override
    public List<Gist> listAuthenticatedUserGists() {
        return execute(() -> restTemplate
                .exchange("/gists?per_page=100", HttpMethod.GET, null, new ParameterizedTypeReference<List<Gist>>() {})
                .getBody());
    }

    @Override
    public Gist getGist(String gistId) {
        return execute(() -> restTemplate.getForObject("/gists/{gistId}", Gist.class, gistId));
    }

    @Override
    public List<Comment> listGistComments(String gistId) {
        return execute(() -> restTemplate
                .exchange(
                        "/gists/{gistId}/comments?per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Comment>>() {},
                        gistId)
                .getBody());
    }
}
