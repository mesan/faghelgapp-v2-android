package no.mesan;

import android.app.Application;

import no.mesan.injector.components.AppComponent;
import no.mesan.injector.modules.ApiModule;
import no.mesan.injector.modules.DatabaseModule;

public class FaghelgApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
    }

    protected void initInjector() {
        buildInjector(new ApiModule(this), new DatabaseModule(this));
    }

    public void buildInjector(ApiModule apiModule, DatabaseModule databaseModule) {
//        appComponent = DaggerAppComponent.builder().apiModule(apiModule).databaseModule(databaseModule).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
