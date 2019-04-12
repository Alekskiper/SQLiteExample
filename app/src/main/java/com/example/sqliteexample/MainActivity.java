package com.example.sqliteexample;
import java.io.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;

   private DatabaseHelper mDBHelper;
   private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        }
        catch (IOException mIOException)
        {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        }
        catch (SQLException mSQLException)
        {
            throw mSQLException;
        }

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String product = "";
                 Cursor cursor = mDb.rawQuery("SELECT * FROM clients", null);
                 cursor.moveToFirst();
                 while (!cursor.isAfterLast()) {
                     product += cursor.getString(1) + " | ";
                     cursor.moveToNext();
                 }
                 cursor.close();
                 textView.setText(product);
            }
        });
    }

}
