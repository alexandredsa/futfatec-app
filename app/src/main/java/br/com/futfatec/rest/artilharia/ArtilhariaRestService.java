package br.com.futfatec.rest.artilharia;

import android.content.Context;

import java.util.List;

import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.artilharia.Jogador;
import br.com.futfatec.rest.AbstractRestService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by alexa on 29/05/2016.
 */
public class ArtilhariaRestService extends AbstractRestService{
    private Context mContext;

    public ArtilhariaRestService(Context mContext) {
        this.mContext = mContext;
    }


    public void get(final AbstractRestService.OnResponse<List<Jogador>> callback, String tabelaId) {
        ArtilhariaService artilhariaService = (ArtilhariaService) initializeRestService(ArtilhariaService.class);
        final Call<List<Jogador>> call;
        call = artilhariaService.get(tabelaId);
        call.enqueue(new Callback<List<Jogador>>() {
            @Override
            public void onResponse(Response<List<Jogador>> response, Retrofit retrofit) {
                List<Jogador> jogadores = response.body();
                callback.success(jogadores);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.error(new HttpRestException(t.getMessage(), 500));
            }
        });
    }
}