package no.mesan.injector.modules;

import dagger.Module;
import dagger.Provides;
import no.mesan.injector.ActivityScope;
import no.mesan.service.ProgramService;
import no.mesan.service.api.FaghelgApi;

@Module
public class ProgramModule {

    @Provides
    @ActivityScope
    ProgramService provideProgramService(FaghelgApi faghelgApi) {
        return new ProgramService(faghelgApi);
    }
}
