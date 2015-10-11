package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.helpers.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rmulla on 10/6/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private long curr_max_id = 0;
    private SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient(); //singleton client
        //max_id set to 0 for first time populating the listview. subsequent scrolls, handle in the scrolllistener
        populateTimeline(0);

    }


    //Send an API req to get timeline.json
    //populate the listview
    public void populateTimeline(long max_id){
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //Toast.makeText(getApplicationContext(), "Success-getHome", Toast.LENGTH_LONG).show();
                Log.d("DEBUG", json.toString());
                //JSON HERE
                //DESERIALIZE JSON
                //CREATE MODELS and ADD THEM to the adapter
                //LOAD MODEL DATA INTO LISTVIEW
                addAll(Tweet.fromJSONArray(json));
                //curr_max_id = getaTweets().getItem(getaTweets().getCount() - 1).getUid() - 1;
                //Toast.makeText(getApplicationContext(), "MAX_ID is" + curr_max_id, Toast.LENGTH_LONG).show();

            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //Toast.makeText(getApplicationContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                Log.d("DEBUG", errorResponse.toString());

            }


        }, max_id);
    }
}
