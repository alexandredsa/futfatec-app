package br.com.futfatec.rest.rodada;

import java.util.List;

import br.com.futfatec.model.rodada.Rodada;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alexa on 28/05/2016.
 */
public interface RodadaService {
    @GET("rodada/{tabelaId}")
    Call<List<Rodada>> get(@Path("tabelaId") String tabelaId);
}
