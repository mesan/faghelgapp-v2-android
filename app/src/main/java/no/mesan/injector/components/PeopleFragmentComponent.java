package no.mesan.injector.components;

import dagger.Component;
import no.mesan.injector.ActivityScope;
import no.mesan.injector.modules.PeopleModule;
import no.mesan.service.PeopleService;
import no.mesan.view.people.PeopleFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {PeopleModule.class})
public interface PeopleFragmentComponent {

    PeopleService providePersonService();

    void inject(PeopleFragment peopleFragment);
}
