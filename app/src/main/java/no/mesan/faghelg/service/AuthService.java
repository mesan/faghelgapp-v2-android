package no.mesan.faghelg.service;

import java.io.IOException;

import javax.inject.Inject;

import no.mesan.faghelg.model.AuthToken;
import no.mesan.faghelg.service.api.FaghelgApi;
import retrofit2.Response;
import rx.Observable;

public class AuthService extends BaseService {

    private final FaghelgApi faghelgApi;

    @Inject
    public AuthService(FaghelgApi faghelgApi) {
        this.faghelgApi = faghelgApi;
    }

    public Observable<AuthToken> suthenticate(String idToken, String phoneNumber) {

        Observable<AuthToken> observable = Observable.create(subscriber -> {

            try {
                Response<AuthToken> response = faghelgApi.authenticate(idToken, phoneNumber).execute();

                if (response.isSuccessful()) {
                    subscriber.onNext(response.body());
                } else {
                    subscriber.onError(new Throwable());
                }
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }

            subscriber.onCompleted();
        });

        return observable.compose(applyAndroidSchedulers());
    }
}
