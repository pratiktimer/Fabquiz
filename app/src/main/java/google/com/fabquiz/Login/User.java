package google.com.fabquiz.Login;

/**
 * Created by Pratik on 2/23/2018.
 */

public class User {
    String phonenumber;
    String fullname;
    String getprofile;
    String userId;

    public User() {
    }

    public User(String phonenumber, String fullname, String getprofile, String userId) {
        this.phonenumber = phonenumber;
        this.fullname = fullname;
        this.getprofile = getprofile;
        this.userId = userId;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGetprofile() {
        return getprofile;
    }

    public void setGetprofile(String getprofile) {
        this.getprofile = getprofile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
