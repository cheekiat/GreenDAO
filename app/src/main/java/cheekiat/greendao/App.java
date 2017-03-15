package cheekiat.greendao;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Chee Kiat on 09/03/2017.
 */

public class App extends Application {

    public static final boolean ENCRYPTED = false;

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper helper = new MyDevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();

//        MyDevOpenHelper helper = new MyDevOpenHelper(this, "notes-db-encrypted.db");
//        Database db = helper.getEncryptedWritableDb("super-secret");
        daoSession = new DaoMaster(db).newSession();
        instance = this;
    }

    private static App instance;

    public static App getInstance() {
        return instance;
    }



    public DaoSession getDaoSession() {
        return daoSession;
    }
}
