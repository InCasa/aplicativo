package com.incasa.incasa.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private String[] TitulosTab;

    public FragmentPagerAdapter(FragmentManager fm, String[] TitulosTab) {
        super(fm);
        this.TitulosTab = TitulosTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentArduino();
            case 1:
                return new FragmentCelular();
            case 2:
                return new  FragmentServidor();
            case 3:
                return new FragmentRele();
            case 4:
                return new FragmentSensor();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.TitulosTab[position];
    }

    @Override
    public int getCount() {
        return this.TitulosTab.length;
    }
}
