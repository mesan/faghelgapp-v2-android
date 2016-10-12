package no.mesan.faghelg.injector.modules;

import dagger.Module;
import dagger.Provides;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.service.PeopleService;
import no.mesan.faghelg.service.api.FaghelgApi;

@Module
public class PeopleModule {

    @Provides
    @ActivityScope
    public PeopleService providePersonService(FaghelgApi faghelgApi) {
        return new PeopleService(faghelgApi);
    }
}
