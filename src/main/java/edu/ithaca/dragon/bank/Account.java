public class Account{

    private String username;
    private String email;
    private String password;
    ArrayList<TravelLog> tLogs;
    ArrayList<Location> fav;

    public Account(String username, String email, String password){
        this.username=username;
        this.email = email;
        this.password = password;
    }

    TravelLog getLogWith(String name){
        return null;
    }

    TravelLog removeLogAt(int i){
        return null;
    }

    Location getFavAt(int i){
        return null;
    }

    Location removeFavAt(int i){
        return null;
    }

    Boolean verifyAccount(String usernameIn, String passIn){
        return false;
    }

    void resetPassword(String p1In, String p2In, String passIn){

    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}