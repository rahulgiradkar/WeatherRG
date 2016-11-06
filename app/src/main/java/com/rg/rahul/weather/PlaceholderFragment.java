package com.rg.rahul.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.rg.rahul.weather.model.TimeBaseData;
import com.rg.rahul.weather.model.WeatherData;
import com.rg.rahul.weather.rest.ApiClient;
import com.rg.rahul.weather.rest.ApiInterface;
import com.rg.rahul.weather.rest.StaticImage;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.angle;
import static android.R.attr.pivotX;
import static android.R.attr.pivotY;

/**
 * Created by Rahul on 03-11-2016.
 */
public  class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static WeatherData weatherData;
   /// private int poision=-1;
    //  private TimeBaseData listData;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber, WeatherData weatherData) {
        PlaceholderFragment.weatherData = weatherData;
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        TextView   min_temp= (TextView) rootView.findViewById(R.id.min_temp);
        TextView   max_temp= (TextView) rootView.findViewById(R.id.max_temp);
        TextView   default_pressure= (TextView) rootView.findViewById(R.id.default_pressure);
        TextView   default_sea_level= (TextView) rootView.findViewById(R.id.default_sea_level);
        TextView   default_grnd_level= (TextView) rootView.findViewById(R.id.default_grnd_level);
        TextView   hummidity= (TextView) rootView.findViewById(R.id.hummidity);
        TextView   main_weather= (TextView) rootView.findViewById(R.id.main_weather);
        TextView   description_weather= (TextView) rootView.findViewById(R.id.description_weather);
        TextView   deg_wind= (TextView) rootView.findViewById(R.id.deg_wind);
        TextView   speed_wind= (TextView) rootView.findViewById(R.id.speed_wind);
        TextView   all_cloud= (TextView) rootView.findViewById(R.id.all_cloud);
        TextView dt_tx= (TextView) getActivity().findViewById(R.id.dt_tx);
        TextView default_temp=(TextView) rootView.findViewById(R.id.default_temp);
        final ImageView weater_icon= (ImageView) rootView.findViewById(R.id.weater_icon);
        int poision = getArguments().getInt(ARG_SECTION_NUMBER, -1);
        Log.d("poision",poision+"");
        final TimeBaseData listData= weatherData.getList().get(poision);;
        String defaultTemp=decimalConverter(listData.getMain().getTemp()-273.15);
        String displayDefaultTemp=("00"+defaultTemp).substring(defaultTemp.length(),defaultTemp.length()+2);
        default_temp.setText(displayDefaultTemp+" \u2103");
        min_temp.setText("L:"+decimalConverter(listData.getMain().getTemp_min()-273.15)+" \u2103");
        max_temp.setText("H:"+decimalConverter(listData.getMain().getTemp_max()-273.15)+" \u2103");
        default_pressure.setText(decimalConverter(listData.getMain().getPressure()*0.030));
        default_sea_level.setText(decimalConverter(listData.getMain().getSea_level()*0.030));
        default_grnd_level.setText(decimalConverter(listData.getMain().getGrnd_level()*0.030));

        hummidity.setText(decimalConverter(listData.getMain().getHumidity())+"%");
        main_weather.setText(listData.getWeather().getMain());
        description_weather.setText(listData.getWeather().getDescription());


       /* main_weather_activity.setText(listData.getWeather().getMain());
        description_weather_activity.setText(listData.getWeather().getDescription());*/

        // weater_icon.setImageURI(Uri.parse("http://openweathermap.org/img/w/"+listData.getWeather().getIcon()));

        deg_wind.setText(listData.getWind().getDeg()+" \u00B0");
        ImageView deg_wind_image = (ImageView) rootView.findViewById(R.id.deg_wind_image);

        final RotateAnimation rotateAnim = new RotateAnimation(0.0f, (float) listData.getWind().getDeg(),
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(1);
        rotateAnim.setFillAfter(true);
        deg_wind_image.startAnimation(rotateAnim);

        speed_wind.setText(decimalConverter(listData.getWind().getSpeed())+"m/s");
        all_cloud.setText(listData.getClouds().getAll()+"%");
        SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {

            Date date=defaultFormat.parse(listData.getDt_txt());
            SimpleDateFormat displayFormat=new SimpleDateFormat("MMM dd, yyyy\nhh:mm a");
            dt_tx.setText(displayFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String url=listData.getWeather().getIcon()+".png";

        if( StaticImage.stringBitmapHashMap.get(url)!=null){
            //To reduce Api hit
            // Dont download image
            weater_icon.setImageBitmap(StaticImage.stringBitmapHashMap.get(url));
        }else {

            final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiService.fetchCaptcha(listData.getWeather().getIcon());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                            //listData.getWeather().setBitmapIcon(bm);
                            weater_icon.setImageBitmap(bm);
                            StaticImage .stringBitmapHashMap.put(url,bm);
                        } else {
                            // TODO
                        }
                    } else {
                        // TODO
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(url, t.toString());
                }
            });
        }
        return rootView;
    }

    public static String decimalConverter(double v) {
        return new DecimalFormat("##").format(v);
    }
}
