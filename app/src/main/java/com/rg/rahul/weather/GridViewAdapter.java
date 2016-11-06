package com.rg.rahul.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rg.rahul.weather.model.DateBaseData;
import com.rg.rahul.weather.model.TimeBaseData;
import com.rg.rahul.weather.rest.ApiClient;
import com.rg.rahul.weather.rest.ApiInterface;
import com.rg.rahul.weather.rest.StaticImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rahul on 04-11-2016.
 */
public class GridViewAdapter {

    private final WeatherActivity weatherActivity;
    private final List<TimeBaseData> dateWiseList;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    public GridViewAdapter(WeatherActivity weatherActivity, List<TimeBaseData> dateWiseList, ViewPager mViewPager, SectionsPagerAdapter mSectionsPagerAdapter) {
        this.weatherActivity = weatherActivity;
        this.dateWiseList = dateWiseList;
        this.mViewPager = mViewPager;
        this.mSectionsPagerAdapter = mSectionsPagerAdapter;
    }


    public void set(LinearLayout dayListGrid) throws ParseException {
        SimpleDateFormat defaultFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat showedFormat=new SimpleDateFormat("E");
        SimpleDateFormat showedTimeFormat=new SimpleDateFormat("hh:mm a");
        for (int i=0;i<dateWiseList.size();i++  ) {
            TimeBaseData dateWise=dateWiseList.get(i);

            Date date=defaultFormat.parse(dateWise.getDt_txt());

            LayoutInflater inflator = weatherActivity.getLayoutInflater();
            View view=   inflator.inflate(R.layout.time_status,null);
            TextView max_temp = (TextView) view.findViewById(R.id.max_temp);

            TextView min_temp = (TextView) view.findViewById(R.id.min_temp);

            final String url=dateWise.getWeather().getIcon()+".png";

            final ImageView weater_icon = (ImageView) view.findViewById(R.id.weater_icon);
            if(StaticImage.stringBitmapHashMap.get(url)!=null){
                weater_icon.setImageBitmap(StaticImage.stringBitmapHashMap.get(url));
            }else {
                final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseBody> call = apiService.fetchCaptcha(dateWise.getWeather().getIcon());
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

            TextView day = (TextView) view.findViewById(R.id.day);
            TextView time = (TextView) view.findViewById(R.id.time);
            time.setText(showedTimeFormat.format(date));
            day.setText(showedFormat.format(date));

            max_temp.setText(PlaceholderFragment.decimalConverter(dateWise.getMain().getTemp()-273.15)+" \u2103");
            view.setTag(i);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mViewPager.setCurrentItem((Integer) v.getTag());
                  //  mSectionsPagerAdapter.notifyDataSetChanged();


                }
            });
            dayListGrid.addView(view);

        }
    }

}
