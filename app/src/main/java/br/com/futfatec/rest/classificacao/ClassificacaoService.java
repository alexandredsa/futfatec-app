package br.com.futfatec.rest.classificacao;

import br.com.futfatec.model.classificacao.Tabela;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alexa on 27/05/2016.
 */
public interface ClassificacaoService {
    @GET("tabela/{leagueId}")
    Call<Tabela> get(@Path("leagueId") String leagueId);

/*    @GET("5749bb4e27000075183735da/{leagueId}")
    Call<Tabela> get(@Path("leagueId") String leagueId);*/
}
