package br.edu.ladoss.nutrif.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import br.edu.ladoss.nutrif.network.ConnectionServer;

public class ConnectionBroadcast extends BroadcastReceiver {
    public ConnectionBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected)
            context.startService(new Intent(context, ReconnectService.class));
    }
}
