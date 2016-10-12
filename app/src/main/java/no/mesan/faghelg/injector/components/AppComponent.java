package no.mesan.faghelg.injector.components;

import javax.inject.Singleton;

import dagger.Component;
import no.mesan.faghelg.injector.modules.ApiModule;
import no.mesan.faghelg.injector.modules.DatabaseModule;
import no.mesan.faghelg.service.DatabaseService;
import no.mesan.faghelg.service.api.FaghelgApi;

@Singleton
@Component(modules = {ApiModule.class, DatabaseModule.class})
public interface AppComponent {

    FaghelgApi provideProductApi();

    DatabaseService provideDatabaseService();

}
