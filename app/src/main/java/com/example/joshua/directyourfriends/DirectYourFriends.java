package com.example.joshua.directyourfriends;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DirectYourFriends extends AppCompatActivity {

    public Button SendButton;
    public TextView DirectionsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_your_friends);

        SendButton = (Button) findViewById(R.id.SendButton);


        //SendButton.setOnClickListener(this);

        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);
    }

    public void sendMessage()
    {
        //Send message through email
        Log.i("Send email", "");

        String[] TO = {"someone@gmail.com"};
        String[] CC = {"xyz@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DirectYourFriends.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.SendButton: sendMessage();
                Toast.makeText(DirectYourFriends.this, "Message Was Sent", Toast.LENGTH_LONG).show();
                finish();//go back to maps activity
                break;
        }
    }
}
