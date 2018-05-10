package com.example.max.recyclerviewtesting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    //private List<String> mData;
    private List<NewsElement> mData;
    private Context context;
    private LayoutInflater mInflater;
    //private int position;
    //private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, List<NewsElement> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context=context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NewsElement news = mData.get(position);

        holder.NewsTitle.setText(news.getNewsText()); //Тут добавляем текст
        holder.NewsDate.setText(news.getNewsDate());
        holder.NewsTime.setText(news.getNewsTime());

       // if(!news.getNewsPicURL().equals("")||!news.getNewsPicURL().equals(" ")) {
           // Log.d("ALAAAAAAAAAAAAAARM", news.getNewsPicURL());
        if(news.getNewsPicURL().isEmpty()){
            Picasso.get().load("https://yt3.ggpht.com/a-/AJLlDp3w3Ok_TD46pLqIlFB7_TbbwUHQ4D867hKRhQ=s900-mo-c-c0xffffffff-rj-k-no").into(holder.NewsPoster);
            Log.d("NewsPosterLost", "потрачено "+news.getNewsPicURL());
        }else {
            Picasso.get().load(news.getNewsPicURL()).resize(80, 80).into(holder.NewsPoster);
            Log.d("NewsPosterLostGet", news.getNewsPicURL());
        }
       // }
        holder.newsClicker.setRecord(news);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  { //Тут инициализируем элементы
        TextView NewsTitle, NewsTime, NewsDate;
        ImageView NewsPoster;
        CardView newsCard;
        NewsClicker newsClicker = new NewsClicker();

        ViewHolder(View itemView) {
            super(itemView);
            NewsTitle = itemView.findViewById(R.id.publis_title);
            NewsPoster=itemView.findViewById(R.id.publish_picture);
            NewsTime=itemView.findViewById(R.id.publish_time);
            NewsDate=itemView.findViewById(R.id.publish_date);
            newsCard = itemView.findViewById(R.id.publish_newscard);

            //newsClicker.setPosition(position);
            newsCard.setOnClickListener(newsClicker);

           // itemView.setOnClickListener(this);
        }

       // @Override
       // public void onClick(View view) {
       //     if (mClickListener != null)
       //         mClickListener.onItemClick(view, getAdapterPosition());
       // }
    }

    // convenience method for getting data at click position
   String getItem(int id) {
       return mData.get(id).newsText;
   }

    class NewsClicker implements View.OnClickListener {

        NewsElement newsElement;
        int position;

        @Override
        public void onClick(View v) {

           Intent intent = new Intent(context, NewsWebview.class);
            position=mData.indexOf(newsElement);
           intent.putExtra("NewsLink", mData.get(position).getNewsLink());
           context.startActivity(intent);

            //Toast.makeText(v.getContext(), "Clicked on "+position, Toast.LENGTH_SHORT).show();
        }



        public void setRecord(NewsElement newsElement){
            this.newsElement=newsElement;
        }
    }

    // allows clicks events to be caught
    //void setClickListener(ItemClickListener itemClickListener) {
    //    this.mClickListener = itemClickListener;
    //}
//
    //// parent activity will implement this method to respond to click events
    //public interface ItemClickListener {
    //    void onItemClick(View view, int position);
    //}
}