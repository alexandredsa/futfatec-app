package br.com.futfatec.content;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

import br.com.futfatec.model.classificacao.Tabela;
import br.com.futfatec.model.classificacao.Time;
import br.com.futfatec.model.rodada.Rodada;

/**
 * Created by alexa on 27/05/2016.
 */
public class AppPreferencesData {
    public static final String PREFS_NAME = "FutFatecAppData";
    public static final String PREFS_KEY_CLASSIFICACAO = "classificacaoData";
    private static final String PREFS_KEY_JOGOS = "jogosData";
    private Gson gson;
    private Context mContext;
    private SharedPreferences prefs;

    public AppPreferencesData(Context mContext) {
        this.mContext = mContext;
        this.gson = new Gson();
        this.prefs = mContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void storeClassificacao(Tabela tabela) {
        storeObject(PREFS_KEY_CLASSIFICACAO, tabela);
    }

    public Tabela retrieveClassificacao() {
        String jsonTabela = prefs.getString(PREFS_KEY_CLASSIFICACAO, null);
        return jsonTabela != null ? gson.fromJson(jsonTabela, Tabela.class) : null;
    }

    public Time retrieveTime(String nome){
        Tabela tabela = retrieveClassificacao();
        return tabela.getTime(nome);
    }

    private void storeObject(String key, Object obj) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, gson.toJson(obj));
        editor.commit();
    }

    public String getTabelaId(){
        Tabela tabela = retrieveClassificacao();
        return tabela.getId();
    }

    public void storeJogos(List<Rodada> jogos) {
        storeObject(PREFS_KEY_JOGOS, jogos);
    }
}
