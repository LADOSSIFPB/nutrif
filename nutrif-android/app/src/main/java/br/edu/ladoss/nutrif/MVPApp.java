package br.edu.ladoss.nutrif;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by LADOSS on 21/06/2017.
 */

public interface MVPApp {

    interface Model {
    }

    interface View {
        Context getContext();
        Context getAppContext();
        AppCompatActivity get();
    }

    interface Presenter {
        void onDestroy();
    }

}
