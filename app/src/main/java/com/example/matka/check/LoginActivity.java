package com.example.matka.check;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "Login Activity";

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView textview;
    private Button anonymousLogin;
    private Intent mainScreenIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        fbConnect();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


    public void fbConnect(){
        mainScreenIntent = new Intent(this,MainScreenActivity.class );
        loginButton =  (LoginButton)findViewById(R.id.fb_login_id);
        anonymousLogin = (Button)findViewById(R.id.anonymous_login);
        textview = (TextView)findViewById(R.id.text_view);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textview.setText("Success + \n" +
                        loginResult.getRecentlyGrantedPermissions() + "\n" );
                try{
                    startActivity(mainScreenIntent);
                }catch (Exception e){
                }
            }

            @Override
            public void onCancel() {
                textview.setText("cancel");
            }

            @Override
            public void onError(FacebookException error) {
                textview.setText("Error occured");
            }
        });
        anonymousLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    startActivity(mainScreenIntent);
                }catch (Exception e){
                    Log.i(TAG, e.getMessage());
                }
            }
        });
    }
}
