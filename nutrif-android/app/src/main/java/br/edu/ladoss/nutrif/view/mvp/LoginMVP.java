package br.edu.ladoss.nutrif.view.mvp;

import android.content.Context;

import br.edu.ladoss.nutrif.MVPApp;
import br.edu.ladoss.nutrif.model.Aluno;

/**
 * Created by LADOSS on 21/06/2017.
 */

public interface LoginMVP {

    interface Model extends MVPApp.Model {
        Aluno retrieveFromDB(Context context);
        Aluno downloadPhoto(Aluno aluno, Context context);
    }

    interface View extends MVPApp.View {
        void showMessage(final String message);
    }

    interface Presenter extends MVPApp.Presenter {
        Aluno retrieveFromDB();
        Aluno downloadPhoto(Aluno aluno);
    }

}
