package me.crymath.hubwatch.client.impl;

import java.util.List;
import me.crymath.hubwatch.client.AbstractGitHubClient;
import me.crymath.hubwatch.client.NotificationClient;
import me.crymath.hubwatch.model.Notification;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class NotificationClientImpl extends AbstractGitHubClient implements NotificationClient {

    public NotificationClientImpl(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public List<Notification> listNotifications(boolean all, boolean participating) {
        return execute(() -> restTemplate
                .exchange(
                        "/notifications?all={all}&participating={participating}&per_page=100",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Notification>>() {},
                        all,
                        participating)
                .getBody());
    }
}
