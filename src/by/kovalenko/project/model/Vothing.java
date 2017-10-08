
package by.kovalenko.project.model;

import java.sql.Date;
import java.util.List;


public class Vothing {
    
    private long id;
    private String title;
    private List<Candidate> candidats;
    private Date dateStart;
    private Date dateOf;
    private String nameVinner;
    private int voicVinner;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameVinner() {
        return nameVinner;
    }

    public void setNameVinner(String nameVinner) {
        this.nameVinner = nameVinner;
    }

    public int getVoicVinner() {
        return voicVinner;
    }

    public void setVoicVinner(int voicVinner) {
        this.voicVinner = voicVinner;
    }
    
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateOf() {
        return dateOf;
    }

    public void setDateOf(Date dateOf) {
        this.dateOf = dateOf;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Candidate> getCandidats() {
        return candidats;
    }

    public void setCandidats(List<Candidate> candidats) {
        this.candidats = candidats;
    }

    @Override
    public String toString() {
        String candidatsString = "[]";
        if (candidats != null){
            for (Candidate candidat : candidats) {
                candidatsString+= " " + candidat;
            }
        }
        return "Vothing{" + "id=" + id + ", title=" + title + ", candidats=" + candidatsString + '}';
    }
    
}
