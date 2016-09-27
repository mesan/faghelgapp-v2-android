package no.mesan.injector.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import no.mesan.service.DatabaseService;

@Module
public class DatabaseModule {
    private final Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public DatabaseService provideDataBase() {
//        DaoMaster.DevOpenHelper master = new DaoMaster.DevOpenHelper(context, ProductDao.TABLENAME, null);
//        SQLiteDatabase database = master.getWritableDatabase();
//        DaoMaster daoMaster = new DaoMaster(database);
//        DaoSession daoSession = daoMaster.newSession();
//        return new DatabaseService(daoSession);
        return null;
    }
}
