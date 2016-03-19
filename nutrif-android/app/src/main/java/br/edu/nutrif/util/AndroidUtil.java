package br.edu.nutrif.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;

public class AndroidUtil {

    public static void showSnackbar(Activity activity, String msg){
        Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).show();
    }


    public static void showSnackbar(Activity activity, int msg){
        showSnackbar(activity, activity.getApplicationContext().getString(msg));
    }

}