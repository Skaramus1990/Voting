
package by.kovalenko.project.service;

import by.kovalenko.project.model.Candidate;
import by.kovalenko.project.model.User;
import by.kovalenko.project.model.Vothing;
import java.sql.Date;
import java.util.ArrayList;


public interface VothingService {
    boolean createVoithing(User currentUser, String title, Date start, Date of);
    boolean registerCandidate(User currentUser,long voithingId, String name);
    ArrayList<Candidate> readAllCandidate(long voithingId);
    ArrayList<Vothing> redAll();
    boolean addVoiced(User user, long voithingId, long candiateId);
    void deleteuser(User currentUser,String name);
    void deleteCandidate(User currentUser, long candidateID);
    void deleteVoiting(User currentUser, long voitingId);
    void givePrivilegi(User currentUser, String name);
}
