package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * Created by rmulla on 10/3/15.
 */
public class ComposeDialog extends DialogFragment {
    private String replyToUser;
    private TwitterClient client;
    private Button btnTweet;
    private TextView tvLength;
    private EditText etTweet;
    private Tweet postedTweet;
    //ASK
    private ComposeDialogListener cdlistener;

    public ComposeDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
        this.cdlistener=null;
    }

    public static ComposeDialog newInstance(String title) {
        ComposeDialog frag = new ComposeDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    public void setComposeDialogListener(ComposeDialogListener listener){
        this.cdlistener= listener;
    }

    @Override
    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        //not working in onViewCreated, hence using it in onResume
        etTweet.getText().clear();
        tvLength.setText("140");

        if(replyToUser!=null){
            Toast.makeText(getContext(), "reply to  user NOT NUll", Toast.LENGTH_SHORT).show();
            etTweet.setText("@"+replyToUser);
            etTweet.requestFocus();
        }

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        replyToUser = getArguments().getString("replyToUser",null);
        return inflater.inflate(R.layout.dialog_compose, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etTweet = (EditText) view.findViewById(R.id.etTweet);
        tvLength = (TextView)view.findViewById(R.id.tvLength);
        btnTweet = (Button) view.findViewById(R.id.btnTweet);



        btnTweet.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {
                                            onTweet();
                                        }
                                    }
        );

        etTweet.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int length = etTweet.getText().toString().length();
                int limit = 140 - length;
                tvLength.setText(Integer.toString(limit));
                return false;
            }
        });



    }


    public void onTweet() {
        //handle click here
        //grab the tweet text
        String tweet = etTweet.getText().toString();
        client = TwitterApplication.getRestClient();
        //call the post api
        client.postTweet(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getContext(), "success-post", Toast.LENGTH_SHORT).show();
                //from the response JSON, create a Tweet object, pass this into the intent while returning to timeline
                postedTweet = Tweet.fromJSON(response);
                //pass the tweet object back to calling activity using the listener.
                //close this fragment
                if(cdlistener==null){
                    Toast.makeText(getContext(), "Listener is NULL", Toast.LENGTH_SHORT).show();
                }else {
                    cdlistener.onTweetFinish(postedTweet);
                }
                dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), "fail-post", Toast.LENGTH_SHORT).show();
                dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getContext(), "Fail-post", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }, tweet);

    }

    public interface ComposeDialogListener{
        void onTweetFinish(Tweet postedTweet);
    }


}

