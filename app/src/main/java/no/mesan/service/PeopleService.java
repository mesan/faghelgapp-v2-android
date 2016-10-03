package no.mesan.service;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import no.mesan.model.Person;
import no.mesan.service.api.FaghelgApi;
import retrofit2.Response;
import rx.Observable;

public class PeopleService extends BaseService {

    private FaghelgApi faghelgApi;

    @Inject
    public PeopleService(FaghelgApi faghelgApi) {
        this.faghelgApi = faghelgApi;
    }

    public Observable<List<Person>> getPersons() {
        Observable<List<Person>> observable = Observable.create(subscriber -> {
            try {
                Response<List<Person>> response = faghelgApi.getPersons().execute();

                if (response.isSuccessful()) {
                    List<Person> persons = response.body();
                    Collections.sort(persons, (person, otherPerson) -> person.getFullName().compareTo(otherPerson.getFullName()));
                    subscriber.onNext(persons);
                } else {
                    subscriber.onError(new Throwable("Backend svarer ikke"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);

            }

            subscriber.onCompleted();
        });

        return observable.compose(applyAndroidSchedulers());
    }
}
