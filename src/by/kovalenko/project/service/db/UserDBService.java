
package by.kovalenko.project.service.db;

import by.kovalenko.project.model.User;
import by.kovalenko.project.model.UserType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDBService extends DBConnection {
    
    
    public boolean addUser(String name, String login, String password){
        PreparedStatement preparedStatement = null;
        String insertSQL = "INSERT INTO voting.user(name, login, password, user_type) VALUES (?,?,?,?)";
        try {
            preparedStatement = getDBconnection().prepareStatement(insertSQL);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, login);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, "ELECTOR");
                preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException ex) {
            System.out.println("Ошибка " + ex);
            return false;
        }
        return true;
    }
    
    public User login(String login, String password, UserType usertype) throws SQLException,NullPointerException{
            PreparedStatement preparedStatement = null;
            String selectSQL = "SELECT name,login, password,user_type  FROM voting.user WHERE login = ?";
            String loginLocal = null;
            String passwordLocal = null;
            String userTypeLocal = null;
            String nameLocal = null;
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                nameLocal = rs.getString("name");
                userTypeLocal = rs.getString("user_type");
		loginLocal = rs.getString("login");
		passwordLocal = rs.getString("password");
            } 
            preparedStatement.close();
            try{
            if (loginLocal.equals(login) && passwordLocal.equals(password)){
                if (userTypeLocal.equals("ELECTOR")){
                    User user = new User();
                    user.setName(nameLocal);
                    user.setLogin(loginLocal);
                    user.setUserType(UserType.ELECTOR);
                    return user;
                }
                if (userTypeLocal.equals("ADMIN")){
                    User user = new User();
                    user.setName(nameLocal);
                    user.setLogin(loginLocal);
                    user.setUserType(UserType.ADMIN);
                    return user;
                } 
            }
            } catch (NullPointerException e){
                System.out.println("ошибка!" + e);
            }
        return null;
    }
}
