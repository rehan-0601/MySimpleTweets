package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by rmulla on 10/7/15.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private long curr_max_id = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient(); //singleton client
        //max_id set to 0 for first time populating the listview. subsequent scrolls, handle in the scrolllistener
        populateTimeline(0);

    }

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        userFragment.setArguments(args);
        return userFragment;
    }

    //Send an API req to get timeline.json
    //populate the listview
    public void populateTimeline(long max_id) {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            //SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //oast.makeText(getActivity(), "Success-getHome", Toast.LENGTH_LONG).show();
                //Log.d("DEBUG", json.toString());
                //if (json == null) {
                //    Toast.makeText(getActivity(), "JSON is null", Toast.LENGTH_LONG).show();
                //} else {
                 //   Toast.makeText(getActivity(), Integer.toString(json.length()), Toast.LENGTH_LONG).show();
                //}
                //JSON HERE
                //DESERIALIZE JSON
                //CREATE MODELS and ADD THEM to the adapter
                //LOAD MODEL DATA INTO LISTVIEW
                addAll(Tweet.fromJSONArray(json));
                /*if(getaTweets().getCount()!=0) {
                    curr_max_id = getaTweets().getItem(getaTweets().getCount() - 1).getUid() - 1;
                }
                else curr_max_id=0;*/
                //Toast.makeText(getApplicationContext(), "MAX_ID is" + curr_max_id, Toast.LENGTH_LONG).show();

            }
            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //Toast.makeText(getActivity(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                Log.d("DEBUG", errorResponse.toString());

            }


        }   );
    }
}
