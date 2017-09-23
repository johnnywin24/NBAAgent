package johnny.nguyen.nbaagent;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DecimalFormat;
import java.util.Comparator;

/**
 * Created by johnnywin24 on 9/17/17.
 */

public class Player implements Parcelable {
    private String firstName;
    private String lastName;
    private String position;
    private String jersey;
    private String salary;

    //For sorting by salary

    public static Comparator<Player> BY_SALARY = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            String sal1 = o1.salary.replaceAll("[$,]", "");
            String sal2 = o2.salary.replaceAll("[$,]", "");

            if (sal1.equals("N/A")) sal1 = "0";
            if (sal2.equals("N/A")) sal2 = "0";
            return Integer.valueOf(sal2).compareTo(Integer.valueOf(sal1));
        }
    };


    public Player() {

    }

    public Player(String firstName, String lastName, String position, String jersey, String salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.jersey = jersey;
        this.salary = salary;
    }

    protected Player(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        position = in.readString();
        jersey = in.readString();
        salary = in.readString();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }


    public String getJersey() {
        return jersey;
    }

    public void setJersey(String jersey) {
        this.jersey = jersey;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        if (salary == null || salary.isEmpty() || salary.equals("null")) this.salary = "N/A";
        else {
            double s = Double.parseDouble(salary);
            DecimalFormat decimalFormat = new DecimalFormat("$#,###");
            this.salary = decimalFormat.format(s);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(position);
        dest.writeString(jersey);
        dest.writeString(salary);

    }
}
