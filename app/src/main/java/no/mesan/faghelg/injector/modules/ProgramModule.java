package no.mesan.faghelg.injector.modules;

import dagger.Module;
import dagger.Provides;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.service.ProgramService;
import no.mesan.faghelg.service.api.FaghelgApi;

@Module
public class ProgramModule {

    @Provides
    @ActivityScope
    ProgramService provideProgramService(FaghelgApi faghelgApi) {
        return new ProgramService(faghelgApi);
    }
}
