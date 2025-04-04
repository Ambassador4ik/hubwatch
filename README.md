# Hubwatch Library Documentation

This documentation provides an extensive overview of the Hubwatch library—a Java-based library designed for interacting with the GitHub API using Spring. It simplifies accessing various GitHub resources such as repositories, issues, gists, notifications, and users, while handling authentication and error processing.

---

## Table of Contents

1. [Overview](#overview)
2. [Configuration](#configuration)
3. [API Clients](#api-clients)
   - [AbstractGitHubClient](#abstractgithubclient)
   - [GistClient](#gistclient)
   - [IssueClient](#issueclient)
   - [NotificationClient](#notificationclient)
   - [RepositoryClient](#repositoryclient)
   - [UserClient](#userclient)
4. [Exceptions](#exceptions)
5. [Model Objects](#model-objects)
6. [Service Layer](#service-layer)
7. [Error Handling](#error-handling)
8. [Usage Examples](#usage-examples)
9. [Extending and Contributing](#extending-and-contributing)

---

## Overview

Hubwatch is a lightweight Java library that facilitates communication with the GitHub API. It provides:
- **Pre-configured API Clients**: Separate clients for repositories, issues, gists, notifications, and users.
- **Error Handling**: A centralized mechanism to transform HTTP errors into meaningful exceptions.
- **Authentication Support**: Built-in handling for personal access tokens with automatic header management.
- **Model Representations**: Data models for GitHub entities like repositories, commits, issues, etc.

The library is built on top of Spring's `RestTemplate` for HTTP communication and uses Lombok for reducing boilerplate code.

---

## Configuration

Hubwatch leverages Spring's configuration mechanism for setting GitHub-specific properties. The following components are key:

### GitHubProperties

- **Location**: `me.crymath.hubwatch.config.GitHubProperties`
- **Purpose**: Holds configuration values such as the API base URL, API version, and personal access token.
- **Example Properties**:
  - `apiBaseUrl`: Defaults to `https://api.github.com`.
  - `apiVersion`: Specifies the GitHub API version (e.g., `2022-11-28`).
  - `personalAccessToken`: Your GitHub token for authenticated requests.

### GitHubClientConfig

- **Location**: `me.crymath.hubwatch.config.GitHubClientConfig`
- **Purpose**: Configures the `RestTemplate` instance used by the API clients.
- **Features**:
  - Sets the base URL for all requests.
  - Registers interceptors for authentication (using `GitHubAuthInterceptor`) and adds default headers (e.g., `Accept`, `X-GitHub-Api-Version`).

### GitHubAuthInterceptor

- **Location**: `me.crymath.hubwatch.config.GitHubAuthInterceptor`
- **Purpose**: Intercepts outgoing HTTP requests to inject the `Authorization` header with a bearer token.
- **Usage**: Automatically added if a personal access token is provided.

---

## API Clients

Hubwatch is organized around several API clients that encapsulate specific GitHub functionalities. Each client extends `AbstractGitHubClient` which centralizes error handling.

### AbstractGitHubClient

- **Location**: `me.crymath.hubwatch.client.AbstractGitHubClient`
- **Purpose**: Provides a common error handling mechanism by converting HTTP errors into structured exceptions.
- **Key Method**:
  - `execute(Supplier<T> supplier)`: Wraps API calls to handle exceptions.
  - `convertException(HttpClientErrorException e)`: Translates HTTP errors to `GitHubApiException` or `GitHubRateLimitExceededException`.

### GistClient

- **Location**: `me.crymath.hubwatch.client.GistClient` and its implementation in `GistClientImpl`
- **Key Methods**:
  - `listUserGists(String username)`: List all gists for a specified user.
  - `listAuthenticatedUserGists()`: List gists for the authenticated user.
  - `getGist(String gistId)`: Retrieve a specific gist.
  - `listGistComments(String gistId)`: List comments on a gist.

### IssueClient

- **Location**: `me.crymath.hubwatch.client.IssueClient` and its implementation in `IssueClientImpl`
- **Key Methods**:
  - `listIssues(String owner, String repo)`: List issues for a given repository.
  - `getIssue(String owner, String repo, int issueNumber)`: Retrieve a specific issue.
  - `listIssueComments(String owner, String repo, int issueNumber)`: List comments on an issue.

### NotificationClient

- **Location**: `me.crymath.hubwatch.client.NotificationClient` and its implementation in `NotificationClientImpl`
- **Key Methods**:
  - `listNotifications(boolean all, boolean participating)`: Retrieve notifications, with filters for all notifications and those the user is participating in.

### RepositoryClient

- **Location**: `me.crymath.hubwatch.client.RepositoryClient` and its implementation in `RepositoryClientImpl`
- **Key Methods**:
  - `listUserRepositories(String username)`: List public repositories of a user.
  - `listAuthenticatedUserRepositories()`: List repositories for the authenticated user.
  - `listOrgRepositories(String organization)`: List repositories for an organization.
  - `getRepository(String owner, String repo)`: Retrieve repository details.
  - `listBranches(String owner, String repo)`: List branches in a repository.
  - `getBranch(String owner, String repo, String branch)`: Retrieve a specific branch.
  - `listTags(String owner, String repo)`: List tags in a repository.
  - `listReleases(String owner, String repo)`: List releases.
  - `getLatestRelease(String owner, String repo)`: Retrieve the latest release.
  - `listCommits(String owner, String repo, String shaOrBranch)`: List commits, optionally filtered by branch or SHA.
  - `getCommit(String owner, String repo, String sha)`: Retrieve a specific commit.
  - `listCommitComments(String owner, String repo, String commitSha)`: List comments on a commit.

### UserClient

- **Location**: `me.crymath.hubwatch.client.UserClient` and its implementation in `UserClientImpl`
- **Key Methods**:
  - `getUser(String username)`: Retrieve details of a specific user.
  - `getAuthenticatedUser()`: Retrieve details of the authenticated user.
  - `getOrganization(String organization)`: Retrieve details about an organization.

---

## Exceptions

The library defines custom exceptions to handle errors from GitHub API responses.

### GitHubApiException

- **Location**: `me.crymath.hubwatch.exception.GitHubApiException`
- **Purpose**: Represents general errors received from the GitHub API.
- **Usage**: Thrown when the API returns error statuses like 404 or 401/403 (with reasons other than rate limiting).

### GitHubRateLimitExceededException

- **Location**: `me.crymath.hubwatch.exception.GitHubRateLimitExceededException`
- **Purpose**: A specialized exception that indicates the GitHub API rate limit has been exceeded.
- **Additional Information**: Provides the epoch time when the rate limit resets.

---

## Model Objects

Hubwatch includes a set of model classes that map directly to GitHub's API JSON responses.

- **Branch**
  - **Location**: `me.crymath.hubwatch.model.Branch`
  - **Fields**: `name`, `commit` (which is a `Commit` object).
- **Comment**
  - **Location**: `me.crymath.hubwatch.model.Comment`
  - **Fields**: `id`, `body`, `user` (a `User` object), and timestamps (`createdAt`, `updatedAt`).
- **Commit**
  - **Location**: `me.crymath.hubwatch.model.Commit`
  - **Fields**: `sha`, `commit` (detailed information including message, author, and committer).
  - **Nested Class**: `Commit.CommitDetail` with nested `Author` class containing `name`, `email`, and `date`.
- **Gist**
  - **Location**: `me.crymath.hubwatch.model.Gist`
  - **Fields**: `id`, `description`, `isPublic`, `owner` (a `User`), `htmlUrl`, timestamps, and a map of `files`.
  - **Nested Class**: `Gist.GistFile` which includes file metadata such as filename, type, language, size, and raw URL.
- **Issue**
  - **Location**: `me.crymath.hubwatch.model.Issue`
  - **Fields**: `id`, `number`, `title`, `body`, `state`, `user`, `htmlUrl`, and timestamps.
- **Notification**
  - **Location**: `me.crymath.hubwatch.model.Notification`
  - **Fields**: `id`, `reason`, `isUnread`, timestamps, and `subject`.
  - **Nested Class**: `Notification.NotificationSubject` that holds details such as title, URL, and type.
- **Release**
  - **Location**: `me.crymath.hubwatch.model.Release`
  - **Fields**: `id`, `tagName`, `name`, `body`, status flags (`isDraft`, `isPreRelease`), and timestamps.
- **Repository**
  - **Location**: `me.crymath.hubwatch.model.Repository`
  - **Fields**: `id`, `name`, `fullName`, `description`, `htmlUrl`, privacy flag (`privateRepo`), `owner`, timestamps, and statistics like `stargazersCount` and `forksCount`.
- **Tag**
  - **Location**: `me.crymath.hubwatch.model.Tag`
  - **Fields**: `name`, and associated `commit`.
- **User**
  - **Location**: `me.crymath.hubwatch.model.User`
  - **Fields**: `id`, `login`, `avatarUrl`, `htmlUrl`, and `type`.

---

## Service Layer

### GitHubService

- **Location**: `me.crymath.hubwatch.service.GitHubService`
- **Purpose**: Acts as a facade that aggregates the various API clients into a single service.
- **Features**:
  - Provides getters for each of the clients: `RepositoryClient`, `GistClient`, `IssueClient`, `UserClient`, and `NotificationClient`.
  - Simplifies integration by exposing all GitHub API functionalities via one service instance.

---

## Error Handling

Hubwatch leverages the `execute` method in `AbstractGitHubClient` to handle errors uniformly. Key aspects include:
- **HTTP Error Conversion**: HTTP errors (e.g., 404, 401, 403) are caught and converted into:
- `GitHubApiException` for general API errors.
- `GitHubRateLimitExceededException` when the rate limit is exceeded (determined by checking the `X-RateLimit-Remaining` header).
- **Logging**: Errors are logged using Lombok's `@Slf4j` annotation.

Developers can catch these exceptions to implement custom retry logic or user notifications.

---

## Usage Examples

Below are some example snippets to illustrate common use cases.

### Initializing the GitHub Service

```java
// Example: Initializing GitHubService with a RestTemplate from the GitHubClientConfig
GitHubProperties properties = new GitHubProperties();
properties.setPersonalAccessToken("YOUR_TOKEN_HERE");
properties.setApiBaseUrl("https://api.github.com");
properties.setApiVersion("2022-11-28");

GitHubClientConfig config = new GitHubClientConfig(properties);
RestTemplate restTemplate = config.gitHubRestTemplate();

GitHubService gitHubService = new GitHubService(restTemplate);
```

### Listing User Repositories

```java
// Example: Listing repositories for a specific user
RepositoryClient repoClient = gitHubService.getRepositoryClient();
List<Repository> repositories = repoClient.listUserRepositories("username");
// Process the repositories as needed
```

### Handling API Exceptions

```java
try {
    Issue issue = gitHubService.getIssueClient().getIssue("owner", "repo", 123);
} catch (GitHubApiException e) {
    // Handle error, such as logging or notifying the user
    System.out.println("GitHub API error: " + e.getMessage());
} catch (GitHubRateLimitExceededException e) {
    // Specific handling for rate limit issues
    System.out.println("Rate limit exceeded. Try again after: " + e.getResetEpochSeconds());
}
```

---

## Extending and Contributing

### Extending the Library

- **Custom API Endpoints**: You can extend the client implementations by adding new methods that wrap additional GitHub API endpoints.
- **Override Error Handling**: By subclassing `AbstractGitHubClient`, you can modify or extend the default error handling behavior.

