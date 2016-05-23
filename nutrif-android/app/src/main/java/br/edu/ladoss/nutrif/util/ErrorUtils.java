package br.edu.ladoss.nutrif.util;

import android.content.Context;

import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.entitys.output.Erro;
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

}
