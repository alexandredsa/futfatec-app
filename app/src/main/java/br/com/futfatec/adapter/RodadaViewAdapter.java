package br.com.futfatec.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.futfatec.R;
import br.com.futfatec.activity.PartidaActivity;
import br.com.futfatec.model.rodada.Etapa;
import br.com.futfatec.model.rodada.Partida;
import br.com.futfatec.model.rodada.Rodada;

/**
 * Created by alexa on 28/05/2016.
 */
public class RodadaViewAdapter extends RecyclerView.Adapter<RodadaViewAdapter.RodadaViewHolder> {
    private List<Rodada> jogos;
    private Context mContext;

    public RodadaViewAdapter(List<Rodada> jogos, Context mContext) {
        this.jogos = jogos;
        this.mContext = mContext;
    }

    @Override
    public RodadaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rodada_dia, parent, false);
        RodadaViewHolder rodadaViewHolder = new RodadaViewHolder(view);
        return rodadaViewHolder;
    }

    @Override
    public void onBindViewHolder(RodadaViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Rodada rodada = this.jogos.get(position);

        if(rodada.getEtapa() == Etapa.GRUPO)
            holder.txtRodadaId.setText(String.format(mContext.getString(R.string.format_text_numero_rodada), position + 1));
        else {
            holder.txtRodadaId.setText(rodada.getEtapa().getDescricao());
        }
        for (Partida partida : rodada.getPartidas()) {
            View v = holder.generateViewPartida(mContext);
            v.setTag(R.id.VIEW_KEY_PARTIDA, partida);
            v.setTag(R.id.VIEW_KEY_RODADA_ID, rodada.getId());
            TextView txtTimeA = (TextView) v.findViewById(R.id.txtTimeA);
            TextView golsTimeA = (TextView) v.findViewById(R.id.txtGolsTimeA);
            TextView txtTimeB = (TextView) v.findViewById(R.id.txtTimeB);
            TextView golsTimeB = (TextView) v.findViewById(R.id.txtGolsTimeB);
            TextView txtHoraJogo = (TextView) v.findViewById(R.id.txtHoraInicio);
            txtHoraJogo.setText(partida.getHoraInicio());
            txtTimeA.setText(partida.getTimeA().getNome());
            txtTimeB.setText(partida.getTimeB().getNome());
            golsTimeA.setText(String.valueOf(partida.getTimeA().getGols()));
            golsTimeB.setText(String.valueOf(partida.getTimeB().getGols()));

            v.setOnClickListener(new PartidaClickListener());
        }

    }

    @Override
    public int getItemCount() {
        return jogos.size();
    }


    public class RodadaViewHolder extends RecyclerView.ViewHolder {
        TextView txtRodadaId;
        LinearLayout layoutHolder;

        public RodadaViewHolder(View itemView) {
            super(itemView);

            txtRodadaId = (TextView) itemView.findViewById(R.id.txtIdRodada);
            layoutHolder = (LinearLayout) itemView.findViewById(R.id.layoutPartidaHolder);
        }


        public View generateViewPartida(Context mContext) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newView = layoutInflater.inflate(R.layout.item_rodada_partida, null);
            layoutHolder.addView(newView);
            return newView;
        }
    }


    class PartidaClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Partida partida = (Partida) v.getTag(R.id.VIEW_KEY_PARTIDA);
            String rodadaId = (String) v.getTag(R.id.VIEW_KEY_RODADA_ID);
            Intent i = new Intent(mContext, PartidaActivity.class);
            i.putExtra(PartidaActivity.KEY_HORA_INICIO_PARTIDA, partida.getHoraInicio());
            i.putExtra(PartidaActivity.KEY_RODADA_ID, rodadaId);
            mContext.startActivity(i);
        }
    }
}
