package no.mesan;

import android.app.Application;

import no.mesan.faghelgapps.R;
import no.mesan.injector.components.AppComponent;
import no.mesan.injector.components.DaggerAppComponent;
import no.mesan.injector.modules.ApiModule;
import no.mesan.injector.modules.DatabaseModule;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class FaghelgApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initInjector();
        Timber.plant(new Timber.DebugTree());
        Timber.tag("TAG");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ProximaNova-Light.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    protected void initInjector() {
        buildInjector(new ApiModule(this), new DatabaseModule(this));
    }

    public void buildInjector(ApiModule apiModule, DatabaseModule databaseModule) {
        appComponent = DaggerAppComponent.builder().apiModule(apiModule).databaseModule(databaseModule).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
