package br.com.futfatec.rest.classificacao;

import br.com.futfatec.model.classificacao.Tabela;
import br.com.futfatec.model.classificacao.Time;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by alexa on 27/05/2016.
 */
public interface ClassificacaoService {
    @GET("tabela/{leagueId}")
    Call<Tabela> get(@Path("leagueId") String leagueId);

    @POST("tabela/time/save/{idTabela}")
    Call<Tabela> postTime(@Path("idTabela") String idTabela, @Body Time time);


}
