package no.mesan.injector.components;

import dagger.Component;
import no.mesan.injector.ActivityScope;
import no.mesan.injector.modules.ProgramModule;
import no.mesan.view.ProgramFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ProgramModule.class})
public interface ProgramFragmentComponent {

    void inject(ProgramFragment fragment);
}
