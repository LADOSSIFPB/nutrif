package br.edu.nutrif.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.edu.nutrif.network.ConnectionServer;

public class ConnectionBroadcast extends BroadcastReceiver {
    public ConnectionBroadcast() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectionServer.getInstance().updateServiceAdress();
    }
}
