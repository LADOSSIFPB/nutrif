package br.edu.ladoss.nutrif.view.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.view.activitys.EnterActivity;
import br.edu.ladoss.nutrif.view.activitys.LoginActivity;

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
        return model.downloadPhoto(aluno);
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public void changeMessage(String message) {
        view.changeMessage(message);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        model = null;
        view = null;
    }

    @Override
    public void doLogin(Bundle extra) {

        if (extra != null && !extra.isEmpty()) {
            Aluno aluno = new Aluno();
            aluno.setEmail(extra.getString("email"));
            aluno.setSenha(extra.getString("validaSenha"));

            if (!(aluno.getEmail() == null)){
                model.doLogin(aluno, extra);
                return;
            }
        }

        model.redirectLogin(extra);
    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void showMessage(String message) {
        view.showMessage(message);
    }

    @Override
    public void finishActivity() {
        AppCompatActivity activity = (AppCompatActivity) view;
        activity.finish();
    }
}
