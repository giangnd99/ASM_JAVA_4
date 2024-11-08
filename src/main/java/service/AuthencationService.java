package service;

import entity.User;

import java.io.IOException;

public interface AuthencationService {

    public void doLogin() throws IOException;

    public void logout() throws IOException;

    public void createTokenForUser(User user);

    public void showLogin();
}
