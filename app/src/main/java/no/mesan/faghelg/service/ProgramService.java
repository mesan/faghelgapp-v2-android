package no.mesan.faghelg.service;

import java.io.IOException;

import javax.inject.Inject;

import no.mesan.faghelg.model.Program;
import no.mesan.faghelg.service.api.FaghelgApi;
import retrofit2.Response;
import rx.Observable;

public class ProgramService extends BaseService {

    private final FaghelgApi faghelgApi;

    @Inject
    public ProgramService(FaghelgApi faghelgApi) {
        this.faghelgApi = faghelgApi;
    }

    public Observable<Program> getProgram() {

        Observable<Program> observable = Observable.create(subscriber -> {

            try {
                Response<Program> response = faghelgApi.getProgram().execute();

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
