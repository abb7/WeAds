package com.example.odatem.weadds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.altbeacon.beacon.*;
import java.util.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity  {



    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users").push();
    DatabaseReference myRef2 = database.getReference("Screens");

    User user;
    EditText id;
    EditText name;
    EditText intereset;
    TextView TV_id;
    TextView TV_name;
    TextView TV_intereset;
    TextView TV_uuid;

    Button button;
    Button button2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id=(EditText)findViewById(R.id.ETuserID);
        name=(EditText)findViewById(R.id.ETname);
        intereset=(EditText)findViewById(R.id.ETinterest);

        TV_id=(TextView) findViewById(R.id.TVid);
        TV_name=(TextView) findViewById(R.id.TVname);
        TV_intereset=(TextView) findViewById(R.id.TVintereset);

        button=(Button)findViewById(R.id.button_id);
        button2=(Button)findViewById(R.id.button_id1);
        intent = new Intent(this, BeaconList.class);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                user = new User(id.getText().toString(),name.getText().toString(),  intereset.getText().toString());
                myRef.setValue(user);
                Toast.makeText(getApplicationContext(),"Thank you for registering!",Toast.LENGTH_LONG).show();


                // ------------ This is a test for update commands -----------

                 //myRef2.child("Screen2").child("userID").setValue(id.getText().toString().trim());


                //-------------------------------------------------------------
//                id.setVisibility(View.GONE);
//                name.setVisibility(View.GONE);
//                intereset.setVisibility(View.GONE);
//                button.setVisibility(View.GONE);
//                button2.setVisibility(View.VISIBLE);
//                //beaconListView.setVisibility(View.VISIBLE);
//
//                TV_id.setVisibility(View.GONE);
//                TV_name.setVisibility(View.GONE);
//                TV_intereset.setVisibility(View.GONE);

                Intent i = new Intent(MainActivity.this, BeaconList.class);
                startActivity(i);




            }
        });


    }



}
