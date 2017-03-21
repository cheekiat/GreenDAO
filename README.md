# Andorid GreenDAO CRUD tutorial

Official website
http://greenrobot.org/greendao/

Who is using greenDAO?
http://www.appbrain.com/stats/libraries/details/greendao/greendao

ScreenShot
----------------
![ScreenShot](https://github.com/cheekiat/GreenDAO/blob/master/screen%20short.gif)

### 3 Step setup project

Step 1
----------------
Open your build.gradle (Module: app) add
```
greendao {
    schemaVersion 1
}

dependencies {
    compile 'org.greenrobot:greendao:3.2.0'
}
```

Step 2
----------------
Create class for extends Application
```
public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper helper = new MyDevOpenHelper(this,"notes-db");
        Database db = helper.getWritableDb();

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
```

Step 3
----------------
Create MyDevOpenHelper class extends DaoMaster.DevOpenHelper for update table
```
public class MyDevOpenHelper extends DaoMaster.DevOpenHelper{
    public MyDevOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {

        super.onUpgrade(db, oldVersion, newVersion);
    }
}
```

### Now can start create table and CRUD

Create table
----------------
```
@Entity
public class Note {

    @Id
    public Long id;

    public String text;
    
 }
```


Create
----------------
```
        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        
        Note note = new Note();
        note.text = "add data";
        noteDao.save(note);
```

Read
----------------
```
        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();

        List<Note> notes = noteDao.loadAll();
```

Update
----------------
```
        int id = 1;//update table id
        
        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        
        Note note = noteDao.queryBuilder().where(NoteDao.Properties.Id.eq(id)
        ).unique();
        note.text = "update text column";
        noteDao.save(note);
```

Delete
----------------
```
        int id = 1;//delete table id
        
        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        
        QueryBuilder<Note> qb = noteDao.queryBuilder();
        DeleteQuery<Note> bd = qb.where(NoteDao.Properties.Id.eq(id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
```
