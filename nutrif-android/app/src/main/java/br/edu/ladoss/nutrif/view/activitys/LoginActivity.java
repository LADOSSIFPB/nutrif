package br.edu.ladoss.nutrif.view.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.model.Aluno;
import br.edu.ladoss.nutrif.model.Pessoa;
import br.edu.ladoss.nutrif.model.output.Erro;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import br.edu.ladoss.nutrif.view.mvp.LoginMVP;
import br.edu.ladoss.nutrif.view.mvp.LoginPresenterImp;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;

public class LoginActivity extends AppCompatActivity implements LoginMVP.View{

    @Bind(R.id.informativo)
    TextView messages;

    private LoginMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        presenter = new LoginPresenterImp(this);

        ButterKnife.bind(this);

        presenter.doLogin(getIntent().getExtras());

    }

    @Override
    public void changeMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                String message = "Inicializando Aplicação...";
                switch (random.nextInt(5)) {
                    case 0:
                        message = getString(R.string.funnylogin1);
                        break;
                    case 1:
                        message = getString(R.string.funnylogin2);
                        break;
                    case 2:
                        message = getString(R.string.funnylogin3);
                        break;
                    case 3:
                        message = getString(R.string.funnylogin4);
                        break;
                    case 4:
                        message = getString(R.string.funnylogin5);
                        break;
                }
                messages.setText(message);
            }
        });
    }


    public void showMessage(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AndroidUtil.showToast(LoginActivity.this, message);
            }
        });
    }

    @Override
    public void showRefresh() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    public AppCompatActivity get() {
        return this;
    }
}
