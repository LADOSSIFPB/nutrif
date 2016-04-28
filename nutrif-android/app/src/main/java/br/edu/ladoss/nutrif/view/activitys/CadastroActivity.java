package br.edu.ladoss.nutrif.view.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.controller.PessoaController;
import br.edu.ladoss.nutrif.controller.Replyable;
import br.edu.ladoss.nutrif.entitys.Aluno;
import br.edu.ladoss.nutrif.entitys.output.Erro;
import br.edu.ladoss.nutrif.util.AndroidUtil;
import br.edu.ladoss.nutrif.util.ValidateUtil;
import butterknife.Bind;
import butterknife.ButterKnife;


public class CadastroActivity extends AppCompatActivity implements Replyable<Aluno> {
    @Bind(R.id.email)
    EditText email;
    @Bind(R.id.password)
    EditText senha;
    @Bind(R.id.matricula)
    EditText matricula;
    @Bind(R.id.carregando)
    LinearLayout carregarLayout;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.photo)
    Button photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setLogo(R.drawable.ic_action_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void tirarFoto(View v){
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, 1);
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo.setBackground(new BitmapDrawable(getResources(),imageBitmap));
            photo.setText("");
        }
    }


    public void registrar(View v) {
        change(false);
        if (!validate()) {
            PessoaController.cadastrar(new Aluno(
                            matricula.getText().toString(),
                            email.getText().toString(),
                            senha.getText().toString()),
                    this, this);
        }
    }

    public boolean validate() {
        if (ValidateUtil.validateField(email.getText().toString(), ValidateUtil.EMAIL) != ValidateUtil.OK) {
            email.setError(this.getString(R.string.invalido));
            return true;
        }
        if (ValidateUtil.validateField(senha.getText().toString(), ValidateUtil.SENHA) != ValidateUtil.OK) {
            senha.setError(this.getString(R.string.invalido));
            return true;
        }
        if (ValidateUtil.validateField(matricula.getText().toString(), ValidateUtil.MATRICULA) != ValidateUtil.OK) {
            matricula.setError(this.getString(R.string.invalido));
            return true;
        }
        return false;
    }

    public void change(final boolean ativo) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                carregarLayout.setVisibility(ativo ? View.GONE : View.VISIBLE);
                content.setVisibility(!ativo ? View.GONE : View.VISIBLE);
            }
        });
    }

    @Override
    public void onSuccess(Aluno aluno) {
        Bundle bundle = new Bundle();
        bundle.putString("matricula", matricula.getText().toString());
        bundle.putString("email", email.getText().toString());
        Intent intent = new Intent(CadastroActivity.this, ConfirmationActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailure(Erro erro) {
        AndroidUtil.showSnackbar(CadastroActivity.this, erro.getMensagem());
        change(true);
    }

    @Override
    public void failCommunication(Throwable throwable) {
        AndroidUtil.showSnackbar(CadastroActivity.this, R.string.erroconexao);
        change(true);
    }
}

