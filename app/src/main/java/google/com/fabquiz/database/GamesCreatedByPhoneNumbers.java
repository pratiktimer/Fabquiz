package google.com.fabquiz.database;

/**
 * Created by Pratik on 3/10/2018.
 */

public class GamesCreatedByPhoneNumbers {
    String gameid;
    String player2;
    Integer scoreofplayer1;
    Integer scoreofplayer2;
    String player2Profile;

    public GamesCreatedByPhoneNumbers() {
    }

    public GamesCreatedByPhoneNumbers(String gameid, String player2, Integer scoreofplayer1, Integer scoreofplayer2, String player2Profile) {
        this.gameid = gameid;
        this.player2 = player2;
        this.scoreofplayer1 = scoreofplayer1;
        this.scoreofplayer2 = scoreofplayer2;
        this.player2Profile = player2Profile;
    }

    public String getGameid() {
        return gameid;
    }

    public void setGameid(String gameid) {
        this.gameid = gameid;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
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

    public String getPlayer2Profile() {
        return player2Profile;
    }

    public void setPlayer2Profile(String player2Profile) {
        this.player2Profile = player2Profile;
    }
}
