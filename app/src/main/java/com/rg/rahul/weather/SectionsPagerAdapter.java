package com.rg.rahul.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rg.rahul.weather.model.WeatherData;

/**
 * Created by Rahul on 03-11-2016.
 */
/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final WeatherData weatherData;

    public SectionsPagerAdapter(FragmentManager fm, WeatherData weatherData) {
        super(fm);
        this.weatherData=weatherData;

    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position,weatherData);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return weatherData.getList().size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
            case 2:
                return "SECTION 3";
            default: return  "SESION N";

        }

    }
}
