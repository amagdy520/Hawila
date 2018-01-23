package com.hawila.hawila.Adapter;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.hawila.hawila.Fragments.Background1;
import com.hawila.hawila.Fragments.Background10;
import com.hawila.hawila.Fragments.Background2;
import com.hawila.hawila.Fragments.Background3;
import com.hawila.hawila.Fragments.Background4;
import com.hawila.hawila.Fragments.Background5;
import com.hawila.hawila.Fragments.Background6;
import com.hawila.hawila.Fragments.Background7;
import com.hawila.hawila.Fragments.Background8;
import com.hawila.hawila.Fragments.Background9;

/**
 * Created by Ahmed Magdy on 9/12/2017.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return Background1.create();
            case 1:
                return Background2.create();
            case 2:
                return Background3.create();
            case 3:
                return Background4.create();
            case 4:
                return Background5.create();
            case 5:
                return Background6.create();
            case 6:
                return Background7.create();
            case 7:
                return Background8.create();
            case 8:
                return Background9.create();
            case 9:
                return Background10.create();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
