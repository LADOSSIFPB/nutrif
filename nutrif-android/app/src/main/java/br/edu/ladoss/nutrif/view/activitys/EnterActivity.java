package br.edu.ladoss.nutrif.view.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.entitys.Aluno;
import br.edu.ladoss.nutrif.validation.Validate;
import butterknife.*;

public class EnterActivity extends AppCompatActivity {

    @Bind(R.id.identificadorEdit)
    EditText identificadorEditText;

    @Bind(R.id.passwordEdit)
    EditText senhaEditText;
    //TODO: colocar textwatcher para validar


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null && bundle.getString("email") != null) {
            identificadorEditText.setText(bundle.getString("email"));
            senhaEditText.requestFocus();
        }
    }

    public void login(View v) {

        boolean isValidated = isValidated();

        if (isValidated) {
            Aluno aluno = new Aluno();
            aluno.setEmail(identificadorEditText.getText().toString().trim());
            aluno.setSenha(senhaEditText.getText().toString());

            Intent intent = new Intent(this,LoginActivity.class);
            Bundle bundle = new Bundle();

            bundle.putString("email", aluno.getEmail());
            bundle.putString("senha", aluno.getSenha());
            intent.putExtras(bundle);

            startActivity(intent);
            finish();
        }
    }

    public void redirecionar(View v) {
        startActivity(new Intent(this, CadastroActivity.class));
    }

    private boolean isValidated() {

        boolean validated = true;

        String identificador = identificadorEditText.getText().toString();
        validated = Validate.identificador(identificador);
        if (!validated) {
            identificadorEditText.setError("E-mail inválidos.");
            identificadorEditText.setFocusable(true);
            identificadorEditText.requestFocus();
        }

        String senha = senhaEditText.getText().toString();
        validated = Validate.senha(senha);
        if (!validated) {
            senhaEditText.setError("Senha inválida.");
            senhaEditText.setFocusable(true);
            senhaEditText.requestFocus();
        }

        return validated;
    }

    public void autenticar(View view) {
        startActivity(new Intent(this, ConfirmationActivity.class));
    }

}