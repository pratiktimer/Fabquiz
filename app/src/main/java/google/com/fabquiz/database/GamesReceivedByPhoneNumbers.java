package google.com.fabquiz.database;

/**
 * Created by Pratik on 3/10/2018.
 */

public class GamesReceivedByPhoneNumbers {
    String gameid;
    String player1;
   Integer scoreofplayer1;
    Integer scoreofplayer2;
    String player1Profile;
    String player1phone;
    String player2phone;
    String catname;

    public GamesReceivedByPhoneNumbers() {
    }

    public GamesReceivedByPhoneNumbers(String gameid, String player1, Integer scoreofplayer1, Integer scoreofplayer2, String player1Profile, String player1phone, String player2phone, String catname) {
        this.gameid = gameid;
        this.player1 = player1;
        this.scoreofplayer1 = scoreofplayer1;
        this.scoreofplayer2 = scoreofplayer2;
        this.player1Profile = player1Profile;
        this.player1phone = player1phone;
        this.player2phone = player2phone;
        this.catname = catname;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public Integer getScoreofplayer1() {
        return scoreofplayer1;
    }

    public void setScoreofplayer1(Integer scoreofplayer1) {
        this.scoreofplayer1 = scoreofplayer1;
    }

    public Integer getScoreofplayer2() {
        return scoreofplayer2;
    }

    public void setScoreofplayer2(Integer scoreofplayer2) {
        this.scoreofplayer2 = scoreofplayer2;
    }

    public String getPlayer1Profile() {
        return player1Profile;
    }

    public void setPlayer1Profile(String player1Profile) {
        this.player1Profile = player1Profile;
    }

    public String getPlayer1phone() {
        return player1phone;
    }

    public void setPlayer1phone(String player1phone) {
        this.player1phone = player1phone;
    }

    public String getPlayer2phone() {
        return player2phone;
    }

    public void setPlayer2phone(String player2phone) {
        this.player2phone = player2phone;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }
}
