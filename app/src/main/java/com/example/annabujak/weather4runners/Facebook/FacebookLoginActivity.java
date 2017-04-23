package com.example.annabujak.weather4runners.Facebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.annabujak.weather4runners.Database.DBWeather4Runners;
import com.example.annabujak.weather4runners.Database.User;
import com.example.annabujak.weather4runners.MainActivity;
import com.example.annabujak.weather4runners.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class FacebookLoginActivity extends AppCompatActivity {
    private TextView passOver;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    DBWeather4Runners database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.facebook_activity);
        database = new DBWeather4Runners(this);

        passOver = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        if(profilExists())
            startMainActivity();
        registerLogin();
    }
    private boolean profilExists(){
        Profile profile = Profile.getCurrentProfile();
        if (profile != null)
            return true;
        return false;
    }
    private void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    private void addUserData(){
        Profile profile = Profile.getCurrentProfile();
        database.addUser(new User(profile.getFirstName(),profile.getLastName()));
        database.close();
    }
    private void registerLogin(){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                database.clearUser();
                addUserData();
                startMainActivity();
            }

            @Override
            public void onCancel(){
            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        passOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}