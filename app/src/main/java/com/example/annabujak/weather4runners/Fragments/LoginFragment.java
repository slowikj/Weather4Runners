package com.example.annabujak.weather4runners.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.annabujak.weather4runners.Database.DBWeather4Runners;
import com.example.annabujak.weather4runners.Objects.User;
import com.example.annabujak.weather4runners.MainActivity;
import com.example.annabujak.weather4runners.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import static com.facebook.FacebookSdk.getApplicationContext;


public class LoginFragment extends Fragment {

    private TextView passOver;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private View fullView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(this.fullView == null) {
            this.fullView = createFullView(inflater, container);

            passOver = (TextView) this.fullView.findViewById(R.id.info);
            loginButton = (LoginButton) this.fullView.findViewById(R.id.login_button);
        }

        return this.fullView;
    }
    private View createFullView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.facebook_activity, container, false);
    }
    private void startMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    private void registerLogin(){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            mProfileTracker.stopTracking();
                          //  addUserData(profile2);
                            startMainActivity();
                        }
                    };
                }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}