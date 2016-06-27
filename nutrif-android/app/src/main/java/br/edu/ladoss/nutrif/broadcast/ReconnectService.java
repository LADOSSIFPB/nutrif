package br.edu.ladoss.nutrif.broadcast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import br.edu.ladoss.nutrif.network.ConnectionServer;

public class ReconnectService extends Service {
    public ReconnectService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ConnectionServer.getInstance().updateServiceAdress();
            }
        }).start();
        return 1;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
