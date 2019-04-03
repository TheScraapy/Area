package com.teamponey.teamponeay.area.Fragments.Oauth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.teamponey.teamponeay.area.Constants;
import com.teamponey.teamponeay.area.LoginActivity;
import com.teamponey.teamponeay.area.Models.Auth;
import com.teamponey.teamponeay.area.Models.BasicResponse;
import com.teamponey.teamponeay.area.Models.OauthModel;
import com.teamponey.teamponeay.area.R;
import com.teamponey.teamponeay.area.RegisterActivity;
import com.teamponey.teamponeay.area.Services.AuthService;
import com.teamponey.teamponeay.area.Services.WidgetService;
import com.teamponey.teamponeay.area.WidgetCreationActivity;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.teamponey.teamponeay.area.Constants.RC_SIGN_IN;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_ACTION;
import static com.teamponey.teamponeay.area.WidgetCreationActivity.FRAGMENT_REACTION;

public class GmailFrag extends Fragment {
    public static GmailFrag newInstance() {
        return (new GmailFrag());
    }

    View view;
    private GoogleSignInClient mGoogleSignInClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_gmail, container, false);

        view.findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestScopes(new Scope("https://www.googleapis.com/auth/gmail.send"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/calendar.events"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/youtube"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/userinfo.email"))
                .requestScopes(new Scope("https://www.googleapis.com/auth/userinfo.profile"))
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        SignInButton signInButton = view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        updateUI(account);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(@NonNull Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("Area", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            OauthModel google = new OauthModel();

            google.access_token = account.getIdToken();

            new AuthService(getContext()).gmail(google, new GmailFrag.UiCallback());
        }
    }

    public class UiCallback implements Callback<BasicResponse> {

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
