package no.mesan.injector.modules;

import dagger.Module;
import dagger.Provides;
import no.mesan.injector.ActivityScope;
import no.mesan.service.PeopleService;
import no.mesan.service.api.FaghelgApi;

@Module
public class PeopleModule {

    @Provides
    @ActivityScope
    public PeopleService providePersonService(FaghelgApi faghelgApi) {
        return new PeopleService(faghelgApi);
    }
}
