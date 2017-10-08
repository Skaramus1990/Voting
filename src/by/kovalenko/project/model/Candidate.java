
package by.kovalenko.project.model;


public class Candidate {
    
    private long id;
    private String name;
    private int voiced;
    private int vothingiD;
    private String nameVoiting;
    
    public Candidate(String name) {
        this.name = name;
        this.voiced = 0;
    }
    
    public Candidate(){
        
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVothingiD() {
        return vothingiD;
    }

    public void setVothingiD(int vothingiD) {
        this.vothingiD = vothingiD;
    }

    public String getNameVoiting() {
        return nameVoiting;
    }

    public void setNameVoiting(String nameVoiting) {
        this.nameVoiting = nameVoiting;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVoiced() {
        return voiced;
    }

    public void setVoiced(int voiced) {
        this.voiced = voiced;
    }

    @Override
    public String toString() {
        return "Candidate{" + "id=" + id + ", name=" + name + ", voiced=" + voiced + '}';
    }
    
}
