
package by.kovalenko.project;

import static by.kovalenko.project.Command.*;
import by.kovalenko.project.model.Candidate;
import by.kovalenko.project.model.User;
import by.kovalenko.project.model.UserType;
import by.kovalenko.project.model.Vothing;
import by.kovalenko.project.service.AuthorizationService;
import by.kovalenko.project.service.VothingService;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class VoithingSystem {
    
    private static Set<Command> adminCommands;
    private static Set<Command> userCommands; 
    private static Set<Command> defaultUser; 
    
    private AuthorizationService authorizationService;
    private VothingService voithingService;
    private User currentUser;
    private Scanner scanner = new Scanner(System.in);
    
    
    public VoithingSystem(AuthorizationService authorizationService, VothingService voithingService) {
        this.authorizationService = authorizationService;
        this.voithingService = voithingService;
        
        defaultUser = new HashSet<>();
        adminCommands = new HashSet<>();
        userCommands = new HashSet<>();
        
        defaultUser.add(HELP);
        defaultUser.add(EXIT);
        defaultUser.add(REGISTER);
        defaultUser.add(LOGIN);
        
        adminCommands.add(GIVE_PRIVILEGES);
        adminCommands.add(REGISTER);
        adminCommands.add(LOGIN);
        adminCommands.add(ADD_CANDIDATE);
        adminCommands.add(CANDIDATE_LIST); 
        adminCommands.add(EXIT); 
        adminCommands.add(LOGOUT);
        adminCommands.add(VOISED); 
        adminCommands.add(ADD_VOTHING); 
        adminCommands.add(VOTHING_LIST);                                                                                                                                                     
        adminCommands.add(DELETE_USER);
        adminCommands.add(DELETE_CANDIDATE);
        adminCommands.add(HELP);
        
        userCommands.add(REGISTER);
        userCommands.add(LOGIN);
        userCommands.add(CANDIDATE_LIST); 
        userCommands.add(EXIT); 
        userCommands.add(LOGOUT);
        userCommands.add(VOISED);
        userCommands.add(VOTHING_LIST);
        userCommands.add(HELP);
    }

    public User getCurrentUser() {
        return currentUser;
    }
    
    public boolean checkCommand(Command command){
        if(command == null){
            return false;
        }
        if(command == LOGIN || command == REGISTER || command == EXIT){
            return true;
        }
        
        if( currentUser == null){
            return defaultUser.contains(command);
        }
        if(currentUser.getUserType() == UserType.ADMIN){
            return adminCommands.contains(command);
        }else{
            return userCommands.contains(command);
        }        
    }
    
    public void showHelp(){
        if(currentUser == null){
            showCommands(defaultUser);
            return;
        }
        switch(currentUser.getUserType()){
            case ADMIN:
                showCommands(adminCommands);
                break;
            case ELECTOR:
                showCommands(userCommands);
                break;
            default:
                break;
        }
    }
    
    private void showCommands(Set<Command> commandSet){
        System.out.println("Доступные команды: ");
        for(Command c : commandSet){
            System.out.print(c.name() + " , ");
        }
        System.out.println( );
    }
    
    public void register() {
        String name = null;
        String login = null;
        String password = null;
            System.out.println("Введите имя пользователя:");
            name = scanner.nextLine();
            System.out.println("Введите логин пользователя:");
            login = scanner.nextLine();
            System.out.println("Введите пароль пользователя:");
            password = scanner.nextLine();
        if (authorizationService.register(name,login,password) == false){
            System.out.println("Ошибка регистрации");
        }
        checkAuthorizationUser();
        System.out.println("Пользователь зарегистрирован. Войдите в систему");
    }

    public void login() {
        String login = null;
        String password = null;
        UserType usertype = null;
            System.out.println("Введите логин:");
            login = scanner.nextLine();;
            System.out.println("Введите пароль:");
            password = scanner.nextLine();;
        currentUser = authorizationService.login(login,password,usertype);
        checkAuthorizationUser();
    }
    
    private void checkAuthorizationUser(){
        if (currentUser == null){
            System.out.println("Вход не выполнен!");
            return;
        }
        System.out.println("Вход выполнен!");
    }
    
    public void addCandidate(){
        String candidateName = null;
        long voiceId = 0;
        System.out.println("Введите имя кандидата:");
        candidateName = scanner.nextLine();;
        System.out.println("Введите номер голосования:");
        voiceId = Long.valueOf(scanner.nextLine());
        if (voithingService.registerCandidate(currentUser, voiceId, candidateName) == false){
            System.out.println("Ошибка добавления кандидата.");
            return;
        }
        System.out.println("кандидат добавле.");
    }
    
    public void showCandidateList(){
        long voiceId = 0;
        System.out.print("Введите номер голосования:");
        voiceId = Long.valueOf(scanner.nextLine());
        System.out.println("Ошибка ввода данных");
        System.out.println("Список кандидатов: ");
        ArrayList<Candidate>  arrayCandidate = voithingService.readAllCandidate(voiceId);
        for (Candidate candidate : arrayCandidate) {
                System.out.println("Номер кандидата: " + candidate.getVothingiD() + ", Имя кандидата: " 
                    + candidate.getName() + ", участвует в голосовании: " + candidate.getNameVoiting());            
        }
    }
    
    public void logout(){
        currentUser = null;
        System.out.println("Сессия завершена!");
    }
    
    public void voithing(){
        long candidateId = 0;
        long voiceId = 0;
        System.out.println("Введите номер голосования:");
        voiceId = Long.valueOf(scanner.nextLine());
        System.out.println("Введите номер кандидата");
        candidateId = Long.valueOf(scanner.nextLine());
        voithingService.addVoiced(currentUser, voiceId, candidateId);
    }
    
    public void createVoithing(){
        String voiceTitle = null;
        Date start = null;
        Date of = null;
        System.out.println("Введите дату начала голосования в формате год-месяц-число:");
        start = Date.valueOf(scanner.nextLine());
        System.out.println("Введите дату окончания голосования в формате год-месяц-число:");
        of = Date.valueOf(scanner.nextLine());
        System.out.println("Введите название голосования:");
        voiceTitle = scanner.nextLine();
        if (voithingService.createVoithing(currentUser, voiceTitle, start, of) == false){
            System.out.println("Ошибка добавления голосования.");
            return;
        }
        System.out.println("Голосование создано!");
    }
    
    public void showVoithingList(){
        ArrayList<Vothing> arrayVoting= voithingService.redAll();
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        for (Vothing vothing : arrayVoting) {
            System.out.println("Номер голосования: " + vothing.getId() + " Название: "
                        + "" + vothing.getTitle() + " Начало "
                    + " выборов: " + vothing.getDateStart() + " Завершение: " + vothing.getDateOf() + "\n");
        if (vothing.getDateOf().getTime() < currentDate.getTime()){
            System.out.println("Победитель голосования №" + vothing.getId() + ":"
                    + " " + vothing.getNameVinner() + " с колличеством голосов " + vothing.getVoicVinner() + " \n");
        } else if(vothing.getDateStart().getTime() > currentDate.getTime()){
            System.out.println("Голосование не начато. \n");
        }else{
            System.out.println("Лидер голосования №" + vothing.getId()+ ": "
                    + "" + vothing.getNameVinner() + " с колличеством голосов " + vothing.getVoicVinner() + "\n");
        }     
        }
    }
    
    public void deleteUser(){
        String name = null;
        System.out.println("Введите имя пользовател:");
        name = scanner.nextLine();
        System.out.println("Ошибка ввода данных");
        voithingService.deleteuser(currentUser, name);
    }
    
    public void deleteCandidate(){
        long candidateId =0;
        System.out.println("Введите ID кандидата:");
        candidateId = Long.valueOf(scanner.nextLine());
        System.out.println("Ошибка ввода данных");
        voithingService.deleteCandidate(currentUser, candidateId);
    }
    
    public void deleteVoiting(){
        long voitingId = 0;
        System.out.println("Введите ID голосования:");
        voitingId = Long.valueOf(scanner.nextLine());
        System.out.println("Ошибка ввода данных");
        voithingService.deleteVoiting(currentUser, voitingId);
    }
    
    public void givePrivilegi(){
        String name = null;
        System.out.println("Введите имя пользователя:");
        name = scanner.nextLine();
        System.out.println("Ошибка ввода данных");
        voithingService.givePrivilegi(currentUser, name);
    }
}