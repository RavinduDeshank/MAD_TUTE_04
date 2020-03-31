package com.example.tutorial_04;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tutorial_04.Database.DBHandler;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText txtuser, txtpass;
    Button btnselect,btnAdd,btnsignIn,btndelete,btnupdate;
    DBHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtuser = findViewById(R.id.editText);
        txtpass = findViewById(R.id.editText2);

        btnAdd = findViewById(R.id.buttonadd);
        btndelete = findViewById(R.id.buttondelete);
        btnselect = findViewById(R.id.buttonselect);
        btnsignIn = findViewById(R.id.buttonsign);
        btnupdate = findViewById(R.id.buttonupdate);

        database = new DBHandler(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtuser.getText().length() <= 0 || txtpass.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(),"Please enter username and password.",Toast.LENGTH_SHORT).show();
                    return;
                }
                database.AddInfo(txtuser.getText().toString(),txtpass.getText().toString());
                Toast.makeText(getApplicationContext(),"Details add successfully.",Toast.LENGTH_SHORT).show();
            }
        });
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> username = database.readAllInfo();

                for (Object user: username) {
                    Log.i("db", (String) user);
                }
            }
        });

        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtuser.getText().length() <= 0 || txtpass.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter username and password.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                List<String> matchList = database.readInfo(txtuser.getText().toString(), txtpass.getText().toString());

                if(matchList.size() > 0){
                    Toast.makeText(getApplicationContext(), "Username and password is correct",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Username and password is wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtuser.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter username to delete",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                database.deleteInfo(txtuser.getText().toString());
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtuser.getText().length() <= 0 || txtpass.getText().length() <= 0){
                    Toast.makeText(getApplicationContext(), "Please enter username and password.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                database.updateInfo(txtuser.getText().toString(), txtpass.getText().toString());
            }
        });
    }
}
