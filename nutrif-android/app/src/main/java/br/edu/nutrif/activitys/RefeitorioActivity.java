package br.edu.nutrif.activitys;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import br.edu.nutrif.R;

import br.edu.nutrif.adapters.HorarioAdapter;
import br.edu.nutrif.callback.CallbackButton;
import br.edu.nutrif.database.dao.AlunoDAO;
import br.edu.nutrif.entitys.DiaRefeicao;
import br.edu.nutrif.network.ConnectionServer;
import br.edu.nutrif.util.AndroidUtil;
import br.edu.nutrif.util.PreferencesUtils;
import butterknife.*;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RefeitorioActivity extends AppCompatActivity implements CallbackButton {
    @Bind(R.id.loadinglayout)
    LinearLayout loadLayout;

    @Bind(R.id.recycle)
    RecyclerView recycle;

    List<DiaRefeicao> refeicoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refeitorio);
        ButterKnife.bind(this);

        String matricula = AlunoDAO.getInstance(this).find().getMatricula();
        String auth = PreferencesUtils.getAccessKeyOnSharedPreferences(this);
        Call<List<DiaRefeicao>> call = ConnectionServer.getInstance().getService().listaRefeicoes(auth, matricula);
        call.enqueue(new Callback<List<DiaRefeicao>>() {
            @Override
            public void onResponse(Response<List<DiaRefeicao>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    RefeitorioActivity.this.montaTabela(response.body());
                } else {
                    AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.impossivelcarregar);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AndroidUtil.showSnackbar(RefeitorioActivity.this, R.string.impossivelcarregar);
                loadLayout.setVisibility(View.GONE);
            }
        });
    }

    public void montaTabela(List<DiaRefeicao> refeicoes) {
        loadLayout.setVisibility(View.GONE);
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recycle.setLayoutManager(gridLayoutManager);
        recycle.setAdapter(new HorarioAdapter(this, refeicoes, this));

    }

    public void habilitaBotao() {

    }

    @Override
    public void onClickCallback(View view, int position) {

    }
}
