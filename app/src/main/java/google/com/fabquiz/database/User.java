package google.com.fabquiz.database;

/**
 * Created by user on 16-11-2017.
 */

public class User {


    String phonenumber;
    String username;
    String profile;

    public User() {
    }

    public User(String phonenumber, String username, String profile ) {
        this.phonenumber =  phonenumber;
        this.username = username;
        this.profile = profile;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String Phonenumber) {
        this.phonenumber = Phonenumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String Profile) {
        this.profile = Profile;
    }
}
