package me.crymath.hubwatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/** Configures a RestTemplate instance for accessing the GitHub API. */
@Configuration
public class GitHubClientConfig {

    private final GitHubProperties properties;

    public GitHubClientConfig(GitHubProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RestTemplate gitHubRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Set the base URL for the GitHub API
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(properties.getApiBaseUrl()));

        // Add the authentication interceptor if a token is provided
        if (properties.getPersonalAccessToken() != null
                && !properties.getPersonalAccessToken().isEmpty()) {
            restTemplate.getInterceptors().add(new GitHubAuthInterceptor(properties.getPersonalAccessToken()));
        }
        // Add default headers for Accept and API version
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add(HttpHeaders.ACCEPT, "application/vnd.github+json");
            request.getHeaders().add("X-GitHub-Api-Version", properties.getApiVersion());
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
