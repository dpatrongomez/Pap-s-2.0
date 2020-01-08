package es.dpatrongomez.papas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyConnectivityChecker {

    public static boolean isConnected(Context context) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = null;
        NetworkInfo mobile = null;
        if (connectivityManager != null) {
            wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        }
        if (wifi != null && mobile != null) {
            connected = (wifi.isAvailable() && wifi.isConnectedOrConnecting() || (mobile.isAvailable() && mobile.isConnectedOrConnecting()));
        }
        return connected;
    }
}