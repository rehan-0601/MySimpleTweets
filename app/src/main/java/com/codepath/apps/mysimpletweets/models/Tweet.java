package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rmulla on 9/29/15.
 */

//parse the JSON + Store the results , encapsulate state logic or display logic
public class Tweet implements Serializable {

    private String body;
    private long uid;
    private User user;
    private String createdAt;
    private String mediaUrl;
    private int retweetCount;
    private int favoritesCount;

    public String getBody() {
        return body;
    }
    public int getRetweetCount(){
        return retweetCount;
    }

    public int getFavoritedCount(){
        return favoritesCount;
    }

    public String getMediaUrl(){
        return mediaUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    //Deserialise the JSON
    //Tweet.fromJSON("{...}")=> <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet = new Tweet();
        //Extract values from the JSON, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            tweet.retweetCount = jsonObject.getInt("retweet_count");

            tweet.favoritesCount = jsonObject.optInt("favorites_count",0);

            tweet.mediaUrl = null;
            if(jsonObject.optJSONObject("entities")!=null){
                JSONObject entitiesObject = jsonObject.getJSONObject("entities");
                if(entitiesObject.optJSONArray("media")!=null){
                    JSONArray mediaArray = entitiesObject.getJSONArray("media");
                    if(mediaArray.length()>0){
                        tweet.mediaUrl = mediaArray.getJSONObject(0).getString("media_url");
                    }
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return tweet;
    }

    //Tweet.fromJSONArray([{...},{...},{...}]) => List<Tweets>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets = new ArrayList<>();

        //Iterate the JSON array and create tweets
        for(int i=0; i<jsonArray.length(); i++){
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet !=null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }

        return tweets;
    }

}
