package johnny.nguyen.nbaagent;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String key = "a36ea8d2c81c4fa8b4577e05bb8cd8e2";
    String urlTeams = "https://api.fantasydata.net/v3/nba/scores/JSON/Teams";
    String urlSchedule = "https://api.fantasydata.net/v3/nba/scores/JSON/GamesByDate/";
    String urlRoster = "https://api.fantasydata.net/v3/nba/stats/JSON/Players/";
    String selectedDate = "";
    CalendarView calendarView;
    TextView txtDate;
    ProgressBar progressBar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_schedule:

                    return true;
                case R.id.navigation_news:
                    Intent intent = new Intent(getApplicationContext(),NewsActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_standings:

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("selectedDate", selectedDate);
        outState.putInt("calendarState", calendarView.getVisibility());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        txtDate = (TextView) findViewById(R.id.txtDate);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //Get a date string in the format of MM-DD-YYYY
        Calendar cal;
        if (savedInstanceState == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cal = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                selectedDate = dateFormat.format(cal.getTime());
            }
        }
        else{
            selectedDate = savedInstanceState.getString("selectedDate");
            //noinspection WrongConstant
            calendarView.setVisibility(savedInstanceState.getInt("calendarState"));
        }

        PopulateSchedule populateSchedule = new PopulateSchedule();
        populateSchedule.execute(urlSchedule, selectedDate);
        txtDate.setText(selectedDate);
        initalizeCalendar();
    }


    //Background task to download today's NBA Schedule as the home page
    public class PopulateSchedule extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            String result = "";
            selectedDate = args[1];

            try {
                URL url = new URL(args[0] + selectedDate);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestProperty("Ocp-Apim-Subscription-Key", key);

                InputStream stream = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(stream);

                int data = reader.read();
                while (data != -1) {
                    char c = (char) data;
                    result += c;
                    data = reader.read();
                }
                //Log.i("RESULTS", result);
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Failed";
        }

        @Override
        protected void onPostExecute(String s) {

            try {


                //Loop through all teams and add into an array list
                ArrayList<Game> list = new ArrayList<Game>();
                JSONArray jsonArray = new JSONArray(s);
                TextView txtNoGames = (TextView) findViewById(R.id.txtNoGames);

                //if the array size is 0, then there are no games scheduled that day
                if (jsonArray.length() == 0) {
                    txtNoGames.setVisibility(View.VISIBLE);
                } else {
                    txtNoGames.setVisibility(View.INVISIBLE);
                    for (int i = 0; i < jsonArray.length(); i++) {


                        JSONObject object = jsonArray.getJSONObject(i);
                        Game game = new Game();
                        String homeTeam = object.getString("HomeTeam");
                        String awayTeam = object.getString("AwayTeam");
                        String time = object.getString("DateTime");
                        time = time.substring(time.indexOf("T") + 1);
                        time = convert24(time);
                        int awayImage = getResources().getIdentifier(awayTeam.toLowerCase(), "drawable", getPackageName());
                        int homeImage = getResources().getIdentifier(homeTeam.toLowerCase(), "drawable", getPackageName());
                        game.setHomeImage(homeImage);
                        game.setAwayImage(awayImage);
                        game.setHomeTeam(homeTeam);
                        game.setAwayTeam(awayTeam);
                        game.setDateTime(time);
                        list.add(game);
                    }


                    RecyclerView gameRecycler = (RecyclerView) findViewById(R.id.gameRecycler);
                    GameAdapter recyclerAdapter = new GameAdapter(list);
                    gameRecycler.setAdapter(recyclerAdapter);
                    gameRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));




                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


/*
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            Type collectionType = new TypeToken<Collection<Game>>(){}.getType();
            Collection<Game> col = gson.fromJson(s, collectionType);
            ArrayList<Game> list = new ArrayList<Game>(col);

            RecyclerView gameRecycler = (RecyclerView) findViewById(R.id.gameRecycler);
            GameAdapter recyclerAdapter = new GameAdapter(list);
            gameRecycler.setAdapter(recyclerAdapter);
            gameRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
*/

            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(s);
        }
    }

    //Async task to pull team rosters
    public class GetRoster extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            String result = "";
            try {
                URL url = new URL(params[0] + params[1]);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestProperty("Ocp-Apim-Subscription-Key", key);

                InputStream inputStream = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);

                int data = reader.read();
                while (data != -1) {
                    char c = (char) data;
                    result += c;
                    data = reader.read();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "FAILED";
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONArray jsonArray = new JSONArray(s);
                ArrayList<Player> roster = new ArrayList<Player>();
                Team team = new Team();
                JSONObject object = jsonArray.getJSONObject(0);
                String key = object.getString("Team");

                for (int i = 0; i < jsonArray.length(); i++) {
                    object = jsonArray.getJSONObject(i);
                    Player player = new Player();
                    player.setFirstName(object.getString("FirstName"));
                    player.setLastName(object.getString("LastName"));
                    player.setJersey(object.getString("Jersey"));
                    player.setPosition(object.getString("PositionCategory"));
                    player.setSalary(object.getString("Salary"));

                    roster.add(player);
                }

                team.key = key;
                team.roster = roster;
                Intent intent = new Intent(getApplicationContext(), TeamDetails.class);
                intent.putExtra("selectedTeam", team);

                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(s);
        }
    }


    public void toggleCalendar(View view) {
        if (calendarView.getVisibility() == View.INVISIBLE)
            calendarView.setVisibility(View.VISIBLE);
        else calendarView.setVisibility(View.INVISIBLE);
    }

    //Method to display team info when clicked form Reycler view
    public void teamInfo(View view) {
        GetRoster getRoster = new GetRoster();
        getRoster.execute(urlRoster, view.getTag().toString());
    }

    //Method to turn on date picker listener
    public void initalizeCalendar() {
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = String.valueOf(month + 1) + "-" + dayOfMonth + "-" + year;
                calendarView.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                PopulateSchedule populateSchedule = new PopulateSchedule();
                populateSchedule.execute(urlSchedule, selectedDate);
                txtDate.setText(selectedDate);
            }
        });
    }

    public String convert24(String time){

        time = time.substring(time.indexOf("T") + 1);
        int hours = Integer.parseInt(time.substring(0, 2));
        if (hours > 12) {
            hours = hours - 12;
            time = time + " PM";
        } else time = time + " AM";

        String newTime = String.valueOf(hours) + time.substring(2);
        return newTime;
    }

}
