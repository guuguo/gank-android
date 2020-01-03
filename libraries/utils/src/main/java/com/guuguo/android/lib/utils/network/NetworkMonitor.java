package com.guuguo.android.lib.utils.network;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhukkun on 1/23/17.
 */
public class NetworkMonitor {

    Timer timer;
    TimerTask task;

    NetworkListener listener;

    NetWorkUtils.NetworkType currentNetworkType = NetWorkUtils.NetworkType.NETWORK_UNKNOWN;
    boolean currentConnected = false;

    public NetworkMonitor(NetworkListener networkListener) {
        this.listener = networkListener;
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                NetWorkUtils.NetworkType newType = NetWorkUtils.getNetworkType();
                boolean newConnection = NetWorkUtils.isConnected(true);
                if (listener != null) {
                    listener.onNetworkChanged(NetworkMonitor.this, newConnection, newType);
                }
                currentConnected = newConnection;
                currentNetworkType = newType;
            }
        };
    }

    /**
     * 开始监听网络, 需要手动调用stop()结束监听
     */
    public void startMonitor(long period) {
        if (period != 0) {
            timer.schedule(task, 0, period);
        } else {
            timer.schedule(task, 0, 1000);
        }
    }


    /**
     * 开始监听网络, 持续duration时间后结束,若未关闭监听器,则执行endingRunnable
     *
     * @param duration
     * @param endingRunnable
     */
    public void startMonitorDuration(long duration, final Runnable endingRunnable) {
        timer.schedule(task, 0, 1000);

        TimerTask finishTask = new TimerTask() {
            @Override
            public void run() {
                if (!canceled) {
                    endingRunnable.run();
                    stop();
                }
            }
        };
        timer.schedule(finishTask, duration);
    }

    boolean canceled = false;

    /**
     * 结束监听网络
     */
    public void stop() {
        task.cancel();
        timer.cancel();
        canceled = true;
    }

    public interface NetworkListener {

        void onNetworkChanged(NetworkMonitor monitor, boolean connected, NetWorkUtils.NetworkType newType);
    }

}
