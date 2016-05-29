package br.com.futfatec.rest.classificacao;

import android.content.Context;

import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.classificacao.Tabela;
import br.com.futfatec.rest.AbstractRestService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexa on 27/05/2016.
 */
public class ClassificacaoRestService extends AbstractRestService {
    private AppPreferencesData preferencesData;
    private Context mContext;

    public ClassificacaoRestService(Context mContext) {
        this.mContext = mContext;
        preferencesData = new AppPreferencesData(mContext);
    }

    public void get(final OnResponse<Tabela> callback, String leagueId) {
        ClassificacaoService classificacaoService = (ClassificacaoService) initializeRestService(ClassificacaoService.class);
        final Call<Tabela> call;
        call = classificacaoService.get(leagueId);
        call.enqueue(new Callback<Tabela>() {
            @Override
            public void onResponse(Response<Tabela> response, Retrofit retrofit) {
                Tabela t = response.body();
                preferencesData.storeClassificacao(t);
                callback.success(t);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.error(new HttpRestException(t.getMessage(), 500));
            }
        });
    }
}
