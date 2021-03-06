package no.mesan.faghelg;

import android.app.Application;

import com.crashlytics.android.Crashlytics;

import net.danlew.android.joda.JodaTimeAndroid;

import io.fabric.sdk.android.Fabric;
import no.mesan.faghelgapps.R;
import no.mesan.faghelg.injector.components.AppComponent;
import no.mesan.faghelg.injector.components.DaggerAppComponent;
import no.mesan.faghelg.injector.modules.ApiModule;
import no.mesan.faghelg.injector.modules.DatabaseModule;
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
        JodaTimeAndroid.init(this);

        Fabric.with(this, new Crashlytics());

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