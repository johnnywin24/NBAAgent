package johnny.nguyen.nbaagent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * Created by johnnywin24 on 9/13/17.
 */

public class TeamDetails extends AppCompatActivity {

    public static final String EXTRA_TEAM = "selectedTeam";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        Team team = getIntent().getParcelableExtra(EXTRA_TEAM);
        int logo = getResources().getIdentifier(team.key.toLowerCase(),"drawable",getPackageName());
        ImageView ivTeamLogo = (ImageView) findViewById(R.id.ivTeamLogo);
        ivTeamLogo.setImageResource(logo);


        List<Player> roster = team.roster;

        //Sort by salary
        Collections.sort(roster, Player.BY_SALARY);


        //Populate recycler view with roster
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvPlayerRow);
        PlayerAdapter playerAdapter = new PlayerAdapter(roster);
        recyclerView.setAdapter(playerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TeamDetails.this));



    }
}
