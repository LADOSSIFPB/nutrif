package br.edu.ladoss.nutrif.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.model.output.Erro;
import retrofit.Converter;
import retrofit.Response;
import retrofit.Retrofit;

public class ErrorUtils {

    public static Erro parseError(Response<?> response, Retrofit retrofit, Context context) {
        Converter<ResponseBody, Erro> converter =
                retrofit.responseConverter(Erro.class, new Annotation[0]);
        Erro error;
        try {
                error = converter.convert(response.errorBody());
            if(error == null){
                error = new Erro(0, context.getString(R.string.undefinedError));
            }
        } catch (Exception e) {
            error = new Erro(0, context.getString(R.string.undefinedError));
        }
        return error;
    }

    public static Erro parseError(Response<?> response, Context context){
        Erro erro = null;
        try {
            String json = response.errorBody().string();
            erro = new GsonBuilder().create().fromJson(json,Erro.class);
            if(erro == null){
                erro = new Erro(0, context.getString(R.string.undefinedError));
            }
        } catch (Exception e) {
            Log.e(context.getString(R.string.app_name), e.getMessage());
            erro = new Erro(0, context.getString(R.string.undefinedError));
        }
        return erro;
    }

}
