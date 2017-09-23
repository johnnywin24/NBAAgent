package johnny.nguyen.nbaagent;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    String key = "a36ea8d2c81c4fa8b4577e05bb8cd8e2";
    String urlNews = "https://api.fantasydata.net/v3/nba/scores/JSON/News";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        PopulateNews populateNews = new PopulateNews();
        populateNews.execute(urlNews);
    }

    //Background task to download today's NBA Schedule as the home page
    public class PopulateNews extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... args) {
            String result = "";

            try {
                URL url = new URL(args[0]);
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
                ArrayList<Article> list = new ArrayList<Article>();
                JSONArray jsonArray = new JSONArray(s);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        Article article = new Article();
                        String title = object.getString("Title");
                        String author = object.getString("Author");
                        String time = object.getString("Updated");

                        time = time.substring(time.indexOf("T") + 1);
                        time = convert24(time);
                        article.setAuthor(author);
                        article.setTitle(title);
                        article.setDateTime(time);

                        list.add(article);
                    }


                    RecyclerView newRecycler = (RecyclerView) findViewById(R.id.rvNews);
                    NewsAdapter recyclerAdapter = new NewsAdapter(list);
                    newRecycler.setAdapter(recyclerAdapter);
                    newRecycler.setLayoutManager(new LinearLayoutManager(NewsActivity.this));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressBar.setVisibility(View.INVISIBLE);
            super.onPostExecute(s);
        }
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
