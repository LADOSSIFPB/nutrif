package br.edu.ladoss.nutrif;

import android.app.Activity;
import android.content.Context;

/**
 * Created by LADOSS on 21/06/2017.
 */

public interface MVPApp {

    interface Model {
        void onDestroy();
    }

    interface View {
        Context getContext();
        Context getAppContext();
        Activity get();
    }

    interface Presenter {
        void onDestroy();
    }

}
