
package by.kovalenko.project.model;

public class Elector extends User {
    
    private boolean voted;

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }
    
}
