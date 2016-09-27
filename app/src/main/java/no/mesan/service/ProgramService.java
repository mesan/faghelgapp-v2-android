package no.mesan.service;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import no.mesan.model.Event;
import no.mesan.model.Program;
import no.mesan.service.api.FaghelgApi;
import retrofit2.Response;
import rx.Observable;

public class ProgramService extends BaseService {

    private final FaghelgApi faghelgApi;

    @Inject
    public ProgramService(FaghelgApi faghelgApi) {
        this.faghelgApi = faghelgApi;
    }

    public Observable<List<Event>> getEventsFromApi() {

        Observable<List<Event>> observable = Observable.create(subscriber -> {

            try {
                Response<Program> response = faghelgApi.getProgram().execute();

                if (response.isSuccessful()) {
                    subscriber.onNext(response.body().getEvents());
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
