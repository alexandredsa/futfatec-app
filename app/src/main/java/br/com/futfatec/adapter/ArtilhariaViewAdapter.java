package br.com.futfatec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.futfatec.R;
import br.com.futfatec.model.artilharia.Jogador;

/**
 * Created by alexa on 29/05/2016.
 */
public class ArtilhariaViewAdapter extends RecyclerView.Adapter<ArtilhariaViewAdapter.ArtilhariaViewHolder> {
    private List<Jogador> jogadores;
    private Context mContext;
    private int colocacao = 1;

    public ArtilhariaViewAdapter(List<Jogador> jogadores, Context mContext) {
        this.mContext = mContext;
        this.jogadores = jogadores;
        Collections.sort(jogadores);
    }

    @Override
    public ArtilhariaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artilharia_jogador, parent, false);
        ArtilhariaViewHolder artilhariaViewHolder = new ArtilhariaViewHolder(view);
        return artilhariaViewHolder;
    }

    @Override
    public void onBindViewHolder(ArtilhariaViewHolder holder, int position) {
        Jogador jogador = jogadores.get(position);
        compareColocacaoJogadorAnterior(jogadores, position);

        holder.txtColocacao.setText(String.format(mContext.getString(R.string.format_text_artilharia_posicao),colocacao));
        holder.txtJogador.setText(jogador.getNome());
        holder.txtGols.setText(String.format(mContext.getString(
                jogador.getGols() > 1 ? R.string.format_text_artilharia_gols : R.string.format_text_artilharia_gol)
                , jogador.getGols()));
        holder.txtTime.setText(String.valueOf(jogador.getTime()));


    }

    private void compareColocacaoJogadorAnterior(List<Jogador> jogadores, int position) {
        if (position == 0)
            return;

        if (jogadores.get(position).getGols() < jogadores.get(position - 1).getGols())
            ++colocacao;
    }

    @Override
    public int getItemCount() {
        return this.jogadores.size();
    }


    public class ArtilhariaViewHolder extends RecyclerView.ViewHolder {
        TextView txtColocacao, txtJogador, txtTime, txtGols;

        public ArtilhariaViewHolder(View itemView) {
            super(itemView);

            txtColocacao = (TextView) itemView.findViewById(R.id.txtPosicao);
            txtJogador = (TextView) itemView.findViewById(R.id.txtNome);
            txtTime = (TextView) itemView.findViewById(R.id.txtTime);
            txtGols = (TextView) itemView.findViewById(R.id.txtGols);
        }
    }
}
