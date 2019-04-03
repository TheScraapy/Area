package com.teamponey.teamponeay.area.Fragments.Oauth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.teamponey.teamponeay.area.Models.OauthModel;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.Services.AuthService;
import com.teamponey.teamponeay.area.WidgetCreationActivity;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_ACTION;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_REACTION;

public class FacebookFrag extends Fragment {
    public static FacebookFrag newInstance() {
        return (new FacebookFrag());
    }

    View view;

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private boolean isLoggedIn;
    private OauthModel facebook;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_facebook, container, false);

        callbackManager = CallbackManager.Factory.create();

        facebook = new OauthModel();

        accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

        LoginManager loginManager = LoginManager.getInstance();

        loginManager.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile", "user_friends", "user_photos", "user_likes", "user_posts");
        loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebook.access_token = accessToken.getCurrentAccessToken().getToken();
                new AuthService(getContext()).facebook(facebook, new FacebookFrag.UiCallback());
            }

            @Override
            public void onCancel() {
                Log.d("Area", "Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("Area", "Error: " + exception.toString());
            }
        });



        if (isLoggedIn) {
            facebook.access_token = accessToken.getCurrentAccessToken().getToken();
            new AuthService(getContext()).facebook(facebook, new FacebookFrag.UiCallback());
        }

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public class UiCallback implements Callback<com.teamponey.teamponeay.area.Models.BasicResponse> {

        @Override
        public void success(com.teamponey.teamponeay.area.Models.BasicResponse answer, retrofit.client.Response response) {

            if (WidgetCreationActivity.newWidget.getAction() == -1) {
                WidgetCreationActivity.getInstance().showFragment(FRAGMENT_ACTION);
            } else {
                WidgetCreationActivity.getInstance().showFragment(FRAGMENT_REACTION);
            }

        }

        @Override
        public void failure(RetrofitError error) {
            Log.e("Area", error.toString());
        }
    }
}