package br.edu.nutrif.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.edu.nutrif.R;
import br.edu.nutrif.entitys.input.ConfirmationKey;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.ErrorUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ConfirmationActivity extends AppCompatActivity {
    @Bind(R.id.matricula)
    EditText matricula;
    @Bind(R.id.codigo)
    EditText codigo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        ButterKnife.bind(this);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null && bundle.getString("matricula") != null) {
            matricula.setText(bundle.getString("matricula"));
            codigo.requestFocus();
        }
    }

    public void confirmar(View v) {
        if (matricula.getText().toString().length() == 0) {
            matricula.setError(getString(R.string.invalido));
            return;
        }
        if (codigo.getText().toString().length() == 0) {
            codigo.setError(getString(R.string.invalido));
            return;
        }
        Call<Void> call = ConnectionServer.getInstance()
                .getService()
                .confirmar(
                        new ConfirmationKey(
                                matricula.getText().toString(),
                                codigo.getText().toString()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Response<Void> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    startActivity(new Intent(ConfirmationActivity.this, LoginActivity.class));
                    finish();
                } else {
                    AndroidUtil.showSnackbar(ConfirmationActivity.this,
                            ErrorUtils.parseError
                                    (response, retrofit, ConfirmationActivity.this).getMensagem());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                AndroidUtil.showSnackbar(ConfirmationActivity.this, R.string.erroconexao);
            }
        });
    }

}
