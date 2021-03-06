package no.mesan.faghelg.injector.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import no.mesan.faghelg.injector.ActivityScope;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ActivityScope
    Context provideActivityContext() {
        return context;
    }

}
