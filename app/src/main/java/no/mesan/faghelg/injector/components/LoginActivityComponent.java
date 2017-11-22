package no.mesan.faghelg.injector.components;

import dagger.Component;
import no.mesan.faghelg.injector.ActivityScope;
import no.mesan.faghelg.injector.modules.AuthModule;
import no.mesan.faghelg.service.AuthService;
import no.mesan.faghelg.view.login.LoginActivity;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {AuthModule.class})
public interface LoginActivityComponent {

    AuthService provideProgramService();

    void inject(LoginActivity loginActivity);
}
