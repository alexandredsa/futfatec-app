package br.com.futfatec.rest.rodada;

import android.content.Context;

import java.util.List;

import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.rodada.Evento;
import br.com.futfatec.model.rodada.Partida;
import br.com.futfatec.model.rodada.Rodada;
import br.com.futfatec.rest.AbstractRestService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexa on 28/05/2016.
 */
public class RodadaRestService extends AbstractRestService {
    private AppPreferencesData preferencesData;
    private Context mContext;

    public RodadaRestService(Context mContext) {
        this.mContext = mContext;
        preferencesData = new AppPreferencesData(mContext);
    }


    public void get(final OnResponse<List<Rodada>> callback, String tabelaId) {
        RodadaService rodadaService = (RodadaService) initializeRestService(RodadaService.class);
        final Call<List<Rodada>> call;
        call = rodadaService.get(tabelaId);
        call.enqueue(new Callback<List<Rodada>>() {
            @Override
            public void onResponse(Response<List<Rodada>> response, Retrofit retrofit) {
                List<Rodada> jogos = response.body();
                preferencesData.storeJogos(jogos);
                callback.success(jogos);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.error(new HttpRestException(t.getMessage(), 500));
            }
        });
    }

    public void getPartida(final OnResponse<Partida> callback, String rodadaId, String horaInicio) {
        RodadaService rodadaService = (RodadaService) initializeRestService(RodadaService.class);
        final Call<Partida> call;
        call = rodadaService.getPartida(rodadaId, horaInicio);
        call.enqueue(new Callback<Partida>() {
            @Override
            public void onResponse(Response<Partida> response, Retrofit retrofit) {
                Partida partida = response.body();
                callback.success(partida);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.error(new HttpRestException(t.getMessage(), 500));
            }
        });

    }

    public void postEvento(final OnResponse<Rodada> callback, String rodadaId, String horaInicio, Evento evento) {
        RodadaService rodadaService = (RodadaService) initializeRestService(RodadaService.class);
        final Call<Rodada> call;
        call = rodadaService.postEvento(rodadaId, horaInicio, evento);
        call.enqueue(new Callback<Rodada>() {
            @Override
            public void onResponse(Response<Rodada> response, Retrofit retrofit) {
                Rodada rodada = response.body();
                callback.success(rodada);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.error(new HttpRestException(t.getMessage(), 500));
            }
        });

    }


    public void postPartida(final OnResponse<Rodada> callback, String rodadaId, Partida partida) {
        RodadaService rodadaService = (RodadaService) initializeRestService(RodadaService.class);
        final Call<Rodada> call;
        call = rodadaService.postPartida(rodadaId, partida);
        call.enqueue(new Callback<Rodada>() {
            @Override
            public void onResponse(Response<Rodada> response, Retrofit retrofit) {
                Rodada rodada = response.body();
                callback.success(rodada);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.error(new HttpRestException(t.getMessage(), 500));
            }
        });

    }
}
