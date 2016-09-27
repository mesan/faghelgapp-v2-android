package no.mesan.injector.components;

import javax.inject.Singleton;

import dagger.Component;
import no.mesan.injector.modules.ApiModule;
import no.mesan.injector.modules.DatabaseModule;
import no.mesan.service.DatabaseService;
import no.mesan.service.api.FaghelgApi;

@Singleton
@Component(modules = {ApiModule.class, DatabaseModule.class})
public interface AppComponent {

    FaghelgApi provideProductApi();

    DatabaseService provideDatabaseService();

}
