package com.codepath.apps.mysimpletweets;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class TweetDetailActivity extends AppCompatActivity {

    private Tweet currentTweet;
    private ComposeDialog composeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#55ACEE")));

        currentTweet = (Tweet) getIntent().getSerializableExtra("current_tweet");

        ImageView ivProfilePic = (ImageView)findViewById(R.id.ivProfilePic);
        TextView tvUserName  = (TextView) findViewById(R.id.tvUserName);
        TextView tvScreenName = (TextView)findViewById(R.id.tvScreenName);
        TextView tvTweet = (TextView)findViewById(R.id.tvTweet);
        TextView tvCounter = (TextView)findViewById(R.id.tvCounter);
        ImageView ivMedia = (ImageView)findViewById(R.id.ivMedia);

        Picasso.with(getApplicationContext()).load(currentTweet.getUser().getProfileImageUrl()).into(ivProfilePic);
        tvUserName.setText(currentTweet.getUser().getName());
        tvScreenName.setText("@" + currentTweet.getUser().getScreenName());
        tvTweet.setText(currentTweet.getBody());
        tvCounter.setText(Integer.toString(currentTweet.getRetweetCount())+ " RETWEETS "+ Integer.toString(currentTweet.getFavoritedCount())+" FAVORITES");
        if(currentTweet.getMediaUrl()!=null){
            //Toast.makeText(this, "Media URL not null", Toast.LENGTH_SHORT).show();
            Picasso.with(getApplicationContext()).load(currentTweet.getMediaUrl()).into(ivMedia);
        }






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_detail, menu);
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

    public void onReplyTweet(View view) {
        FragmentManager fm = getSupportFragmentManager();
        composeDialog = ComposeDialog.newInstance("Compose a tweet");
        Bundle args = new Bundle();
        args.putString("replyToUser",currentTweet.getUser().getScreenName());
        composeDialog.setArguments(args);
        //initialising in onCreate. we need the instance earlier to set the listener on it.
        //composeDialog = ComposeDialog.newInstance("Compose a tweet");
        composeDialog.show(fm, "dialog_compose");
    }
}
