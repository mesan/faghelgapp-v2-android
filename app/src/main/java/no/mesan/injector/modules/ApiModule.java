package no.mesan.injector.modules;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import no.mesan.faghelgapps.R;
import no.mesan.service.api.FaghelgApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import timber.log.Timber;

@Module
public class ApiModule {
    private enum LogLevel {
        NONE, BASIC, FULL
    }

    private final LogLevel logLevel;
    private final Context context;

    public ApiModule(Context context) {
        this.context = context;
        logLevel = context.getResources().getBoolean(R.bool.debugging) ? LogLevel.FULL : LogLevel.NONE;
    }

    @Provides
    @Singleton
    public FaghelgApi provideProductApi() {
        okhttp3.OkHttpClient.Builder clientBuilder = getClientBuilder(5);
        Retrofit restAdapter = new Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl(context.getString(R.string.default_product_base_url))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return restAdapter.create(FaghelgApi.class);
    }

    private okhttp3.OkHttpClient.Builder getClientBuilder(int timeout) {
        okhttp3.Interceptor requestInterceptor = chain -> {
            okhttp3.Request originalRequest = chain.request();
            okhttp3.Request request = originalRequest.newBuilder().build();

            if (LogLevel.FULL.equals(logLevel)) {
                Timber.d(String.format("--> %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            } else if (LogLevel.BASIC.equals(logLevel)) {
                Timber.d(String.format("--> %s", request.url()));
            }
            okhttp3.Response response = chain.proceed(request);
            if (LogLevel.FULL.equals(logLevel)) {
                Timber.d(String.format("<-- %s - %s", response.code(), request.url()));
                Timber.d(String.format("%s", response.headers()));
                Timber.d("");
                Timber.d("Body:");
                Timber.d(String.format("%s", request.body()));
            } else if (LogLevel.BASIC.equals(logLevel)) {
                Timber.d(String.format("<-- %s - %s", response.code(), request.url()));
            }

            return response;
        };
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient().newBuilder().readTimeout(timeout, TimeUnit.MINUTES).addInterceptor(requestInterceptor);

        return clientBuilder;
    }
}
