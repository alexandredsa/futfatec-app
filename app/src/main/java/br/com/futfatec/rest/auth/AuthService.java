package br.com.futfatec.rest.auth;

import br.com.futfatec.model.auth.ValidationStatus;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by alexa on 11/06/2016.
 */
public interface AuthService {
    @GET("auth/{accessId}")
    Call<ValidationStatus> login(@Path("accessId") String accessId);
}
