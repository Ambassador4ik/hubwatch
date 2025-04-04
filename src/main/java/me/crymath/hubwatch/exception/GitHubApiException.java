package me.crymath.hubwatch.exception;

import lombok.Getter;

/** Represents an error response from the GitHub API. */
@Getter
public class GitHubApiException extends RuntimeException {

    private final int statusCode;

    public GitHubApiException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public GitHubApiException(String message, int statusCode, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }
}
