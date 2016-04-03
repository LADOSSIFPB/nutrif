package br.edu.nutrif.view;

import android.app.Application;

import br.edu.nutrif.network.ConnectionServer;

/**
 * Created by juan on 02/04/16.
 */
public class NutrifApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // ConnectionServer.getInstance();
    }
}
