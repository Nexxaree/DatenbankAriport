package com.example.kroairport;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    public static String db = "flughafenpr.sqlite";
    DBHelper dbHelper;
    DBQueries dbQueries;
    EditText edtName;
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext(), db, null, 1);

        dbQueries = new DBQueries(getApplicationContext());

        edtName = findViewById(R.id.edtText);
        listView = findViewById(R.id.listview);

        readDb();
        Button btn = findViewById(R.id.search);
    }

    private void readDb() {
        if (dbHelper.DatabaseExists()) {
            dbQueries.open();
            adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, dbQueries.getDetail());
            listView.setAdapter(adapter);
            dbQueries.close();
        } else {
            Toast.makeText(getApplicationContext(), "DB does not Exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void search(View view) {
        dbQueries.open();
        adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listview, dbQueries.searchname(edtName.getText().toString()));
        listView.setAdapter(adapter);
        dbQueries.close();
    }

    public void check(View view) {
        if (dbHelper.DatabaseExists()) {
            Toast.makeText(getApplicationContext(), "DB Exists", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "DB does not Exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void importdb(View view) throws IOException {
        dbHelper.importDataBaseFromAssets();
        readDb();
    }

    public void reset(View view){
        edtName.setText("");
    }

    /* public void insert(View view) {
        if (dbHelper.DatabaseExists()) {
            if (edtName.getText().toString().trim().length() > 0) {
                dbQueries.open();
                long success = dbQueries.insertDetail(edtName.getText().toString().trim());
                if (success > -1) {
                    edtName.setText(null);
                    edtName.clearFocus();
                    Toast.makeText(getApplicationContext(),"Successfully Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Insert Failed", Toast.LENGTH_SHORT).show();
                }
                dbHelper.close();
                readDb();
            }else {
                edtName.setError("Enter Name");
            }

        }else {
            edtName.setError("Import DB First");
        }

    } */
}