package google.com.fabquiz.Internet;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import google.com.fabquiz.Nougat.NetworkSchedulerService;

/**
 * Created by Pratik on 2/24/2018.
 */
public class ConnectionReceiver extends BroadcastReceiver {
    public static ConnectionReceiverListener connectionReceiverListener;
    public ConnectionReceiver() {
        super();
    }
    public ConnectionReceiver(ConnectionReceiverListener networkSchedulerService) {
        connectionReceiverListener= networkSchedulerService;
    }
    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnected();

        if (connectionReceiverListener != null) {
                connectionReceiverListener.onNetworkConnectionChanged(isConnected);
            }


    }
    public static boolean isConnected2(Context context) {
        ConnectivityManager
                cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnected();
    }
    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) TestApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnected();
    }
    public interface ConnectionReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);

        void onClick(View v);
    }
}