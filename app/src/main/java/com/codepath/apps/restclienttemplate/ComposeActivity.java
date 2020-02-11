package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Headers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

public class ComposeActivity extends AppCompatActivity {

    EditText etCompose;
    Button btnTweet;
    final int  MAX_TWEET_LENGTH=140;
    public static final String TAG="ComposeActivity";
    TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client=TwitterApp.getRestClient(this);
        setContentView(R.layout.activity_compose);
        etCompose=findViewById(R.id.etCompose);
        btnTweet=findViewById(R.id.btnTweet);

        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make API call after error handling
                String tweetcontent=etCompose.getText().toString();
                if(tweetcontent.isEmpty()){
                    Toast.makeText(ComposeActivity.this,"Field cannot be empty ",Toast.LENGTH_SHORT).show();
                    return;}
                if (tweetcontent.length()>MAX_TWEET_LENGTH){
                     Toast.makeText(ComposeActivity.this,"Sorry, exceeded chaarcter limit ",Toast.LENGTH_SHORT).show();
                     return;}
                Toast.makeText(ComposeActivity.this,"Tweet Tweet ",Toast.LENGTH_SHORT).show();

                client.publishTweet(tweetcontent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG,"onSuccess to publish tweet");
                        try {
                            Tweet tweet= Tweet.fromJson(json.jsonObject);
                            Log.i(TAG,"Published tweet says: "+tweet.body);
                            Intent intent=new Intent();
                            intent.putExtra("tweet", Parcels.wrap(tweet));
                            setResult(RESULT_OK,intent);
                            //Closes activity, pass data to parent
                            finish();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG,"failure to publish tweet",throwable);
                    }
                });
            }
        });
    }
}
