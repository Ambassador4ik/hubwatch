package me.crymath.hubwatch.config;

import lombok.Getter;
import lombok.Setter;

/** Configuration properties for GitHub API. */
@Getter
@Setter
public class GitHubProperties {
    private String apiBaseUrl = "https://api.github.com";
    private String apiVersion = "2022-11-28";
    private String personalAccessToken;
}
