package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.ComposeDialog.ComposeDialogListener;
import com.codepath.apps.mysimpletweets.helpers.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 10;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private long curr_max_id = 0;
    private SwipeRefreshLayout swipeContainer;
    private ComposeDialog composeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55ACEE")));

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);



        addListenerToListView();


        client = TwitterApplication.getRestClient(); //singleton client
        //max_id set to 0 for first time populating the listview. subsequent scrolls, handle in the scrolllistener
        populateTimeline(0);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                //clearing out old list before we populate new ones
                aTweets.clear();
                populateTimeline(0);
                swipeContainer.setRefreshing(false);
                aTweets.notifyDataSetChanged();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //setup listeners that timeline activity needs to react to
        setUpListeners();


    }

    private void setUpListeners(){
        composeDialog = ComposeDialog.newInstance("Compose a tweet");
        composeDialog.setComposeDialogListener(new ComposeDialogListener() {
            //hello
            @Override
            public void onTweetFinish(Tweet postedTweet) {
                //Need to add new tweet to beginning of adapter. Not sure which api. Using the List to do it.
                tweets.add(0, postedTweet);
                //aTweets.add(postedTweet);
                aTweets.notifyDataSetChanged();

            }
        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tweet tweet = tweets.get(position);
                Intent tweetDetailIntent = new Intent(TimelineActivity.this, TweetDetailActivity.class);
                tweetDetailIntent.putExtra("current_tweet",tweet);
                startActivity(tweetDetailIntent);

            }
        });

    }

    private void addListenerToListView(){
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline(curr_max_id);
                return true;
            }
        });
    }
    //Send an API req to get timeline.json
    //populate the listview
    private void populateTimeline(long max_id){
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
                aTweets.addAll(Tweet.fromJSONArray(json));
                curr_max_id = aTweets.getItem(aTweets.getCount() - 1).getUid() - 1;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onComposeAction(MenuItem mi) {
        // handle click here on compose button here
        //Intent i = new Intent(TimelineActivity.this, ComposeDialog.class);
        //startActivityForResult(i, REQUEST_CODE);
        showComposeDialog();
    }

    private void showComposeDialog(){
        FragmentManager fm = getSupportFragmentManager();
        //initialising in onCreate. we need the instance earlier to set the listener on it.
        //composeDialog = ComposeDialog.newInstance("Compose a tweet");
        composeDialog.show(fm, "dialog_compose");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            //recover the tweet passed in ==> postedTweet.
            //append to the adapter
            //notify changes
            Tweet postedTweet = (Tweet) data.getSerializableExtra("posted_tweet");
            //Need to add new tweet to beginning of adapter. Not sure which api. Using the List to do it.
            tweets.add(0, postedTweet);
            //aTweets.add(postedTweet);
            aTweets.notifyDataSetChanged();
        }
    }

}
