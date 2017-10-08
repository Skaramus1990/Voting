
package by.kovalenko.project.service;

import by.kovalenko.project.model.User;
import by.kovalenko.project.model.UserType;


public interface AuthorizationService {
    boolean register(String name, String login, String password);
    User login(String login, String password, UserType usertype);
    boolean logout(User user);
}
