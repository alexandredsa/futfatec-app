package br.com.futfatec.content;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.futfatec.model.Tabela;

/**
 * Created by alexa on 27/05/2016.
 */
public class AppPreferencesData {
    public static final String PREFS_NAME = "FutFatecAppData";
    public static final String PREFS_KEY_CLASSIFICACAO = "classificacaoData";
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

    private void storeObject(String key, Object obj) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, gson.toJson(obj));
        editor.commit();
    }
}
