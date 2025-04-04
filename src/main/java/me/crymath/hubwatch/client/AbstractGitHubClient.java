package me.crymath.hubwatch.client;

import java.util.Objects;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import me.crymath.hubwatch.exception.GitHubApiException;
import me.crymath.hubwatch.exception.GitHubRateLimitExceededException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/** Abstract base class for GitHub API clients. Centralizes error handling. */
@Slf4j
public abstract class AbstractGitHubClient {

    protected final RestTemplate restTemplate;

    protected AbstractGitHubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /** Executes the given supplier and converts HTTP errors into GitHubApiException. */
    protected <T> T execute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException e) {
            log.error("GitHub API error: {}", e.getResponseBodyAsString());
            throw convertException(e);
        }
    }

    /** Converts an HttpClientErrorException into a structured exception. */
    protected RuntimeException convertException(HttpClientErrorException e) {
        int status = e.getStatusCode().value();
        if (status == 404) {
            return new GitHubApiException("Resource not found: " + e.getResponseBodyAsString(), status);
        } else if (status == 401 || status == 403) {
            String remaining = Objects.requireNonNull(e.getResponseHeaders()).getFirst("X-RateLimit-Remaining");
            if ("0".equals(remaining)) {
                String reset = Objects.requireNonNull(e.getResponseHeaders()).getFirst("X-RateLimit-Reset");
                long resetEpoch = reset != null ? Long.parseLong(reset) : 0;
                return new GitHubRateLimitExceededException(
                        "Rate limit exceeded: " + e.getResponseBodyAsString(), status, resetEpoch);
            }
            return new GitHubApiException("Authentication failed: " + e.getResponseBodyAsString(), status);
        }
        return new GitHubApiException("GitHub API error: " + e.getResponseBodyAsString(), status, e);
    }
}
