package br.edu.nutrif.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by juan on 14/03/16.
 */
public class ConnectionServer {

    private static final String[] URL_BASES =
            {"http://192.168.56.1:8080/NutrIF_Sevice/",
                    "http://ladoss.com.br:8773/NutrIF_Sevice/",
                    "http://192.168.56.1:8773/NutrIF_Sevice/"};
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

        for (String endereco : URL_BASES) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(endereco)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(APIService.class);

            try {
                Response<Void> response = service.status().execute();
                if (response.isSuccess())
                    break;
            } catch (IOException e) {
                continue;
            }
        }
    }
}
