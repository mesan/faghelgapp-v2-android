package no.mesan.faghelg.injector.modules;

import dagger.Module;
import dagger.Provides;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.service.PushService;
import no.mesan.faghelg.service.api.FaghelgApi;

@Module
public class PushModule {

    @Provides
    @ActivityScope
    public PushService providePushService(FaghelgApi faghelgApi) {
        return new PushService(faghelgApi);
    }
}
