package johnny.nguyen.nbaagent;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnnywin24 on 9/12/17.
 */

public class Team implements Parcelable{

    public int teamID;
    public String key;
    public String city;
    public String name;
    public String conference;
    public String division;
    public String primaryColor;
    public String secondaryColor;
    public ArrayList<Player> roster;


    public Team(){}


    public Team(int teamID, String key, String city, String name, String conference, String division, String primaryColor, String secondaryColor, ArrayList<Player> roster) {
        this.teamID = teamID;
        this.key = key;
        this.city = city;
        this.name = name;
        this.conference = conference;
        this.division = division;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.roster = roster;
    }


    protected Team(Parcel in) {
        teamID = in.readInt();
        key = in.readString();
        city = in.readString();
        name = in.readString();
        conference = in.readString();
        division = in.readString();
        primaryColor = in.readString();
        secondaryColor = in.readString();
        roster = in.readArrayList(Team.class.getClassLoader());
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(teamID);
        dest.writeString(key);
        dest.writeString(city);
        dest.writeString(name);
        dest.writeString(conference);
        dest.writeString(division);
        dest.writeString(primaryColor);
        dest.writeString(secondaryColor);
        dest.writeList(roster);
    }
}
