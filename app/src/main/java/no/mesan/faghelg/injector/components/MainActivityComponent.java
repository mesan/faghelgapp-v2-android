package no.mesan.faghelg.injector.components;

import dagger.Component;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.injector.modules.PushModule;
import no.mesan.faghelg.service.PushService;
import no.mesan.faghelg.view.MainActivity;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {PushModule.class})
public interface MainActivityComponent {

    PushService providePushService();

    void inject(MainActivity mainActivity);
}
