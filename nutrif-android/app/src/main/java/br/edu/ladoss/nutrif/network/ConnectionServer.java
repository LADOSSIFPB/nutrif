package br.edu.ladoss.nutrif.network;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by juan on 14/03/16.
 */
public class ConnectionServer {

    private static final String URL_BASE = "http://ladoss.com.br:8773/NutrIF_Service_homologacao/";
    private static APIService service;
    private static ConnectionServer ourInstance = new ConnectionServer();

    public static ConnectionServer getInstance() {
        return ourInstance;
    }

    public APIService getService() {
        return service;
    }

    private ConnectionServer() {
        updateServiceAdress();
    }

    public void updateServiceAdress() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);

        Response<Void> response = null;
        try {
            response = service.status().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
