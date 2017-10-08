
package by.kovalenko.project.service.db;

import by.kovalenko.project.model.Candidate;
import by.kovalenko.project.model.User;
import by.kovalenko.project.model.Vothing;
import static by.kovalenko.project.service.db.DBConnection.getDBconnection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class VoithingDBService extends DBConnection{
    private ArrayList<Candidate> arrayCandidats;
    private ArrayList<Vothing> arrayVoting;
    
    public boolean createVoithing(String title, Date start, Date of){
        PreparedStatement preparedStatement = null;
        String insertSQL = "INSERT INTO voting.voting( title, date_start, date_of) VALUES (?,?,?)";
        try {
            preparedStatement = getDBconnection().prepareStatement(insertSQL);
            preparedStatement.setString(1, title);
            preparedStatement.setDate(2, start);
            preparedStatement.setDate(3, of);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException ex) {
            System.out.println("Ошибка " + ex);
            return false;
        }
        return true;
    }

    public boolean registerCandidate(long voithingId, String name){
        PreparedStatement preparedStatement = null;
        String insertSQL = "INSERT INTO voting.candidate( name, voitingID, voic) VALUES (?,?,?)";
        try {
            preparedStatement = getDBconnection().prepareStatement(insertSQL);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, voithingId);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException ex) {
            System.out.println("Ошибка " + ex);
            return false;
        }
        return true;
    }
    
    public ArrayList<Candidate> readAllCandidate(long voithingId){
        int localcount = 0;
        arrayCandidats = new ArrayList();
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT v.id, v.name, vo.title FROM voting.candidate v, voting.voting vo WHERE v.voitingID=vo.id AND vo.id = ?";
        try {
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setLong(1, voithingId);
            ResultSet rs2 = preparedStatement.executeQuery();
        while ( rs2.next() ){
            arrayCandidats.add(localcount, new Candidate());
            arrayCandidats.get(localcount).setName(rs2.getString("name"));
            arrayCandidats.get(localcount).setNameVoiting(rs2.getString("title"));
            arrayCandidats.get(localcount).setVothingiD(rs2.getInt(1));
        }
        preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка" + ex);
        }
        return arrayCandidats;
    }
    
    public ArrayList<Vothing> readAll(){
        arrayVoting = new ArrayList();
        int localcount = 0;
        Statement statement = null;
        String selectSQL = "SELECT v.id, v.title, v.date_start, v.date_of, c.name, "
                + "MAX(c.voic) FROM voting.voting v, voting.candidate c  WHERE c.voitingID=v.id GROUP BY v.id";
        try {
            statement = getDBconnection().createStatement();
            statement.execute(selectSQL);
            ResultSet rs2 = statement.getResultSet();
        while ( rs2.next() ){
            arrayVoting.add(localcount, new Vothing());
            arrayVoting.get(localcount).setTitle(rs2.getString("title"));
            arrayVoting.get(localcount).setId(rs2.getInt("id"));
            arrayVoting.get(localcount).setDateStart(rs2.getDate("date_start"));
            arrayVoting.get(localcount).setDateOf(rs2.getDate("date_of"));
            arrayVoting.get(localcount).setNameVinner(rs2.getString("name"));
            arrayVoting.get(localcount).setVoicVinner(rs2.getInt(6));
        }
        } catch (SQLException ex) {
          System.out.println("Ошибка " + ex);
        }
        return arrayVoting;
    }

    public boolean addVoiced(User user, long voithingId, long candidateId){        
        ArrayList<Long> l = checkVoic(user, voithingId);
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        Date localDateOf = checkDateEnd(voithingId);
        Date localDateStart = checkDateStart(voithingId);
        if (localDateOf.getTime() < currentDate.getTime()){
            System.out.println("Голосование закончилось!");
            return false;
        }
        if (localDateStart.getTime() > currentDate.getTime()){
            System.out.println("Голосование не начато!");
            return false;
        }
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i) == voithingId){
                System.out.println("Вы уже проголосовали");
                return false;
            }
        }
        long candidatevoic = selectCandidateVoic(candidateId);
            PreparedStatement preparedStatement = null;
            String insertSQL = "UPDATE voting.candidate SET voic=?";
        try {
            preparedStatement = getDBconnection().prepareStatement(insertSQL);
            preparedStatement.setLong(1,candidatevoic + 1);
            preparedStatement.executeUpdate();
            System.out.println("Ваш голос добавлен!");
            preparedStatement.close();
        }catch (SQLException ex) {
            System.out.println(ex.fillInStackTrace());
        }finally {
            inserVoting(user,voithingId);
        }
        return true;
    } 
    
    private Date checkDateEnd(long vothingId){
        Date localDate = null;
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT date_of FROM voting.voting WHERE id = ?";
        try{
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setLong(1, vothingId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                localDate = rs.getDate("date_of");
            }
            preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка" + ex);
        }    
        return localDate;
    }
    
    private Date checkDateStart(long vothingId){
        Date localDate = null;
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT date_start FROM voting.voting WHERE id = ?";
        try{
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setLong(1, vothingId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                localDate = rs.getDate("date_start");
            }
            preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка" + ex);
        }    
        return localDate;
    }
    
    private void inserVoting(User user,long voithingId ){
        PreparedStatement preparedStatement = null;
        String insertSQL = "INSERT INTO voting.voices(namevoices, voithingIDvoices) VALUES (?,?)";
        try {
            preparedStatement = getDBconnection().prepareStatement(insertSQL);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setLong(2, voithingId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException ex) {
            System.out.println("Ошибка " + ex);
        }
    }
    
    private long selectCandidateVoic(long candidateID){
        long voic = 0;
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT voic FROM voting.candidate WHERE id = ?";
        try{
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setLong(1, candidateID);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                voic = rs.getInt("voic");
            }
            preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка" + ex);
        }
        return voic;
    }
    
    private ArrayList<Long> checkVoic(User user, long voithingId){
        PreparedStatement preparedStatement = null;
        ArrayList<Long> l = new ArrayList();
        String selectSQL = "SELECT voithingIDvoices, namevoices FROM voting.voices "
                + "WHERE namevoices = ? AND voithingIDvoices = ?";
        try{
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setLong(2, voithingId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                l.add(rs.getLong(1));
            }
            preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка" + ex);
        }
        return l;
    }
    
    public void deleteuser(String name) {
        PreparedStatement preparedStatement = null;
        String selectSQL = "DELETE FROM voting.user where name = ?";
        try{
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка" + ex);
        }
        System.out.println("Пользователь: " + name + " удалён");
    }

    public void deleteCandidate(long candidateID) {
        PreparedStatement preparedStatement = null;
        String selectSQL = "DELETE FROM voting.candidate WHERE id = ?";
        try{
            preparedStatement = getDBconnection().prepareStatement(selectSQL);
            preparedStatement.setLong(1, candidateID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка " + ex);
        }
        System.out.println("Кандидат №: " + candidateID + " удалён");
    }

    public void deleteVoiting(long voitingId) {
        PreparedStatement preparedStatement = null;
        String deleteSQL = "DELETE FROM voting.voting WHERE id = ?";
        try{
            preparedStatement = getDBconnection().prepareStatement(deleteSQL);
            preparedStatement.setLong(1, voitingId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
          System.out.println("Ошибка" + ex);
        }
        System.out.println("Голосование с №: " + voitingId + " удалено");
    }

    public void givePrivilegi(String name) {
        PreparedStatement preparedStatement = null;
        String insertSQL = "UPDATE voting.user SET user_type=? WHERE name=?";
        try {
            preparedStatement = getDBconnection().prepareStatement(insertSQL);
            preparedStatement.setString(1,"ADMIN");
            preparedStatement.setString(2,name);
            preparedStatement.executeUpdate();
            if (preparedStatement.executeUpdate() != 0){
                System.out.println("пользователь " + name + " повышен!");
            }else{
                System.out.println("Пользователя с таким именем не найдено!");
            }
            preparedStatement.close();
        }catch (SQLException ex) {
            System.out.println(ex.fillInStackTrace());
        }
    }
}
