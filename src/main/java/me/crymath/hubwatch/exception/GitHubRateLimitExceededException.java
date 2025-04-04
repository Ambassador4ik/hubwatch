package me.crymath.hubwatch.exception;

import lombok.Getter;

/** Exception indicating that the GitHub API rate limit has been exceeded. */
@Getter
public class GitHubRateLimitExceededException extends GitHubApiException {

    private final long resetEpochSeconds;

    public GitHubRateLimitExceededException(String message, int statusCode, long resetEpochSeconds) {
        super(message, statusCode);
        this.resetEpochSeconds = resetEpochSeconds;
    }
}
