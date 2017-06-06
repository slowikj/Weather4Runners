package com.example.annabujak.weather4runners.Fragments;

import android.content.Intent;
import android.media.tv.TvInputService;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.facebook.share.Sharer;

import org.w3c.dom.Text;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by pawel.bujak on 14.05.2017.
 */

public class LoginFragment extends Fragment {

    private TextView passOver;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setHasOptionsMenu(true);
        setMenuVisibility(false);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.facebook_fragment, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(ProfileExists())
            CloseFragment();

        SetButtons(view);
        RegisterLogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void SetButtons(View parentView) {

        loginButton = (LoginButton) parentView.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        passOver = (TextView)parentView.findViewById(R.id.info);
    }

    private void RegisterLogin(){
        callbackManager = CallbackManager.Factory.create();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            private ProfileTracker mProfileTracker;
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    //Toast.makeText(getContext(), loginResult.getAccessToken().getUserId(), Toast.LENGTH_LONG).show();
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            mProfileTracker.stopTracking();
                            ((MainActivity)getActivity()).AddUser(profile2.getFirstName(),profile2.getLastName());
                            Toast.makeText(getContext(), profile2.getFirstName(), Toast.LENGTH_LONG).show();
                            CloseFragment();
                        }
                    };
                }
            }

            @Override
            public void onCancel() {
                // App code
                    showAlert();
            }

            @Override
            public void onError(FacebookException exception) {
                    showAlert();
            }

            private void showAlert() {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Canceled")
                        .setMessage("Canceled")
                        .setPositiveButton("ok", null)
                        .show();
            }

        });

        passOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseFragment();
            }
        });

    }
    private void CloseFragment() {
        ((MainActivity)getActivity()).StartPagerFragment();
    }
    private boolean ProfileExists(){
        Profile profile = Profile.getCurrentProfile();
        if (profile != null)
            return true;
        return false;
    }
}
