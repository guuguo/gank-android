package com.guuguo.android.lib.utils.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhukkun on 3/16/17.
 */
public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {

    //10S内网络是否刚发生过变化
    boolean justNetworkChanged = false;
    //是否启动了倒计时发送器
    boolean hasSchedule = false;
    Timer timer = null;
    TimerTask task = null;
    NetworkChangeCallBack mCallback;
    Handler handler;
    NetWorkUtils.NetworkType networkType;


    public interface NetworkChangeCallBack{
        void onNetworkChanged(boolean connected, NetWorkUtils.NetworkType type);
    }

    public NetworkChangeBroadcastReceiver(NetworkChangeCallBack callBack){
        this.mCallback = callBack;
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("uploadtest", "onReceive");
        if(timer == null) {
            timer = new Timer();
        }

        justNetworkChanged = true;

        if (task != null) {
            task.cancel();
            hasSchedule = false;
        }

        if (!hasSchedule) {
            task = new TimerTask() {
                @Override
                public void run() {
                    justNetworkChanged = false;
                    hasSchedule = false;
                }
            };
            if (timer != null) {
                timer.schedule(task, 10000);
                hasSchedule = true;
            }
        }
        if(mCallback!=null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    networkType = NetWorkUtils.getNetworkType();
                    final boolean newConnection = NetWorkUtils.isConnected(true);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mCallback.onNetworkChanged(newConnection, networkType);
                        }
                    });
                }
            }).start();
        }
    }

    public void registReceiver(Context context){
        context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void unregist(Context context){
        try {
            context.unregisterReceiver(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean justNetworkChanged(){
        return  justNetworkChanged;
    }

}
