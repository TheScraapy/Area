package com.teamponey.teamponeay.area;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.teamponey.teamponeay.area.Models.Auth;
import com.teamponey.teamponeay.area.Models.OauthModel;
import com.teamponey.teamponeay.area.Models.Response;
import com.teamponey.teamponeay.area.Services.AuthService;

import retrofit.Callback;
import retrofit.RetrofitError;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener{

    private static final int RC_SIGN_IN = 9001;

    private EditText etEmailAddress;
    private EditText etPassword;
    private EditText etIP;
    private Auth auth;

    private static String ACCESS_TOKEN_KEY = "access_token";

    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mStatusTextView = findViewById(R.id.tvError);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);

        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        etIP = findViewById(R.id.etIP);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

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

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setColorScheme(SignInButton.COLOR_LIGHT);

        Button bLogin = findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                auth = new Auth();
                auth.email = etEmailAddress.getText().toString();
                auth.password = etPassword.getText().toString();
                new AuthService(LoginActivity.this).login(auth, new LoginActivity.UiCallback());

            }
        });


        etIP.setText(Constants.server);

        etIP.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Constants.server = etIP.getText().toString();
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
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

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            OauthModel google = new OauthModel();

            google.access_token = account.getIdToken();
            Log.d("Area", "TOKEN: " + google.access_token);
            new AuthService(LoginActivity.this).google(google, new LoginActivity.UiCallback());

        } else {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
    }

    private class UiCallback implements Callback<Response> {

        @Override
        public void success(Response answer, retrofit.client.Response response) {

            Log.d("Area", "User is logged in success");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(ACCESS_TOKEN_KEY, answer.getToken());
            startActivity(intent);

        }

        @Override
        public void failure(RetrofitError error) {

            TextView tvError = findViewById(R.id.tvError);
            if (error == null)
                tvError.setText(getResources().getString(R.string.no_internet));
            else
                tvError.setText(getResources().getString(R.string.error, error));
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            default:
                Intent RegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(RegisterActivity);
                break;

        }
    }
}