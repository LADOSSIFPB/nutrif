package br.edu.ladoss.nutrif.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Random;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.view.mvp.LoginMVP;

/**
 * Created by Rennan on 16/08/17.
 */

public class LoginAnimationAsyncTask extends AsyncTask<Void, Void, String> {

    private LoginMVP.Presenter presenter;
    private Context context;

    public LoginAnimationAsyncTask(LoginMVP.Presenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        Random random = new Random();

        String message = "";
        switch (random.nextInt(5)) {
            case 0:
                message = context.getString(R.string.funnylogin1);
                break;
            case 1:
                message = context.getString(R.string.funnylogin2);
                break;
            case 2:
                message = context.getString(R.string.funnylogin3);
                break;
            case 3:
                message = context.getString(R.string.funnylogin4);
                break;
            case 4:
                message = context.getString(R.string.funnylogin5);
                break;
        }
        return message;
    }

    @Override
    protected void onPostExecute(String msg) {
        presenter.changeMessage(msg);
    }

}
