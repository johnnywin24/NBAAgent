package johnny.nguyen.nbaagent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by johnnywin24 on 9/18/17.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder>{

    //List that will be passed to adapter
    List<Player> roster;

    public PlayerAdapter(List<Player> roster) {
        this.roster = roster;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        //Reference our player_row textviews
        TextView txtPostion;
        TextView txtName;
        TextView txtSalary;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtPostion = (TextView) itemView.findViewById(R.id.txtPosition);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtSalary = (TextView) itemView.findViewById(R.id.txtSalary);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Player player = roster.get(position);
        holder.txtPostion.setText(player.getPosition());
        holder.txtName.setText(player.getFirstName() + " " + player.getLastName());
        holder.txtSalary.setText(player.getSalary());
    }

    @Override
    public int getItemCount() {
        return roster.size();
    }

}
