package com.arjun.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView info;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info=(TextView)findViewById(R.id.info);

        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        final Geocoder geocoder= new Geocoder(getApplicationContext(), Locale.getDefault());

        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {//When the phone moves


                try {
                    List<Address> listAddresses= geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);

                    if(listAddresses!=null && listAddresses.size()>0) {


                        String information="";
                        if(Double.toString(listAddresses.get(0).getLatitude()) != null)
                            information+= "Latitude: "+listAddresses.get(0).getLatitude() +"\n";
                        if(Double.toString(listAddresses.get(0).getLongitude()) != null)
                            information+= "Longitude: "+listAddresses.get(0).getLongitude() +"\n";
                        if(Double.toString(location.getAltitude()) != null)
                            information+= "Altitude: "+location.getAltitude() +"\n";
                        if(Float.toString(location.getAccuracy()) != null)
                            information+= "Accuracy: "+location.getAccuracy() +"\n\n";
                        if(listAddresses.get(0).getThoroughfare() != null)
                            information+="Address: "+listAddresses.get(0).getThoroughfare()+"\n\t";
                        if(listAddresses.get(0).getLocality() != null)
                            information+=listAddresses.get(0).getLocality()+" - ";
                        if(listAddresses.get(0).getPostalCode() != null)
                            information+=listAddresses.get(0).getPostalCode();


                        info.setText(information);
                    }


                }
                catch (IOException e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {//When GPS enabled or disabled

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);


            try {
                List<Address> listAddresses= geocoder.getFromLocation(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude(), 1);

                if(listAddresses!=null && listAddresses.size()>0) {


                    String information="";
                    if(Double.toString(listAddresses.get(0).getLatitude()) != null)
                        information+= "Latitude: "+listAddresses.get(0).getLatitude() +"\n";
                    if(Double.toString(listAddresses.get(0).getLongitude()) != null)
                        information+= "Longitude: "+listAddresses.get(0).getLongitude() +"\n";
                    if(Double.toString(lastKnownLocation.getAltitude()) != null)
                        information+= "Altitude: "+lastKnownLocation.getAltitude() +"\n";
                    if(Float.toString(lastKnownLocation.getAccuracy()) != null)
                        information+= "Accuracy: "+lastKnownLocation.getAccuracy() +"\n\n";
                    if(listAddresses.get(0).getThoroughfare() != null)
                        information+="Address: "+listAddresses.get(0).getThoroughfare()+"\n\t";
                    if(listAddresses.get(0).getLocality() != null)
                        information+=listAddresses.get(0).getLocality()+" - ";
                    if(listAddresses.get(0).getPostalCode() != null)
                        information+=listAddresses.get(0).getPostalCode();



                    info.setText(information);
                }


            }
            catch (IOException e) {

                e.printStackTrace();
            }


            }

        }

    }
