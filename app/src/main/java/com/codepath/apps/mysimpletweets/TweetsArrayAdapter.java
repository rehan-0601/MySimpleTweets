package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.helpers.LinkifiedTextView;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by rmulla on 9/29/15.
 */
//Taking the tweets objets and turning them into views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    private Tweet tweet;
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        //super(context, android.R.layout.simple_list_item_1);
        //using my own custom logic instead of simple_list_item_1
        super(context, 0, tweets);

    }

    //**update this to use View Holder Pattern

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get tweet
        tweet = getItem(position);
        //find /inflate template
        //find the subviews to fill in data with the template
        //populate data into subviews
        //return the view to be inserted into the list
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            //convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);

        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvUser);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
        ImageView ivMedia = (ImageView) convertView.findViewById(R.id.ivMedia);
        TextView tvRetweet = (TextView) convertView.findViewById(R.id.tvRetweet);
        TextView tvFavorite =(TextView) convertView.findViewById(R.id.tvFavorite);

        tvUser.setText(tweet.getUser().getName());
        tvUserName.setText("@"+tweet.getUser().getScreenName());
        ivProfileImage.setTag(tweet);
        tvBody.setText(tweet.getBody());
        tvCreatedAt.setText(trimRelativeTimeAgo(getRelativeTimeAgo(tweet.getCreatedAt())));

        ivProfileImage.setImageResource(android.R.color.transparent);//clear out old image for recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        ivMedia.setImageResource(android.R.color.transparent);
        if(tweet.getMediaUrl()!=null) {
            //ivMedia.setMaxHeight(175);

            //getting image of size:small, supported by twitter
            Picasso.with(getContext()).load(tweet.getMediaUrl() + ":small").into(ivMedia);
        }

        tvRetweet.setText(Integer.toString(tweet.getRetweetCount()));
        tvFavorite.setText(Integer.toString(tweet.getFavoritedCount()));





        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tweet curr_tweet =(Tweet) v.getTag();
                String screen_name = curr_tweet.getUser().getScreenName();
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", curr_tweet.getUser().getScreenName());
                i.putExtra("current_tweet", curr_tweet);
                getContext().startActivity(i);
            }
        });

        return convertView;
    }


    private String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    private String trimRelativeTimeAgo(String str){
        String[] timeArray = str.split(" ");
        if (timeArray.length>1) {
            String result = timeArray[0] + timeArray[1].substring(0, 1);
            return  result;
        }
        else return str;
    }
}
