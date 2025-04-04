package me.crymath.hubwatch.client.impl;

import me.crymath.hubwatch.client.AbstractGitHubClient;
import me.crymath.hubwatch.client.UserClient;
import me.crymath.hubwatch.model.User;
import org.springframework.web.client.RestTemplate;

public class UserClientImpl extends AbstractGitHubClient implements UserClient {

    public UserClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public User getUser(String username) {
        return execute(() -> restTemplate.getForObject("/users/{username}", User.class, username));
    }

    @Override
    public User getAuthenticatedUser() {
        return execute(() -> restTemplate.getForObject("/user", User.class));
    }

    @Override
    public User getOrganization(String organization) {
        return execute(() -> restTemplate.getForObject("/orgs/{org}", User.class, organization));
    }
}
