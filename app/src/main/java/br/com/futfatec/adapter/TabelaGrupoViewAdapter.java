package br.com.futfatec.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.futfatec.R;
import br.com.futfatec.model.classificacao.Grupo;
import br.com.futfatec.model.classificacao.Tabela;
import br.com.futfatec.model.classificacao.Time;

/**
 * Created by alexa on 28/05/2016.
 */
public class TabelaGrupoViewAdapter extends RecyclerView.Adapter<TabelaGrupoViewAdapter.TabelaViewHolder> {

    private Tabela tabela;
    private Context mContext;

    public TabelaGrupoViewAdapter(Tabela tabela, Context mContext) {
        this.mContext = mContext;
        this.tabela = tabela;
    }

    @Override
    public TabelaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classificacao_grupo, parent, false);
        TabelaViewHolder tabelaViewHolder = new TabelaViewHolder(view);
        return tabelaViewHolder;
    }

    @Override
    public void onBindViewHolder(TabelaViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Grupo grupo = tabela.getGrupos().get(position);
        holder.txtGrupo.setText(String.format(mContext.getString(R.string.format_text_grupo), grupo.getSigla()));
        int posicao = 1;
        createHeader(holder);

        for(Time time : grupo.getTimes()){
            View v = holder.generateViewTime(mContext);
            TextView txtPosicao = (TextView) v.findViewById(R.id.txtPosicao);
            TextView txtNome = (TextView) v.findViewById(R.id.txtNome);
            TextView txtJogos = (TextView) v.findViewById(R.id.txtJogos);
            TextView txtVitorias = (TextView) v.findViewById(R.id.txtVitorias);
            TextView txtGolsPro = (TextView) v.findViewById(R.id.txtGolsPro);
            TextView txtGolsContra = (TextView) v.findViewById(R.id.txtGolsContra);
            TextView txtSaldoGols = (TextView) v.findViewById(R.id.txtSaldoGols);
            TextView txtPontos = (TextView) v.findViewById(R.id.txtPontos);

            if(posicao < 3){
                CardView cdvTime = (CardView) v.findViewById(R.id.cdvTime);
                cdvTime.setCardBackgroundColor(mContext.getResources().getColor(R.color.colorBackgroundQualifiedTeam));
            }

            txtPosicao.setText(String.format(mContext.getString(R.string.format_text_time_posicao), posicao++));
            txtNome.setText(time.getNome());
            txtJogos.setText(String.valueOf(time.getJogos()));
            txtVitorias.setText(String.valueOf(time.getVitorias()));
            txtSaldoGols.setText(String.valueOf(time.getSaldoGols()));
            txtGolsContra.setText(String.valueOf(time.getGolsContra()));
            txtGolsPro.setText(String.valueOf(time.getGolsPro()));
            txtPontos.setText(String.valueOf(time.getPontos()));


        }
    }

    private void createHeader(TabelaViewHolder holder) {
        View v = holder.generateViewTime(mContext);
        TextView txtPosicao = (TextView) v.findViewById(R.id.txtPosicao);
        TextView txtNome = (TextView) v.findViewById(R.id.txtNome);
        TextView txtJogos = (TextView) v.findViewById(R.id.txtJogos);
        TextView txtVitorias = (TextView) v.findViewById(R.id.txtVitorias);
        TextView txtGolsPro = (TextView) v.findViewById(R.id.txtGolsPro);
        TextView txtGolsContra = (TextView) v.findViewById(R.id.txtGolsContra);
        TextView txtSaldoGols = (TextView) v.findViewById(R.id.txtSaldoGols);
        TextView txtPontos = (TextView) v.findViewById(R.id.txtPontos);

        txtNome.setText("Equipe");
        txtPosicao.setText("#");
        txtJogos.setText("J");
        txtVitorias.setText("V");
        txtGolsPro.setText("GP");
        txtGolsContra.setText("GC");
        txtSaldoGols.setText("SG");
        txtPontos.setText("PTS");
    }

    @Override
    public int getItemCount() {
        return tabela.getGrupos().size();
    }

    public class TabelaViewHolder extends RecyclerView.ViewHolder {
        TextView txtGrupo;
        LinearLayout layoutHolder;

        public TabelaViewHolder(View itemView) {
            super(itemView);
            txtGrupo = (TextView) itemView.findViewById(R.id.txtGrupo);

            layoutHolder = (LinearLayout) itemView.findViewById(R.id.layoutTimeHolder);
        }


        public View generateViewTime(Context mContext) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newView = layoutInflater.inflate(R.layout.item_classificacao_time, null);
            layoutHolder.addView(newView);
            return newView;
        }
    }
}
