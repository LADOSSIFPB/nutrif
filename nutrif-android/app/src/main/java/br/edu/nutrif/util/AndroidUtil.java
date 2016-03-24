package br.edu.nutrif.util;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;

import br.edu.nutrif.R;

public class AndroidUtil {

    public static void showSnackbar(Activity activity, String msg){
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.snack));
        snackbar.show();
    }


    public static void showSnackbar(Activity activity, int msg){
        showSnackbar(activity, activity.getApplicationContext().getString(msg));
    }

}