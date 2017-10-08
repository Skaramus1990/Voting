
package by.kovalenko.project;
import by.kovalenko.project.service.impl.AuthorizationServiceImpl;
import by.kovalenko.project.service.impl.VoithingServiceImpl;

import java.util.Scanner;

public class Project {
    
    public static void main(String[] args) {
    VoithingSystem voithingSystem = new VoithingSystem(new AuthorizationServiceImpl(), 
            new VoithingServiceImpl());
    
    while(true){
            System.out.println("Введите команду:");
            Command command = returnCommand();
            if(!voithingSystem.checkCommand(command)){
                System.out.println("Введена неверная команда!");
                continue;
            }
            
            switch(command){
                case REGISTER:
                    voithingSystem.register();
                    break;
                case LOGIN:
                    voithingSystem.login();
                    break;
                case ADD_CANDIDATE:
                    voithingSystem.addCandidate();
                    break;
                case CANDIDATE_LIST:
                    voithingSystem.showCandidateList();
                    break;
                case EXIT:
                    System.out.println("Выход");
                    return;
                case LOGOUT:
                    voithingSystem.logout();
                    break;
                case VOISED:
                    voithingSystem.voithing();
                    break;
                case ADD_VOTHING:
                    voithingSystem.createVoithing();
                    break;
                case VOTHING_LIST:
                    voithingSystem.showVoithingList();
                    break;
                case DELETE_USER:
                    voithingSystem.deleteUser();
                    break;
                case DELETE_CANDIDATE:
                    voithingSystem.deleteCandidate();
                    break;
                case DELETE_VOTHING:
                    voithingSystem.deleteVoiting();
                    break;
                case GIVE_PRIVILEGES:
                    voithingSystem.givePrivilegi();
                    break;
                case HELP:
                    voithingSystem.showHelp();
                    break;
            }
       }    
    }
    private static Command returnCommand() throws IllegalArgumentException{
        Scanner scanner = new Scanner(System.in, "utf-8");
        Command command = null;
        command = Command.getValue(scanner.nextLine());
        return command;
    }
}
