package br.edu.nutrif.view.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import br.edu.nutrif.R;
import br.edu.nutrif.view.adapters.HorarioAdapter;
import br.edu.nutrif.view.callback.CallbackButton;
import br.edu.nutrif.controller.DiaRefeicaoController;
import br.edu.nutrif.controller.Replyable;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.entitys.output.Erro;
import br.edu.nutrif.util.AndroidUtil;
import butterknife.Bind;
import butterknife.ButterKnife;

public class RefeitorioActivity extends AppCompatActivity implements CallbackButton {
    @Bind(R.id.loadinglayout)
    LinearLayout loadLayout;

    @Bind(R.id.content)
    RelativeLayout content;

    @Bind(R.id.recycle)
    RecyclerView recycle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeitorio);
        ButterKnife.bind(this);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        DiaRefeicaoController.gerarHorario(this, new Replyable<List<DiaRefeicao>>() {
            @Override
            public void onSuccess(List<DiaRefeicao> diaRefeicaos) {
                montaTabela(diaRefeicaos);
            }

            @Override
            public void onFailure(Erro erro) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, erro.getMensagem());
            }

            @Override
            public void failCommunication(Throwable throwable) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.impossivelcarregar);
                montaTabela(DiaRefeicaoController.refeicoes);
            }
        });
    }

    public void montaTabela(List<DiaRefeicao> refeicoes) {
        loadLayout.setVisibility(View.GONE);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(gridLayoutManager);
        recycle.setAdapter(new HorarioAdapter(this, refeicoes, this));
        if (recycle.getAdapter().getItemCount() == 0) {
            TextView textView = new TextView(this);
            textView.setText(R.string.nodays);
            content.addView(textView);
        }

    }

    @Override
    public void onClickCallback(View view, int position) {
        Intent intent = new Intent(this, RefeicaoActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }


}
