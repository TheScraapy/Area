package com.teamponey.teamponeay.area.Fragments.Oauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamponey.teamponeay.area.Models.BasicResponse;
import com.teamponey.teamponeay.area.Models.OauthModel;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.Services.AuthService;
import com.teamponey.teamponeay.area.WidgetCreationActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import retrofit.RetrofitError;

import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_ACTION;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_REACTION;

public class TwitterFrag extends Fragment {
    public static TwitterFrag newInstance() {
        return (new TwitterFrag());
    }

    View view;
    private TwitterLoginButton loginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TwitterConfig config = new TwitterConfig.Builder(getContext())
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.CONSUMER_KEY), getString(R.string.CONSUMER_SECRET)))
                .debug(true)
                .build();

        Twitter.initialize(config);

        view = inflater.inflate(R.layout.frag_twitter, container, false);

        loginButton = view.findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

                TwitterAuthToken authToken = session.getAuthToken();

                String secret = authToken.secret;

                OauthModel twitter = new OauthModel();

                twitter.access_token = authToken.token + " " + authToken.secret ;

                Log.d("Area", "Token total: " + twitter.access_token);
                Log.d("Area", "Token twitter: " + authToken.token);
                Log.d("Area", "Token secret: " + authToken.secret);

                new AuthService(getContext()).twitter(twitter, new TwitterFrag.UiCallback());
            }

            @Override
            public void failure(TwitterException exception) {
                Log.e("Area", "Error with Twitter: " + exception);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private class UiCallback implements retrofit.Callback<BasicResponse> {

        @Override
        public void success(final BasicResponse answer, retrofit.client.Response response) {

            Log.d("Area", " succes twitter");

            if (WidgetCreationActivity.newWidget.getAction() == -1) {
                WidgetCreationActivity.getInstance().showFragment(FRAGMENT_ACTION);
            } else {
                WidgetCreationActivity.getInstance().showFragment(FRAGMENT_REACTION);
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Log.d("Area", " failed twitter");
            if (error == null)
                Log.e("AREA", getResources().getString(R.string.no_internet));
            else
                Log.e("AREA", getResources().getString(R.string.error, error));
        }
    }
}