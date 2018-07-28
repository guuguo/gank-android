package com.guuguo.gank.app.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.guuguo.android.lib.app.LNBaseActivity;
import com.guuguo.android.lib.app.LNBaseFragment;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by mimi on 2017-02-09.
 */

public abstract class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private String[] strs;
    
    public MyFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    public MyFragmentPagerAdapter(Context context, FragmentManager fm, String[] strs) {
        this(context, fm);
        this.strs = strs;
    }

    HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    @Override
    public Fragment getItem(int position) {
       return initNewFragment(position, strs[position]);
    }


    protected abstract Fragment initNewFragment(int position, String title);

    @Override
    public int getCount() {
        return strs.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return strs[position];
    }
}
