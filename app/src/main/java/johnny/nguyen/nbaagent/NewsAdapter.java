package johnny.nguyen.nbaagent;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by johnnywin24 on 9/14/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<Article> articles;

    public NewsAdapter(List<Article> articles) {
        this.articles = articles;
    }

    //Create a viewholder class to represent one row of data
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;

        //When class is initalized, get a handle on the row views
        public MyViewHolder(final View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
        }

    }

    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.txtTitle.setText(article.getTitle());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


}
