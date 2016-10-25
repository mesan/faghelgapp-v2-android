package no.mesan.faghelg.injector.components;

import dagger.Component;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.injector.modules.SocialModule;
import no.mesan.faghelg.view.social.SocialFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {SocialModule.class})
public interface SocialFragmentComponent {

    void inject(SocialFragment socialFragment);
}
