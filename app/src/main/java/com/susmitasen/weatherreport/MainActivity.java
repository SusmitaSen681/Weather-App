package com.susmitasen.weatherreport;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    final String API = "0b6be43643d69a47fa8e466fe65a0803";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    final long Min_time = 5000;
    final float Min_distance = 1000;
    final int request_code = 101;


    String location_provider = LocationManager.GPS_PROVIDER;
    TextView NameCity, WeatherState, Temperature,Humidity,WindSpeed,Pressure,Rain,Mintemp,Maxtemp,Feelslike;
    ImageView iconWeather;
    RelativeLayout mCityFind;

    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NameCity = (TextView) findViewById(R.id.cityName);
        WeatherState = (TextView) findViewById(R.id.weatherCondition);
        Temperature = (TextView) findViewById(R.id.temperature);
        iconWeather = (ImageView) findViewById(R.id.weatherIcon);
        mCityFind = (RelativeLayout) findViewById(R.id.findCity);
        Humidity=(TextView)findViewById(R.id.humidity);
        WindSpeed=(TextView)findViewById(R.id.windSpread);
        Pressure=(TextView)findViewById(R.id.Pressure);

        Rain=(TextView)findViewById(R.id.rain) ;
        Mintemp=(TextView)findViewById(R.id.mintemp);
        Maxtemp=(TextView)findViewById(R.id.maxtemp);
        Feelslike=(TextView)findViewById(R.id.feels);

        mCityFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Cityfind.class);
                startActivity(intent);
            }
        });


    }

   /* @Override
    protected void onResume() {
        super.onResume();
        getWeatherlocation();
    }*/
   @Override
   protected void onResume() {
       super.onResume();
       Intent intent=getIntent();
       String city=intent.getStringExtra("City");
       if(city!=null)
       {
           getWeathernewCity(city);
       }
       else {
           getWeatherlocation();
       }


   }

   private  void getWeathernewCity(String city)
   {
       RequestParams params=new RequestParams();
       params.put("q",city);
       params.put("appid",API);
       networking(params);
   }


    private void getWeatherlocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());
              RequestParams params=new RequestParams();
              params.put("lat",Latitude);
              params.put("lon",Longitude);
              params.put("appid",API);
              networking(params);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                //not able to get location
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request_code);
            return;
        }
        locationManager.requestLocationUpdates(location_provider, Min_time, Min_distance, locationListener);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==request_code)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MainActivity.this,"Locationget Succesfully",Toast.LENGTH_SHORT).show();
                getWeatherlocation();
            }
            else {
                //user denied
            }
        }
    }
    private void networking(RequestParams params)
    {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(MainActivity.this,"Data Get Success",Toast.LENGTH_SHORT).show();
                weatherData weatherD=weatherData.fromJson(response);
                updateUI(weatherD);
               // super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
               // super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }
    public  void updateUI(weatherData weather)
    {
        Temperature.setText(weather.getmTemperature());
        NameCity.setText(weather.getmCity());
        Humidity.setText(weather.getmHumidity());
        WeatherState.setText(weather.getmWeatherType());
        WindSpeed.setText(weather.getmWindspeed());
        Pressure.setText(weather.getmPressure());

        Rain.setText(weather.getmRain());
        Mintemp.setText(weather.getmMintemp());
        Maxtemp.setText(weather.getmMaxtemp());
        Feelslike.setText(weather.getmRealfeel());
        int resourceID=getResources().getIdentifier(weather.getmIcon(),"drawable",getPackageName());
        iconWeather.setImageResource(resourceID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!=null)
        {
            locationManager.removeUpdates(locationListener);
        }
    }
}