package com.guuguo.learnsave.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.HashMap;


/**
 * Created by mimi on 2017-02-09.
 */

public class MyFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
    private Context mContext;

    public MyFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    public MyFragmentPagerAdapter(Context context, FragmentManager fm, Class[] fragments) {
        this(context, fm);
        this.fragments = fragments;
    }

    HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentHashMap.get(position);
        if (fragment == null) {
            return addFragment(position);
        } else {
            return fragment;
        }
    }

    private Fragment addFragment(int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragments[position].newInstance();
            fragmentHashMap.put(position, fragment);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    String[] strs = {"直播", "推荐", "追番", "分区", "关注", "发现"};

    public void setFragments(Class[] fragments) {
        this.fragments = fragments;
    }

    private Class[] fragments;

    @Override
    public CharSequence getPageTitle(int position) {
        return strs[position];
    }
}
