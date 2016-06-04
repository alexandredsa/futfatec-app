package br.com.futfatec.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.futfatec.R;
import br.com.futfatec.adapter.enums.DrawableSide;
import br.com.futfatec.model.rodada.Evento;
import br.com.futfatec.model.rodada.Partida;

/**
 * Created by alexa on 04/06/2016.
 */
public class PartidaViewAdapter extends RecyclerView.Adapter<PartidaViewAdapter.PartidaViewHolder> {

    private final Context mContext;
    private final Partida partida;
    private DrawableSide drawableSize;

    public PartidaViewAdapter(Partida partida, Context mContext) {
        this.partida = partida;
        this.mContext = mContext;
    }

    @Override
    public PartidaViewAdapter.PartidaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partida_evento, parent, false);
        PartidaViewHolder partidaViewHolder = new PartidaViewHolder(view);
        return partidaViewHolder;
    }

    @Override
    public void onBindViewHolder(PartidaViewAdapter.PartidaViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Evento evento = partida.getEventos().get(position);
        holder.txtEventoJogador.setText(evento.getJogador().getNome());
        DrawableSide size = getDrawableSize(evento);

        switch (evento.getTipo()) {
            case GOL:
                setDrawable(size, R.drawable.ic_evento_gol, holder);
                break;
            case CARTAO_AMARELO:
                setDrawable(size, R.drawable.ic_evento_cartao_amarelo, holder);
                break;
            case CARTAO_VERMELHO:
                setDrawable(size, R.drawable.ic_evento_cartao_vermelho, holder);
                break;
        }

    }

    private void setDrawable(DrawableSide size, int drawable, PartidaViewHolder holder) {
        if(size == DrawableSide.LEFT){
            holder.txtEventoJogador.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
            holder.layoutEventoJogador.setGravity(Gravity.RIGHT);
            holder.txtEventoJogador.setGravity(Gravity.LEFT);
        }else{
            holder.txtEventoJogador.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0);
            holder.layoutEventoJogador.setGravity(Gravity.LEFT);
            holder.txtEventoJogador.setGravity(Gravity.RIGHT);
        }
    }

    @Override
    public int getItemCount() {
        return partida.getEventos().size();
    }

    public DrawableSide getDrawableSize(Evento evento) {
        if (evento.getJogador().getTime().equalsIgnoreCase(partida.getTimeB().getNome()))
            return DrawableSide.LEFT;

        return DrawableSide.RIGHT;
    }

    class PartidaViewHolder extends RecyclerView.ViewHolder {
        TextView txtEventoJogador;
        LinearLayout layoutEventoJogador;

        public PartidaViewHolder(View itemView) {
            super(itemView);
            txtEventoJogador = (TextView) itemView.findViewById(R.id.txtEventoJogador);
            layoutEventoJogador = (LinearLayout) itemView.findViewById(R.id.layoutEventoJogador);
        }
    }
}
