package br.edu.nutrif.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.edu.nutrif.R;
import br.edu.nutrif.entitys.Aluno;
import br.edu.nutrif.entitys.Pessoa;
import br.edu.nutrif.network.ConnectionServer;
import butterknife.*;
import retrofit.Call;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.emailEdit)
    EditText email;
    @Bind(R.id.passwordEdit)
    EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    public void login(View v){
        if(!validate()){
            Call<Aluno> call = ConnectionServer
                    .getInstance()
                    .getService()
                    .inserir(new Aluno());
        }
    }

    public void redirecionar(View v){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    public boolean validate(){
        if(email.getText().length() > Pessoa.LENGHT_MAX_EMAIL ||
                email.getText().length() < Pessoa.LENGHT_MIN_EMAIL ||
                email.getText().toString().matches("^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÍÏÓÔÕÖÚÇÑ ]+$")){
            email.setError(this.getString(R.string.invalido));
            return true;
        }
        if(senha.getText().length() > Pessoa.LENGHT_MAX_SENHA ||
                senha.getText().length() < Pessoa.LENGHT_MIN_SENHA) {
            senha.setError(this.getString(R.string.invalido));
            return true;
        }
        return false;
    }

    public void autenticar(View view){
        startActivity(new Intent(this, ConfirmationActivity.class));
    }
}
