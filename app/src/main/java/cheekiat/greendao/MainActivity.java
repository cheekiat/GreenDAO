package cheekiat.greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Note().create();

        List<Note> notes = Note.getAllDatas();
        for (int i = 0; i < notes.size(); i++) {
            Log.d(this.getClass().getName(), "id = "+notes.get(i).id+" \n Text : " + notes.get(i).text);
        }

        Note.update(1,"update data");

        Note.delete(1);

    }

}
