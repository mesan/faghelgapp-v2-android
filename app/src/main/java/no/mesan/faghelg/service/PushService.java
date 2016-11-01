package no.mesan.faghelg.service;

import no.mesan.faghelg.model.PushDevice;
import rx.Observable;

import javax.inject.Inject;

import no.mesan.faghelg.service.api.FaghelgApi;

public class PushService extends BaseService {

    private FaghelgApi faghelgApi;

    @Inject
    public PushService(FaghelgApi faghelgApi) {
        this.faghelgApi = faghelgApi;
    }

    public Observable<String> registerForPush(String token, String regid) {
        Observable<String> observable = Observable.create(subscriber -> {
        try {
            PushDevice pushDevice = new PushDevice("", regid, "Android");
            faghelgApi.registerForPush(token, pushDevice).execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            subscriber.onError(ex);
        }
        subscriber.onCompleted();
        });

        return observable.compose(applyAndroidSchedulers());
    }
}
