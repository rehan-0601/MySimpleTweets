package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TimelineActivity;
import com.codepath.apps.mysimpletweets.TweetDetailActivity;
import com.codepath.apps.mysimpletweets.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.helpers.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rmulla on 10/6/15.
 */
public abstract class TweetsListFragment extends Fragment{

    private long curr_max_id;
    private  ArrayList<Tweet> tweets;
    private  TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;

    public TweetsArrayAdapter getaTweets(){
        return aTweets;
    }

    public ArrayList<Tweet> getTweets(){
        if (tweets!=null){
            return tweets;
        }
      return null;
    }
    //will be used by the timeline activity while communicating with the fragment. Since it doesnt have arraylist and adapter
    public void clear(){
        if (tweets!=null){
        tweets.clear();
        }
    }

    public void addAll(ArrayList<Tweet> newTweets){
        if(tweets!=null){
            tweets.addAll(newTweets);
        }
        if(aTweets!=null){
            aTweets.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);


        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (getaTweets().getCount() != 0) {
                    long curr_max_id_new;
                    curr_max_id_new = getaTweets().getItem(getaTweets().getCount() - 1).getUid() - 1;
                    if (curr_max_id_new!=curr_max_id){
                        curr_max_id=curr_max_id_new;
                        populateTimeline(curr_max_id);
                    }

                }


                return true;
            }
        });


        swipeContainer = (SwipeRefreshLayout)v.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //clearing out old list before we populate new ones
                getaTweets().clear();
                populateTimeline(0);
                swipeContainer.setRefreshing(false);
                getaTweets().notifyDataSetChanged();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        return v;
    }

    //creation life cycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);



    }

    public abstract void  populateTimeline(long max_id);

    //creating an api which we can use from TimelineActivity
    public void addAll(List<Tweet> tweets){
        aTweets.addAll(tweets);
    }
}
