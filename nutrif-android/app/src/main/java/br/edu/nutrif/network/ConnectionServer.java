package br.edu.nutrif.network;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by juan on 14/03/16.
 */
public class ConnectionServer {
    private static final String URL_BASE = "";
    private static APIService service;
    private static ConnectionServer ourInstance = new ConnectionServer();

    public static ConnectionServer getInstance() {
        return ourInstance;
    }

    public static APIService getService() {
        return service;
    }

    private ConnectionServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);
    }
}
