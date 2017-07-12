package br.edu.ladoss.nutrif.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.database.dao.AlunoDAO;
import br.edu.ladoss.nutrif.database.dao.DiaRefeicaoDAO;
import br.edu.ladoss.nutrif.model.DiaRefeicao;
import br.edu.ladoss.nutrif.model.output.Erro;
import br.edu.ladoss.nutrif.network.ConnectionServer;
import br.edu.ladoss.nutrif.util.ErrorUtils;
import br.edu.ladoss.nutrif.util.PreferencesUtils;
import br.edu.ladoss.nutrif.view.activitys.RefeicaoActivity;
import br.edu.ladoss.nutrif.view.adapters.HorarioAdapter;
import br.edu.ladoss.nutrif.view.callback.MessagingCallback;
import br.edu.ladoss.nutrif.view.callback.RecycleButtonClicked;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;

public class ListMealsFragment extends Fragment {

    private MessagingCallback activity;

    @Bind(R.id.recycle)
    public RecyclerView recycle;

    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swipe;

    public static ListMealsFragment getInstance(MessagingCallback activity) {
        ListMealsFragment fragment = new ListMealsFragment();
        fragment.activity = activity;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meals, null);
        ButterKnife.bind(this, view);
        swipe.setColorSchemeColors(getResources().getColor(R.color.accent));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        swipe.requestDisallowInterceptTouchEvent(true);
                        doNewRequisition();
                        swipe.requestDisallowInterceptTouchEvent(false);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipe.setRefreshing(false);
                            }
                        });

                    }
                }).start();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!doLocalTable()){
                    doNewRequisition();
                }
            }
        }).start();
        return view;
    }

    //Chama serviço para recuperar as refeições
    private boolean doNewRequisition() {
        String auth = PreferencesUtils.getAccessKeyOnSharedPreferences(getContext());
        String matricula = AlunoDAO.getInstance(getActivity().getBaseContext()).find().getMatricula();

        Call<List<DiaRefeicao>> call = ConnectionServer.getInstance().getService().listaRefeicoes(matricula);

        Response<List<DiaRefeicao>> response = null;
        try {
            response = call.execute();
            if (response.isSuccess()) {
                addListDiaRefeicao(response.body());
                montaTabela(response.body());
            } else {
                Erro erro = ErrorUtils.parseError(response, getContext());
                activity.showMessage(erro.getMensagem());
                return false;
            }
        } catch (IOException e) {
            activity.showMessage(getString(R.string.erroconexao));
            return false;
        }

        return true;
    }

    //Método que recupera as refeições localmente
    private boolean doLocalTable(){
        DiaRefeicaoDAO diaRefeicaoDAO = new DiaRefeicaoDAO(getContext());

        List<DiaRefeicao> list = diaRefeicaoDAO.findAll();

        if(list == null)
            return false;
        else{
            montaTabela(list);
            return true;
        }

    }

    private void montaTabela(final List<DiaRefeicao> refeicoes) {
        final Context context = getActivity().getBaseContext();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recycle.setLayoutManager(gridLayoutManager);
                recycle.setAdapter(new HorarioAdapter(context, refeicoes, new RecycleButtonClicked() {
                    @Override
                    public void onClickCallback(DiaRefeicao refeicao) {
                        Intent intent = new Intent(context, RefeicaoActivity.class);
                        intent.putExtra("refeicao", refeicao);
                        startActivity(intent);
                    }
                }));

                if (recycle.getAdapter().getItemCount() == 0) {
                    recycle.setVisibility(View.GONE);
                    TextView textView = new TextView(ListMealsFragment.this.getContext());
                    textView.setText(R.string.nodays);
                    textView.setGravity(Gravity.CENTER);
                } else
                    recycle.setVisibility(View.VISIBLE);
            }
        });


    }

    private void addListDiaRefeicao(List<DiaRefeicao> list){
        DiaRefeicaoDAO dao = new DiaRefeicaoDAO(getActivity().getBaseContext());

        dao.deleteAll();

        for(DiaRefeicao dia: list)
            dao.insert(dia);
    }

}
