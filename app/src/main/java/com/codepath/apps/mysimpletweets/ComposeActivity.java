package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {
    private TwitterClient client;
    private EditText etTweet;
    private Tweet postedTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etTweet = (EditText)findViewById(R.id.etTweet);
        client = TwitterApplication.getRestClient();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    public void onTweet(MenuItem mi){
        //handle click here
        //grab the tweet text
        String tweet = etTweet.getText().toString();

        //call the post api
        client.postTweet(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Toast.makeText(getApplicationContext(), "success-post", Toast.LENGTH_SHORT).show();
                //from the response JSON, create a Tweet object, pass this into the intent while returning to timeline
                postedTweet = Tweet.fromJSON(response);
                //pass the tweet object back to calling activity.
                //close this intent
                Intent intent = new Intent();
                intent.putExtra("posted_tweet", postedTweet);
                setResult(RESULT_OK, intent);
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Toast.makeText(getApplicationContext(), "dfsdvdjhvj", Toast.LENGTH_SHORT).show();
                //
                // Toast.makeText(getApplicationContext(), "fail-post", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, tweet);


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


}
