package br.edu.nutrif.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import br.edu.nutrif.R;
import br.edu.nutrif.entitys.ConfirmationKey;
import br.edu.nutrif.network.ConnectionServer;
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
                    startActivity(new Intent(ConfirmationActivity.this, RefeitorioActivity.class));
                    finish();
                } else {
                    Snackbar.make(
                            ConfirmationActivity.this.findViewById(android.R.id.content),
                            ErrorUtils.parseError(response, retrofit, ConfirmationActivity.this).getMensagem(),
                            Snackbar.LENGTH_LONG)
                            .show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Snackbar.make(
                        ConfirmationActivity.this.findViewById(android.R.id.content),
                        ConfirmationActivity.this.getString(R.string.erroconexao),
                        Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

}
