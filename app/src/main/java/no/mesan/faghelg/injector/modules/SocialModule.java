package no.mesan.faghelg.injector.modules;

import dagger.Module;
import dagger.Provides;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.service.SocialService;
import no.mesan.faghelg.service.api.FaghelgApi;

@Module
public class SocialModule {

    @Provides
    @ActivityScope
    SocialService provideSocialService(FaghelgApi faghelgApi) {
        return new SocialService(faghelgApi);
    }
}
