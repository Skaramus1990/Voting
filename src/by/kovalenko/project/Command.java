
package by.kovalenko.project;

public enum Command {
    REGISTER, LOGIN,ADD_CANDIDATE, CANDIDATE_LIST, EXIT, LOGOUT, VOISED, ADD_VOTHING, VOTHING_LIST, 
    DELETE_USER, DELETE_CANDIDATE, DELETE_VOTHING, GIVE_PRIVILEGES, HELP;
    
    public static Command getValue(String command){
        try{
            return valueOf(command.toUpperCase());
        }catch(Exception e){
        }
        return HELP;
    }
}
