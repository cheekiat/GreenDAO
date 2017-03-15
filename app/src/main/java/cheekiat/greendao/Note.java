package cheekiat.greendao;

import android.util.Log;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;
import java.util.Date;

/**
 * Created by Chee Kiat on 09/03/2017.
 */

@Entity
public class Note {

    @Id
    public Long id;

    public String text;
    public java.util.Date date;

    @Convert(converter = NoteTypeConverter.class, columnType = String.class)
    public NoteType type;


    @Generated(hash = 1144130834)
    public Note(Long id, String text, java.util.Date date, NoteType type) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.type = type;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }


    public void create() {

        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        Note note = new Note();
        note.text = "add data";
        noteDao.save(note);
    }

    public static List<Note> getAllDatas() {
        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();

        List<Note> notes = noteDao.loadAll();
        return notes;
    }

    public static List<Note> queryDates(String keyword) {
        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        Query<Note> query = noteDao.queryBuilder().where(NoteDao.Properties.Text.eq(keyword)
        ).build();

        List<Note> notes = query.list();
        return notes;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public java.util.Date getDate() {
        return this.date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public NoteType getType() {
        return this.type;
    }

    public void setType(NoteType type) {
        this.type = type;
    }


    public static void delete(int id) {
        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        QueryBuilder<Note> qb = noteDao.queryBuilder();
        DeleteQuery<Note> bd = qb.where(NoteDao.Properties.Id.eq(id)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    public static void update(int id, String str) {

        DaoSession daoSession = (App.getInstance()).getDaoSession();
        NoteDao noteDao = daoSession.getNoteDao();
        Note note = noteDao.queryBuilder().where(NoteDao.Properties.Id.eq(id)
        ).unique();
        note.text = str;
        noteDao.save(note);

    }
}