package com.rg.rahul.weather;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rg.rahul.weather.model.TimeBaseData;
import com.rg.rahul.weather.model.WeatherData;
import com.rg.rahul.weather.rest.ApiClient;
import com.rg.rahul.weather.rest.ApiInterface;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class WeatherActivity extends AppCompatActivity implements LocationListener {
    SharedPreferences sharedPreferences;//=getSharedPreferences("com.weather",MODE_PRIVATE);
    private static final int LOCATION_REQUEST = 5;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    View view;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sharedPreferences=getSharedPreferences("com.weather",MODE_PRIVATE);
       /* toolbar.setVisibility(View.GONE);*/

        view=findViewById(R.id.main_content);
        findViewById(R.id.appbar).setVisibility(View.GONE);
        if(savedInstanceState==null){

            this.locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            //LocationListener locationListener = new MyLocationListener();
            locationOn();
           /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });*/
        }
    }


    private void locationOn() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST);
        } else {
            callWeatherApi();
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    Snackbar snackbar;

    private void callWeatherApi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        snackbar = Snackbar.make(view,"Retry to fetched weather", Snackbar.LENGTH_INDEFINITE);
        if(!isNetworkConnected()){
            snackbar.setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    locationOn();
                }
            }).show();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            // set title
            alertDialogBuilder.setTitle("Weather");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Please on Internet service")
                    .setCancelable(false)
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setNegativeButton("No",null);
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
            return;
        }
        else {
            if(snackbar!=null){
                snackbar.dismiss();
            }
        }
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            // set title
            alertDialogBuilder.setTitle("Weather");
            // set dialog message
            alertDialogBuilder
                    .setMessage("Location services is off Do you want to open?")
                    .setCancelable(false)
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),LOCATION_REQUEST);
                        }
                    }).setNegativeButton("No",null);
            AlertDialog alertDialog=alertDialogBuilder.create();
            alertDialog.show();
            return;
        }
        else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             lastKnownLocationGPS = lastKnownLocationGPS==null?locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER):lastKnownLocationGPS;
            if(lastKnownLocationGPS!=null){
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("lon",String.valueOf(lastKnownLocationGPS.getLongitude()));
                editor.putString("lat",String.valueOf(lastKnownLocationGPS.getLatitude()));
                getLocationAPI();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callWeatherApi();
        switch (requestCode) {
            case LOCATION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callWeatherApi();
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            this);
                    // set title
                    alertDialogBuilder.setTitle("Weather");
                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Your Application will not worked Do you want to exit")
                            .setCancelable(false)
                            .setPositiveButton("yes",null).setNegativeButton("No",null);
                    AlertDialog alertDialog=alertDialogBuilder.create();
                    alertDialog.show();
                }
                return;
            }
        }}

    @Override
    protected void onStart() {
        super.onStart();
    }

    public WeatherData weatherData;
    @Override
    public void onLocationChanged(Location location) {

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("lon",String.valueOf(location.getLongitude()));
        editor.putString("lat",String.valueOf(location.getLatitude()));
        editor.commit();
        getLocationAPI();
    }

    private void getLocationAPI() {
        double lat= Double.parseDouble(   sharedPreferences.getString("lat","-1"));
        double lon=   Double.parseDouble(  sharedPreferences.getString("lon","-1"));
        if((int)lat==-1 ||(int)lon==-1 ){
            return;
        }
        Toast.makeText(this,"Current Location\n Lon "+lon+ " Lat "+lat,Toast.LENGTH_LONG).show();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Map<String, Object> apiSe=new LinkedHashMap<>();
        apiSe.put("APPID","fa1f449d09228a99c5fcd7acec400135");

        apiSe.put("lon",lon);
        apiSe.put("lat",lat);
        Call<WeatherData> call = apiService.getWeather(apiSe);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData>call, Response<WeatherData> response) {
                WeatherData responseweatherData = response.body();
                Log.d("", "Weather" + response.toString());
                displayWeather(responseweatherData);
                //dayListGrid.setAdapter(gridViewAdapter);
            }
            @Override
            public void onFailure(Call<WeatherData>call, Throwable t) {
                // Log error here since request failed
                Log.e("", t.toString());
                snackbar= Snackbar.make(view,"Internal server error", Snackbar.LENGTH_INDEFINITE);
                if(!isNetworkConnected()){
                    snackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            locationOn();
                        }
                    }).show();
                }}
        });
    }

    private void displayWeather(WeatherData responseWeather) {
        weatherData=responseWeather;

        try {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),weatherData);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            mViewPager = (ViewPager) findViewById(R.id.container);

            TextView city= (TextView) findViewById(R.id.city);
            city.setText(weatherData.getCity().getName());

            LinearLayout dayListGrid= (LinearLayout) findViewById(R.id.dayListGrid);
            GridViewAdapter gridViewAdapter= null;
            List<TimeBaseData> asd=weatherData.getList();
            gridViewAdapter = new GridViewAdapter(WeatherActivity.this,asd,mViewPager,mSectionsPagerAdapter);

            gridViewAdapter.set(dayListGrid);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
       // Toast.makeText(this,"onStatusChanged"+provider,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
       // Toast.makeText(this,"onProviderEnabled"+provider,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(LOCATION_REQUEST==requestCode){
            locationOn();
        }
    }
}
