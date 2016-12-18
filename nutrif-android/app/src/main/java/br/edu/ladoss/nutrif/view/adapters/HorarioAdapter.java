package br.edu.ladoss.nutrif.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ladoss.nutrif.R;
import br.edu.ladoss.nutrif.view.callback.RecycleButtonClicked;
import br.edu.ladoss.nutrif.model.DiaRefeicao;

/**
 * Created by juan on 23/02/16.
 */
public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.RoomViewHolder> {
    private List<DiaRefeicao> refeicoes;
    private LayoutInflater inflater;
    private RecycleButtonClicked view;

    public HorarioAdapter(Context context, List<DiaRefeicao> myList, RecycleButtonClicked view) {
        if (myList == null) {
            this.refeicoes = new ArrayList<>();
        } else {
            this.refeicoes = myList;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = view;
    }

    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.item_horario, parent, false);
        RoomViewHolder roomViewHolder = new RoomViewHolder(v);

        return roomViewHolder;
    }

    public void removeAll() {
        int tam = refeicoes.size();
        for (int i = tam - 1; i >= 0; i--) {
            refeicoes.remove(i);
        }
        if (tam > 0)
            notifyItemRangeRemoved(0, tam);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        String horarios = refeicoes.get(position).getRefeicao().getHoraInicio()
                .concat("/")
                .concat(refeicoes.get(position).getRefeicao().getHoraFinal());
        holder.natureza.setText(refeicoes.get(position).getRefeicao().getTipo());
        holder.horario.setText(horarios);
        holder.diadasemana.setText(refeicoes.get(position).getDia().getNome());
    }

    @Override
    public int getItemCount() {
        return refeicoes.size();
    }


    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView natureza, horario, diadasemana;

        public RoomViewHolder(View item) {
            super(item);
            natureza = (TextView) item.findViewById(R.id.natureza);
            horario = (TextView) item.findViewById(R.id.horario);
            diadasemana = (TextView) item.findViewById(R.id.diadasemana);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            view.onClickCallback(refeicoes.get(getAdapterPosition()));
        }
    }
}