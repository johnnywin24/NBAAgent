package johnny.nguyen.nbaagent;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by johnnywin24 on 9/14/17.
 */

public class Game {

    private String AwayTeam;
    private String HomeTeam;
    private String DateTime;
    private int awayImage;
    private int homeImage;


    public Game() {
    }

    public Game(String awayTeam, String homeTeam, String dateTime, int homeImage, int awayImage) {
        this.AwayTeam = awayTeam;
        this.HomeTeam = homeTeam;
        this.awayImage = awayImage;
        this.homeImage = homeImage;
        this.DateTime = dateTime;
    }

    public int getAwayImage() {
        return awayImage;
    }

    public void setAwayImage(int awayImage) {
        this.awayImage = awayImage;
    }

    public int getHomeImage() {
        return homeImage;
    }

    public void setHomeImage(int homeImage) {
        this.homeImage = homeImage;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.AwayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.HomeTeam = homeTeam;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        this.DateTime = dateTime;
    }

}
