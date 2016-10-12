package no.mesan.faghelg.injector.components;

import dagger.Component;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.injector.modules.PeopleModule;
import no.mesan.faghelg.service.PeopleService;
import no.mesan.faghelg.view.people.PeopleFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {PeopleModule.class})
public interface PeopleFragmentComponent {

    PeopleService providePersonService();

    void inject(PeopleFragment peopleFragment);
}
