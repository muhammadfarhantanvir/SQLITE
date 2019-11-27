package com.example.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName,etCell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etCell = findViewById(R.id.etCell);
    }

    public void btnSaveData(View v)
    {
        ContactsDB db = new ContactsDB(this);
        db.open();
        db.createEntry(etName.getText().toString(), etCell.getText().toString());
        etName.setText("");
        etCell.setText("");
        Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
        db.close();

    }
    public void btnShowData(View v)
    {
        startActivity(new Intent(this, Data.class));

    }
    public void btnEditData(View v)
    {

    }
    public void btnDeleteData(View v)
    {

    }
}
