package br.com.futfatec.rest.artilharia;

import java.util.List;

import br.com.futfatec.model.artilharia.Jogador;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alexa on 29/05/2016.
 */
public interface ArtilhariaService {
    @GET("artilharia/{tabelaId}")
    Call<List<Jogador>> get(@Path("tabelaId")String tabelaId);
}
