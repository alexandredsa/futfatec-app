package br.com.futfatec.content;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.List;

import br.com.futfatec.model.auth.Role;
import br.com.futfatec.model.auth.ValidationStatus;
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
    private static final String PREFS_KEY_VALIDATION = "validationStatusData";
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

    public Time retrieveTime(String nome) {
        Tabela tabela = retrieveClassificacao();
        return tabela.getTime(nome);
    }

    private void storeObject(String key, Object obj) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, gson.toJson(obj));
        editor.commit();
    }

    private void remove(String key) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.commit();
    }

    public String getTabelaId() {
        Tabela tabela = retrieveClassificacao();
        return tabela.getId();
    }

    public void storeJogos(List<Rodada> jogos) {
        storeObject(PREFS_KEY_JOGOS, jogos);
    }

    public void storeValidation(ValidationStatus validationStatus) {
        storeObject(PREFS_KEY_VALIDATION, validationStatus);
    }

    public String getLeagueId() {
        ValidationStatus status = retrieveValidationStatus();

        return status.getLeagueId();
    }

    public void invalidateSession() {
        remove(PREFS_KEY_VALIDATION);
    }

    public boolean hasSession() {
        ValidationStatus status = retrieveValidationStatus();
        return status != null;
    }

    public boolean isAdmin() {
        ValidationStatus status = retrieveValidationStatus();
        return status.getRole() == Role.ADMIN;
    }

    private ValidationStatus retrieveValidationStatus() {
        String jsonValidation = prefs.getString(PREFS_KEY_VALIDATION, null);
        return jsonValidation != null ? gson.fromJson(jsonValidation, ValidationStatus.class) : null;
    }
}
