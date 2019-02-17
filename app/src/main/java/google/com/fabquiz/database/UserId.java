package google.com.fabquiz.database;

/**
 * Created by Pratik on 3/16/2018.
 */

public class UserId {
    public String userId;
    public <T extends UserId>T withId(final String id) {
        this.userId=id;
        return (T) this;
    }

}
