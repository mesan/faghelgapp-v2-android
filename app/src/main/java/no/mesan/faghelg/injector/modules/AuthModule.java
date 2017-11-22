package no.mesan.faghelg.injector.modules;

import dagger.Module;
import dagger.Provides;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.service.AuthService;
import no.mesan.faghelg.service.api.FaghelgApi;

@Module
public class AuthModule {

    @Provides
    @ActivityScope
    AuthService provideProgramService(FaghelgApi faghelgApi) {
        return new AuthService(faghelgApi);
    }
}
