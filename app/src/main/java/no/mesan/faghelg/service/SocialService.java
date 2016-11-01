package no.mesan.faghelg.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import no.mesan.faghelg.model.Message;
import no.mesan.faghelg.model.MessageOutput;
import no.mesan.faghelg.service.api.FaghelgApi;
import retrofit2.Response;
import rx.Observable;

public class SocialService extends BaseService {

    private final FaghelgApi faghelgApi;

    @Inject
    public SocialService(FaghelgApi faghelgApi) {
        this.faghelgApi = faghelgApi;
    }

    public Observable<List<Message>> getMessages() {
        Observable<List<Message>> observable = Observable.create(subscriber -> {

            try {
                Response<List<Message>> response = faghelgApi.getMessages().execute();

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

    public Observable<Void> postMessage(String token, MessageOutput message) {
        Observable<Void> observable = Observable.create(subscriber -> {
            try {
                Response<Void> response = faghelgApi.postMessage(token, message).execute();

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
