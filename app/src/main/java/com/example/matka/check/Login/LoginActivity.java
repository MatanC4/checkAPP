package com.example.matka.check.Login;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.matka.check.MainScreen.MainScreenActivity;
import com.example.matka.check.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import bl.controlers.AppManager;
import bl.entities.UserInfo;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "Login Activity";

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView textview;
    private Button anonymousLogin;
    private Intent mainScreenIntent;
    private String city = "Earth";
    private String country="Earth";
    private String name = "Anonynous";
    private int age;
    private AppManager manager;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //dummyWriteToFireBase();
        this.requestWindowFeature(getWindow().FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        manager = AppManager.getInstance(this);
        mainScreenIntent = new Intent(this, MainScreenActivity.class);
        UserInfo info = manager.getUserInformation(this);
        Log.v("USER_INFO", info.toString());
        //dummyReadFromFireBase();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        if(!info.isAnonymous()) {
            startNextActivity();
            finish();
        }

        else{
            fbConnect();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void dummyWriteToFireBase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference();
        DatabaseReference postsRef = mDatabase.child("msg");
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue("Hello, Universe!");
    }

    private void dummyReadFromFireBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //mDatabase = database.getReference("msg");
        Query q = database.getReference("msg").limitToFirst(3);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String s = child.getValue(String.class);
                    Log.d("Reading:", s);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

    public void fbConnect() {
        loginButton = (LoginButton) findViewById(R.id.fb_login_id);
        anonymousLogin = (Button) findViewById(R.id.anonymous_login);
        textview = (TextView) findViewById(R.id.text_view);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends", "user_location"));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                textview.setText("Success + \n" +
                        loginResult.getRecentlyGrantedPermissions() + "\n");
                        GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {
                                    String birthday = object.getString("birthday");
                                    JSONObject location = (JSONObject)object.get("location");
                                    LoginActivity.this.name = object.get("name").toString();
                                    String locationDetails  = location.getString("name");
                                    String[] localDetails = locationDetails.split(", ");
                                    LoginActivity.this.city = localDetails[0];
                                    if(localDetails.length>0)
                                        LoginActivity.this.country = localDetails[localDetails.length-1];
                                    int year;
                                    try{
                                        year = Integer.parseInt(birthday.substring(birthday.length() - 4));
                                    }
                                    catch(Exception e){
                                        year = 2000;
                                    }
                                    LoginActivity.this.age = Calendar.getInstance().get(Calendar.YEAR) - year;
                                    Log.d("FACEBOOK","name: " + name + " ,BirthDay: " + birthday + " City: "+ localDetails[0] + " Country: "+ localDetails[localDetails.length-1] + " ,Age: " + age);
                                    LoginActivity.this.saveUserData();
                                } catch (JSONException e) {
                                    Log.d("FACEBOOK","Failed");
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,location");
                request.setParameters(parameters);
                request.executeAsync();
                startNextActivity();
                finish();
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
                String country = LoginActivity.this.getResources().getConfiguration().locale.getCountry();
                LoginActivity.this.manager.saveAnonymousUserInformation(LoginActivity.this, country);
                startNextActivity();
                finish();
            }
        });
    }

    public void startNextActivity(){
        try {
            startActivity(mainScreenIntent);
        } catch (Exception e) {
            Log.i(TAG, e.getMessage());
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Login Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void saveUserData(){
        manager.saveLoggedUserInformation(this,name,age,city,country);
    }
}
