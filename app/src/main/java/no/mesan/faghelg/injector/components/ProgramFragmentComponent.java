package no.mesan.faghelg.injector.components;

import dagger.Component;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.injector.modules.ProgramModule;
import no.mesan.faghelg.view.program.ProgramFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ProgramModule.class})
public interface ProgramFragmentComponent {

    void inject(ProgramFragment fragment);
}
