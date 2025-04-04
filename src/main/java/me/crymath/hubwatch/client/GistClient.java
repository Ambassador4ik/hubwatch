package me.crymath.hubwatch.client;

import java.util.List;
import me.crymath.hubwatch.model.Comment;
import me.crymath.hubwatch.model.Gist;

public interface GistClient {
    List<Gist> listUserGists(String username);

    List<Gist> listAuthenticatedUserGists();

    Gist getGist(String gistId);

    List<Comment> listGistComments(String gistId);
}
