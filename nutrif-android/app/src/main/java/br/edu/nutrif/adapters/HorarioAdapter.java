package br.edu.nutrif.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import br.edu.nutrif.R;
import br.edu.nutrif.callback.CallbackButton;
import br.edu.nutrif.entitys.DiaRefeicao;

/**
 * Created by juan on 23/02/16.
 */
public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.RoomViewHolder> {
    private List<DiaRefeicao> refeicoes;
    private LayoutInflater inflater;
    private CallbackButton view;

    public HorarioAdapter(Context context, List<DiaRefeicao> myList, CallbackButton view) {
        this.refeicoes = myList;
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
    }

    @Override
    public int getItemCount() {
        return refeicoes.size();
    }


    public class RoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ativo;

        public RoomViewHolder(View item) {
            super(item);
            ativo = (ImageView) item.findViewById(R.id.ativo);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            view.onClickCallback(v,getAdapterPosition());
        }
    }
}