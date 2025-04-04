package me.crymath.hubwatch.client;

import me.crymath.hubwatch.model.User;

public interface UserClient {
    User getUser(String username);

    User getAuthenticatedUser();

    User getOrganization(String organization);
}
