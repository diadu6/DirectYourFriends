package com.example.joshua.directyourfriends;

import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.Object.*;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;


public class DirectYourFriends extends AppCompatActivity implements OnClickListener{

    public Button SendButton;
    public TextView DirectionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_your_friends);

        Bundle extras = getIntent().getExtras();
        SendButton = (Button) findViewById(R.id.SendButton);
        String to = extras.getString("to");
        String from = extras.getString("from");



        //showDirections();

        //SendButton.setOnClickListener(this);

        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);
    }

    public void sendMessage() {
        //Send message through email
        Log.i("Send email", "");

        String[] TO = {"diadu6@yahoo.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            //Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DirectYourFriends.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SendButton:
                sendMessage();
                Toast.makeText(DirectYourFriends.this, "Message Was Sent", Toast.LENGTH_LONG).show();
                finish();//go back to maps activity
                break;
        }
    }


    public void showDirections() {


        //Output database
        //db inData = new db();


        //Parse text directions from Google
        //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?" + "saddr=location_start" + "daddr=location_end"));
        //intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        //startActivity(intent);

        //String instruction = Object.getString("html_instructions");
        //DirectionsTextView.setText(Html.fromHtml(instruction));

        /*



        List<Route> routes = mResults.getRoutes();
        Route mRoute = routes.get(0);


        // Get the list of directions from the Route object
        final List<RouteDirection> directions = mRoute.getRoutingDirections();
        String[] flatArray = new String[directions.size()];

        // Iterate through all of the directions and create formatted Strings
        for(int i = 0; i < directions.size(); i++) {
            RouteDirection direction = directions.get(i);
            flatArray[i] = String.format("%s%nTime: %.1f minutes, Length: %.1f miles", direction.getText(), direction.getMinutes(), direction.getLength()));
        }

        // Create an array adapter for the formatted directions Strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, flatArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
*/
    }
}

