package br.edu.ladoss.nutrif.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.edu.ladoss.nutrif.R;

public class AndroidUtil {

    public static void showSnackbar(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.snack));
        snackbar.show();
    }

    public static void showSnackbar(Activity activity, int msg) {
        showSnackbar(activity, activity.getApplicationContext().getString(msg));
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int msg) {
        showToast(context, context.getString(msg));
    }

    public static String convertLongToString(Long l) {
        Date date = new Date(l);
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM");
        String dateText = df2.format(date);
        return dateText;
    }

    public static Long parseStringToLong(String dataPretensao){
        java.text.SimpleDateFormat dateformate = new SimpleDateFormat("dd/MM");
        try {
            return dateformate.parse(dataPretensao).getTime();
        } catch (ParseException e) {
            Log.w("parseStringToLong","erro ao converter string");
            return null;
        }
    }
}