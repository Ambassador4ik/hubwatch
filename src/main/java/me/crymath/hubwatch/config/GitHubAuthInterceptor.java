package me.crymath.hubwatch.config;

import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

/** Interceptor to add GitHub authentication headers to HTTP requests. */
public class GitHubAuthInterceptor implements ClientHttpRequestInterceptor {

    private final String token;

    public GitHubAuthInterceptor(String token) {
        this.token = token;
    }

    @NonNull
    @Override
    public ClientHttpResponse intercept(HttpRequest request, @NonNull byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        request.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return execution.execute(request, body);
    }
}
