package br.edu.ladoss.nutrif.view.mvp;

import br.edu.ladoss.nutrif.model.Aluno;

/**
 * Created by LADOSS on 21/06/2017.
 */

public class LoginPresenterImp implements LoginMVP.Presenter{

    private LoginMVP.View view;
    private LoginMVP.Model model;

    public LoginPresenterImp(LoginMVP.View view) {
        this.view = view;
        this.model = new LoginModelImp(this);
    }

    @Override
    public Aluno retrieveFromDB() {
        return model.retrieveFromDB(view.getContext());
    }

    @Override
    public Aluno downloadPhoto(Aluno aluno) {
        return model.downloadPhoto(aluno, view.getContext());
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        model = null;
        view = null;
    }
}
