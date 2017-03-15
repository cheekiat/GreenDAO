package cheekiat.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Chee Kiat on 15/03/2017.
 */

public class MyDevOpenHelper extends DaoMaster.DevOpenHelper{
    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        Log.d(this.getClass().getName(),"oldVersion : "+oldVersion+" / newVersion"+newVersion);

        switch (oldVersion) {
            case 1:
            /* v1->v2: all changes made in version 2 come here */
            /* break was omitted by purpose. */
            case 2:
            /* v2->v3: all changes made in version 3 come here */
            /* break was omitted by purpose. */
            case 4:
            /* v2->v3: all changes made in version 3 come here */
                db.execSQL("ALTER TABLE "+NoteDao.TABLENAME+" ADD COLUMN 'MY_NEW_B' TEXT;");
            case 5:
            /* v2->v3: all changes made in version 3 come here */
                db.execSQL("ALTER TABLE "+NoteDao.TABLENAME+" ADD COLUMN 'MY_NEW_C' TEXT;");
        }
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
