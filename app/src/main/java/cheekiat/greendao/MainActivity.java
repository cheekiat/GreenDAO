package cheekiat.greendao;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    AlertDialog mDialogCreaate, mDialogUpdate, mDialogDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDialogInit();
        updateDialogInit();
        DeleteDialogInit();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);

        reload();

        findViewById(R.id.create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mDialogCreaate.isShowing()) {
                    mDialogCreaate.show();
                }
            }
        });
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mDialogUpdate.isShowing()) {
                    mDialogUpdate.show();
                }
            }
        });
        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mDialogDelete.isShowing()) {
                    mDialogDelete.show();
                }
            }
        });

    }

    void createDialogInit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_create, null);
        final EditText mText = (EditText) view.findViewById(R.id.text);
        builder.setView(view)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        DaoSession daoSession = (App.getInstance()).getDaoSession();
                        NoteDao noteDao = daoSession.getNoteDao();
                        Note note = new Note();
                        note.text = mText.getText().toString();
                        noteDao.save(note);
                        mText.setText("");
                        reload();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        mDialogCreaate = builder.create();
    }

    void updateDialogInit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_update, null);
        final EditText mText = (EditText) view.findViewById(R.id.text);
        final EditText mId = (EditText) view.findViewById(R.id.id);
        builder.setView(view)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        DaoSession daoSession = (App.getInstance()).getDaoSession();
                        NoteDao noteDao = daoSession.getNoteDao();
                        if(!mId.getText().toString().isEmpty()) {
                            Note note = noteDao.queryBuilder().where(NoteDao.Properties.Id.eq(Integer.valueOf(mId.getText().toString()))
                            ).unique();

                            if (note == null) {
                                Toast.makeText(MainActivity.this, "id not found", Toast.LENGTH_SHORT).show();
                            } else {
                                note.text = mText.getText().toString();
                                noteDao.save(note);
                            }
                        }
                        mText.setText("");
                        mId.setText("");
                        reload();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        mDialogUpdate = builder.create();
    }

    void DeleteDialogInit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_delete, null);
        final EditText mText = (EditText) view.findViewById(R.id.text);
        final EditText mId = (EditText) view.findViewById(R.id.id);
        builder.setView(view)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        DaoSession daoSession = (App.getInstance()).getDaoSession();
                        NoteDao noteDao = daoSession.getNoteDao();
                        QueryBuilder<Note> qb = noteDao.queryBuilder();
                        DeleteQuery<Note> bd = qb.where(NoteDao.Properties.Id.eq(mId.getText().toString())).buildDelete();
                        bd.executeDeleteWithoutDetachingEntities();

                        mId.setText("");
                        reload();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        mDialogDelete = builder.create();
    }

    void reload() {
        mAdapter.clear();
        List<Note> notes = Note.getAllDatas();
        for (int i = 0; i < notes.size(); i++) {
            Log.d(this.getClass().getName(), "id = " + notes.get(i).id + " \n Keyin Date : " + notes.get(i).text);
            mAdapter.addData("Id : " + notes.get(i).id + " \nText : " + notes.get(i).text);
        }
    }
}
