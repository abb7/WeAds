package com.example.odatem.weadds;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class BeaconList extends AppCompatActivity implements BeaconConsumer {

    String sessionId;

    private static final String TAG = "BEACON_PROJECT";
    private ArrayList<String> beaconList;
    private ListView beaconListView;
    private ArrayAdapter<String> adapter;
    private BeaconManager beaconManager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef2 = database.getReference("Screens");

    String beaconID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beacon_list);

        sessionId= getIntent().getStringExtra("USER_ID");

        beaconID = new String();

        this.beaconList = new ArrayList<String>();
        this.beaconListView = (ListView) findViewById(R.id.listView);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.beaconList);
        this.beaconListView.setAdapter(adapter);

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

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.beaconManager.unbind(this);
    }

//    @Override
//    public void onBeaconServiceConnect() {
//        this.beaconManager.setRangeNotifier(new RangeNotifier() {
//            @Override
//            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
//                if (beacons.size() > 0) {
//                    beaconList.clear();
//                    for(Iterator<Beacon> iterator = beacons.iterator(); iterator.hasNext();) {
//                        beaconID = iterator.next().getId1().toString();
//                        beaconList.add(beaconID);
//
//                        //Write To database_________________________________
//
//
//                        myRef2.child("Screen2").child("userID").setValue(sessionId);
//
//
//                        //___________________________________________________
//                    }
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            adapter.notifyDataSetChanged();
//                        }
//                    });
//                }
//            }
//        });
//        try {
//            this.beaconManager.startRangingBeaconsInRegion(new Region("users", Identifier.parse("075cdb40-5128-45e9-b680-4b3324fc4555"), null, null));
//
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    public void onBeaconServiceConnect() {
        this.beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    for(Iterator<Beacon> iterator = beacons.iterator(); iterator.hasNext();) {
//                        beaconID = iterator.next().getId1().toString();
//                        beaconList.add(beaconID);



                        Beacon beacon = iterator.next();
                        if(!beaconList.contains(beacon.getId1().toString())) {
                            //Write To database_________________________________


                            myRef2.child("Screen1").child("userID").setValue(sessionId);


                            //___________________________________________________
                            beaconList.add(beacon.getId1().toString());
                        }
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
            this.beaconManager.startRangingBeaconsInRegion(new Region("users", Identifier.parse("075cdb40-5128-45e9-b680-4b3324fc4555"), null, null));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}
