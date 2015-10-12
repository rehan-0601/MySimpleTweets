package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.ComposeDialog.ComposeDialogListener;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.helpers.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.api.TwitterApi;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {
    HomeTimelineFragment hometimelineFragment;
    public static final int REQUEST_CODE = 10;
    private ComposeDialog composeDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        //addListenerToListView();

        //setup listeners that timeline activity needs to react to
        //get the viewpager
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        //set viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        //find the pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        //attach pager tabs to viewpager
        tabStrip.setViewPager(vpPager);
        composeDialog = ComposeDialog.newInstance("Compose a tweet");
        setUpListeners();

    }

    private void setUpListeners() {

        composeDialog.setComposeDialogListener(new ComposeDialogListener() {
            //hello
            @Override
            public void onTweetFinish(Tweet postedTweet) {
                //Need to add new tweet to beginning of adapter. Not sure which api. Using the List to do it.
                //HomeTimelineFragment hometimelineFrag = new HomeTimelineFragment();
                ArrayList<Tweet> newTweets = new ArrayList<Tweet>();
                if (hometimelineFragment.getaTweets()!=null){
                    newTweets.addAll(hometimelineFragment.getTweets());
                }
                newTweets.add(0, postedTweet);
                hometimelineFragment.clear();
                hometimelineFragment.addAll(newTweets);


            }
        });
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
        /*if (id == R.id.action_settings) {
            return true;
        }*/

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
        //initialising in onCreate. we need the instance earlier to set the listener on it. and we need to use same instance not a new one
        composeDialog.show(fm, "dialog_compose");
    }

    //return order of fragments in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter{

        final int PAGE_COUNT=2;
        private String tabTitles[] ={"Home","Mentions"};


        //adapter gets the manager which is used to insert or remove fragment from the activity
        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            hometimelineFragment = new HomeTimelineFragment();
            return  hometimelineFragment;
        }
        else if(position == 1){
            return new MentionsTimelineFragment();
        }else
            return null;
    }

    //how many fragments
    @Override
    public int getCount() {
        return  tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
    public User user;
    public void onProfileView(MenuItem mi){

        TwitterClient client = TwitterApplication.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Toast.makeText(TimelineActivity.this, "PAss get user", Toast.LENGTH_SHORT).show();
                user = User.fromJSON(response);
                Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
                i.putExtra("screen_name", user.getScreenName());
                startActivity(i);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Toast.makeText(TimelineActivity.this, "FAIL get user", Toast.LENGTH_SHORT).show();
            }
        });
        //Launch profile view

    }
}
