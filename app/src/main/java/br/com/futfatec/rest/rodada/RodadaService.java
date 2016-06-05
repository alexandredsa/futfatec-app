package br.com.futfatec.rest.rodada;

import java.util.List;

import br.com.futfatec.model.rodada.Evento;
import br.com.futfatec.model.rodada.Partida;
import br.com.futfatec.model.rodada.Rodada;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by alexa on 28/05/2016.
 */
public interface RodadaService {
    @GET("rodada/{tabelaId}")
    Call<List<Rodada>> get(@Path("tabelaId") String tabelaId);

    @GET("rodada/partida/{rodadaId}/{horaInicio}")
    Call<Partida> getPartida(@Path("rodadaId") String rodadaId, @Path("horaInicio") String horaInicio);

    @POST("rodada/update/{rodadaId}/{horaInicio}/evento")
    Call<Rodada> postEvento(@Path("rodadaId") String rodadaId, @Path("horaInicio") String horaInicio, @Body Evento evento);
}
