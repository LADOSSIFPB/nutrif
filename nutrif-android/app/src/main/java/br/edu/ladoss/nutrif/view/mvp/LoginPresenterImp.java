package br.edu.ladoss.nutrif.view.mvp;

import android.content.Context;
import android.os.Bundle;

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

    public Aluno retrieveFromDB() {
        return model.retrieveFromDB();
    }

    public Aluno downloadPhoto(Aluno aluno) throws Throwable {
        return model.downloadPhoto(aluno.getId());
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        model = null;
        view = null;
    }

    @Override
    public void doLogin(Bundle extra) {

    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void doAnimation() {

    }
}
