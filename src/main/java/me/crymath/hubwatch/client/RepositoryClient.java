package me.crymath.hubwatch.client;

import java.util.List;
import me.crymath.hubwatch.model.*;

public interface RepositoryClient {
    List<Repository> listUserRepositories(String username);

    List<Repository> listAuthenticatedUserRepositories();

    List<Repository> listOrgRepositories(String organization);

    Repository getRepository(String owner, String repo);

    List<Branch> listBranches(String owner, String repo);

    Branch getBranch(String owner, String repo, String branch);

    List<Tag> listTags(String owner, String repo);

    List<Release> listReleases(String owner, String repo);

    Release getLatestRelease(String owner, String repo);

    List<Commit> listCommits(String owner, String repo, String shaOrBranch);

    Commit getCommit(String owner, String repo, String sha);

    List<Comment> listCommitComments(String owner, String repo, String commitSha);
}
