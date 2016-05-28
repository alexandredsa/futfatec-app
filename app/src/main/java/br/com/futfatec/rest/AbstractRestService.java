package br.com.futfatec.rest;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.futfatec.exception.HttpRestException;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by alexa on 27/05/2016.
 */
public class AbstractRestService {

    private static final String BASE_URL = "http://192.168.1.42:8088/";
    private Retrofit retrofit;


    public Object initializeRestService(Class clazz) {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                if (response.code() >= 400)
                    throw new HttpRestException(response.message(), response.code());

                return response;
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(clazz);
    }

    public interface OnResponse<T>{
        void success(T t);
        void error(HttpRestException restException);
    }
}
