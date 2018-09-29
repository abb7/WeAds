package com.example.odatem.weadds;

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


public class MainActivity extends AppCompatActivity implements BeaconConsumer  {

    private static final String TAG = "BEACON_PROJECT";
    private ArrayList<String> beaconList;
    private ListView beaconListView;
    private ArrayAdapter<String> adapter;
    private BeaconManager beaconManager;

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
    String beaconID;
    Button button;
    Button button2;

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

        beaconID = new String();

        setContentView(R.layout.activity_main);
        this.beaconList = new ArrayList<String>();
        this.beaconListView = (ListView) findViewById(R.id.listView);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.beaconList);
        this.beaconListView.setAdapter(adapter);


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
                id.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                intereset.setVisibility(View.GONE);
                button.setVisibility(View.GONE);
                button2.setVisibility(View.VISIBLE);
                //beaconListView.setVisibility(View.VISIBLE);

                TV_id.setVisibility(View.GONE);
                TV_name.setVisibility(View.GONE);
                TV_intereset.setVisibility(View.GONE);



            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startBeacon();
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        this.beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    beaconList.clear();
                    for(Iterator<Beacon> iterator = beacons.iterator(); iterator.hasNext();) {
                        beaconID = iterator.next().getId1().toString();
                        beaconList.add(beaconID);
                        //Write To database_________________________________


                        //___________________________________________________
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        try {
            this.beaconManager.startRangingBeaconsInRegion(new Region("gimbal", Identifier.parse("075cdb40-5128-45e9-b680-4b3324fc4555"), null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void startBeacon(){
        //Beacon Code

        this.beaconManager = BeaconManager.getInstanceForApplication(this);
        this.beaconManager.getBeaconParsers().add(new BeaconParser(). setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        this.beaconManager.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                });
                builder.show();
            }
        }

        //End of beacon onCreate
    }


}
