package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TweetsAdapter extends  RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;


    //pass contents and list of tweets

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //each row inflate tweet layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_tweet,parent,false);
        return new ViewHolder(view);

    }

    //bind values based on position

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //getdata
        Tweet tweet=tweets.get(position);
        //bind tweet
        holder.bind(tweet);


    }

    @Override
    public int getItemCount() {
        return tweets.size();

    }

    public void clear()
    {

        tweets.clear();//modifiying existing ref
        notifyDataSetChanged();
    }

    public void addAll(List<Tweet> tweetList)
    {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }



    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTime;

        public ViewHolder(@NonNull View itemView){
            super((itemView));
            ivProfileImage=itemView.findViewById(R.id.ivProfileImage);
            tvBody=itemView.findViewById(R.id.tvBody);
            tvScreenName=itemView.findViewById(R.id.tvScreenName);
            tvTime=itemView.findViewById(R.id.tvTime);

        }

        public void bind(Tweet tweet) {
            tvBody.setText(tweet.body);
            tvScreenName.setText((tweet.user.screenName));
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);
            tvTime.setText(tweet.getFormattedTimestamp(tweet.time));

        }
    }
}
