package com.teamponey.teamponeay.area;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.teamponey.teamponeay.area.Models.Auth;
import com.teamponey.teamponeay.area.Models.Response;
import com.teamponey.teamponeay.area.Services.AuthService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.teamponey.teamponeay.area.Constants.RC_SIGN_IN;
import static java.security.AccessController.getContext;

public class RegisterActivity extends AppCompatActivity {

    Spinner spinnerLanguage;

    private Auth auth;
    private TextView tvError;
    private EditText etEmailAddress;
    private EditText etPassword;

    private static String ACCESS_TOKEN_KEY = "access_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmailAddress = findViewById(R.id.etEmailAddress);
        etPassword = findViewById(R.id.etPassword);

        final List spinnerList = new ArrayList();
        spinnerList.add(getResources().getString(R.string.lang_en));
        spinnerList.add(getResources().getString(R.string.lang_fr));

        spinnerLanguage = this.findViewById(R.id.spinnerWindow);
        spinnerLanguage.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        ArrayAdapter windowAdapter = new ArrayAdapter(this, R.layout.spinner_item, spinnerList);

        windowAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerLanguage.setAdapter(windowAdapter);

        Button bRegister = findViewById(R.id.bLogin);
        bRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                auth = new Auth();
                auth.email = etEmailAddress.getText().toString();
                auth.password = etPassword.getText().toString();
                new AuthService(RegisterActivity.this).register(auth, new RegisterActivity.UiCallback());
            }
        });
    }

    private class UiCallback implements Callback<Response> {

        @Override
        public void success(Response answer, retrofit.client.Response response) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra(ACCESS_TOKEN_KEY, answer.getToken());
            startActivity(intent);
        }

        @Override
        public void failure(RetrofitError error) {
            tvError = findViewById(R.id.tvError);

            if (error == null)
                tvError.setText(getResources().getString(R.string.no_internet));
            else
                tvError.setText(getResources().getString(R.string.error, error));
        }
    }


}