
package by.kovalenko.project.service.impl;
import by.kovalenko.project.model.Candidate;
import by.kovalenko.project.model.User;
import by.kovalenko.project.model.UserType;
import by.kovalenko.project.model.Vothing;
import by.kovalenko.project.service.VothingService;
import by.kovalenko.project.service.db.VoithingDBService;
import java.sql.Date;
import java.util.ArrayList;

public class VoithingServiceImpl implements VothingService {
    
    private VoithingDBService voithingDBService;
    
    public VoithingServiceImpl() {
        voithingDBService = new VoithingDBService();
    }
    

    @Override
    public boolean registerCandidate(User currentUser, long voithingId, String name) {
        if (currentUser == null || name == null || name.isEmpty()){
            return false;
        }
        if (currentUser.getUserType() != UserType.ADMIN){
            System.out.println("Вы не являетесь администратором!");
            return false;
        }
        return voithingDBService.registerCandidate(voithingId, name);
    }

    @Override
    public ArrayList<Candidate> readAllCandidate(long voithingId) {
        return voithingDBService.readAllCandidate(voithingId);
    }

    @Override
    public boolean addVoiced(User user, long voithingId, long candiateId) {
       return voithingDBService.addVoiced(user, voithingId, candiateId);
    }

    @Override
    public boolean createVoithing(User currentUser, String title, Date start, Date of) {
        if (currentUser == null || title == null || title.isEmpty()){
            System.out.println("Не введены данные!");
            return false;
        }
        if (currentUser.getUserType() != UserType.ADMIN){
            System.out.println("Вы не являетесь администратором!");
            return false;
        }
        return voithingDBService.createVoithing(title,start,of);
    }

    @Override
    public ArrayList<Vothing> redAll() {
        return voithingDBService.readAll();
    }

    @Override
    public void deleteuser(User currentUser, String name) {
        if (currentUser == null || name == null || name.isEmpty()){
            return;
        }
        if (currentUser.getUserType() != UserType.ADMIN){
            System.out.println("Вы не являетесь администратором!");
            return;
        }
        voithingDBService.deleteuser(name);
    }

    @Override
    public void deleteCandidate(User currentUser, long candidateID) {
        if (currentUser == null || candidateID == 0){
            return;
        }
        if (currentUser.getUserType() != UserType.ADMIN){
            System.out.println("Вы не являетесь администратором!");
            return;
        }
        voithingDBService.deleteCandidate(candidateID);
    }

    @Override
    public void deleteVoiting(User currentUser, long voitingId) {
        if (currentUser == null || voitingId == 0){
            return;
        }
        if (currentUser.getUserType() != UserType.ADMIN){
            System.out.println("Вы не являетесь администратором!");
            return;
        }
        voithingDBService.deleteVoiting(voitingId);
    }

    @Override
    public void givePrivilegi(User currentUser, String name) {
        if (currentUser == null || name == null || name.isEmpty()){
            return;
        }
        if (currentUser.getUserType() != UserType.ADMIN){
            System.out.println("Вы не являетесь администратором!");
            return;
        }
        voithingDBService.givePrivilegi(name);
    }
}
