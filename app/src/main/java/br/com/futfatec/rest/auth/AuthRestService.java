package br.com.futfatec.rest.auth;

import android.content.Context;

import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.auth.ValidationStatus;
import br.com.futfatec.rest.AbstractRestService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by alexa on 11/06/2016.
 */
public class AuthRestService extends AbstractRestService {
    private Context mContext;
    private AppPreferencesData preferencesData;


    public AuthRestService(Context mContext) {
        this.mContext = mContext;
        this.preferencesData = new AppPreferencesData(mContext);
    }



    public void login(final OnResponse<ValidationStatus> callback, String accessId) {
        AuthService authService = (AuthService) initializeRestService(AuthService.class);
        final Call<ValidationStatus> call;
        call = authService.login(accessId);
        call.enqueue(new Callback<ValidationStatus>() {
            @Override
            public void onResponse(Response<ValidationStatus> response, Retrofit retrofit) {
                ValidationStatus validationStatus  = response.body();
                preferencesData.storeValidation(validationStatus);
                callback.success(validationStatus);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.error(new HttpRestException(t.getMessage(), 500));
            }
        });
    }
}
