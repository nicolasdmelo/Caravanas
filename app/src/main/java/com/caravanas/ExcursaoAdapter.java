package com.caravanas;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caravanas.Dominio.Entidades.Excursao;

import java.util.List;

public class ExcursaoAdapter extends RecyclerView.Adapter <ExcursaoAdapter.ViewHolderExcursao> {

    private List<Excursao> dados;

    public ExcursaoAdapter(List<Excursao> dados){
        this.dados = dados;
    }

    @Override
    public ExcursaoAdapter.ViewHolderExcursao onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.linha_excursao, parent, false);

        ViewHolderExcursao holderExcursao = new ViewHolderExcursao(view);

        return holderExcursao;
    }

    @Override
    public void onBindViewHolder(ExcursaoAdapter.ViewHolderExcursao holder, int position) {

        if((dados != null) && (dados.size() > 0)) {

            Excursao excursao = dados.get(position);

            holder.TxtNomeExcursao.setText(excursao.nome);
            holder.TxtLocalSaidaExcursao.setText(excursao.saidaLocal);
        }

    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderExcursao extends RecyclerView.ViewHolder{

        public TextView TxtNomeExcursao;
        public TextView TxtLocalSaidaExcursao;

        public ViewHolderExcursao(View itemView) {
            super(itemView);

            TxtNomeExcursao = (TextView) itemView.findViewById(R.id.TxtNomeExcursao);
            TxtLocalSaidaExcursao = (TextView) itemView.findViewById(R.id.TxtLocalSaidaExcursao);


        }
    }
}
