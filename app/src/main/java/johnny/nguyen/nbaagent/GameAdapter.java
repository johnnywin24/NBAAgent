package johnny.nguyen.nbaagent;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by johnnywin24 on 9/14/17.
 */

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private List<Game> games;

    public GameAdapter(List<Game> games) {
        this.games = games;
    }

    //Create a viewholder class to represent one row of data
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtHomeTeam;
        TextView txtAwayTeam;
        TextView txtTime;
        ImageView ivHome;
        ImageView ivAway;

        //When class is initalized, get a handle on the row views
        public MyViewHolder(final View itemView) {
            super(itemView);
            txtHomeTeam = (TextView) itemView.findViewById(R.id.txtHomeTeam);
            txtAwayTeam = (TextView) itemView.findViewById(R.id.txtAwayTeam);
            txtTime = (TextView) itemView.findViewById(R.id.txtVs);
            ivHome = (ImageView) itemView.findViewById(R.id.ivHomeTeam);
            ivAway = (ImageView) itemView.findViewById(R.id.ivAwayTeam);
        }
    }

    @Override
    public GameAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameAdapter.MyViewHolder holder, int position) {
        Game game = games.get(position);
        holder.txtHomeTeam.setText(game.getHomeTeam());
        holder.txtAwayTeam.setText(game.getAwayTeam());
        holder.txtTime.setText(game.getDateTime());
        holder.ivAway.setImageResource(game.getAwayImage());
        holder.ivAway.setTag(game.getAwayTeam());
        holder.ivHome.setTag(game.getHomeTeam());
        holder.ivHome.setImageResource(game.getHomeImage());
    }

    @Override
    public int getItemCount() {
        return games.size();
    }


}
