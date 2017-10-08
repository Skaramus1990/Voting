
package by.kovalenko.project.service.impl;

import by.kovalenko.project.model.User;
import by.kovalenko.project.model.UserType;
import by.kovalenko.project.service.AuthorizationService;
import by.kovalenko.project.service.db.UserDBService;
import java.sql.SQLException;



public class AuthorizationServiceImpl implements AuthorizationService{
    
    private UserDBService userDBService;

    public AuthorizationServiceImpl() {
        userDBService = new UserDBService();
    }

    @Override
    public boolean register(String name, String login, String password) {
        if(name.isEmpty() || name== null || login.isEmpty() || login == null || password.isEmpty() || password == null){
            System.out.println("Неверно введены данные!");
            return false;
        }
        return userDBService.addUser(name,login,password);
    }
    
    @Override
    public User login(String login, String password, UserType usertype) {
        if (login.isEmpty() || login == null || password.isEmpty() || password == null){
            System.out.println("Неверно введены данные!");
            return null;
        }
        try {
            return userDBService.login(login, password, usertype);
        } catch (SQLException ex) {
            System.out.println("Ошибка " + ex);
        }
        return null;
    }

    @Override
    public boolean logout(User user) {
        return true;
    }
    
}
