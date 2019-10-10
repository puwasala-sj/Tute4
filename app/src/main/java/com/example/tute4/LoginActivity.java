package com.example.tute4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBHelper;

public class LoginActivity extends AppCompatActivity {

    Button add,signIn,delete,update,selectAll;
    EditText username, password;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBHelper(this);

        username = (EditText)findViewById(R.id.txt1);
        password = (EditText)findViewById(R.id.txt2);

        selectAll = (Button)findViewById(R.id.select);
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.readAllInfo();
                if(res.getCount() == 0){
                    Toast.makeText(LoginActivity.this,"Empty",Toast.LENGTH_SHORT).show();
                }

                StringBuffer buffer = new StringBuffer();
                    while(res.moveToNext()) {
                        buffer.append("Id: " + res.getInt(0) + "\n");
                        buffer.append("Username: " + res.getString(1) + "\n");
                        buffer.append("Password: " + res.getString(2) + "\n\n");
                    }
                    showMessage("Users",buffer.toString());
            }
        });

        add = (Button)findViewById(R.id.btn1);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passWord = password.getText().toString();

                boolean res = db.addInfo(userName,passWord);
                if(res == true){
                    Toast.makeText(LoginActivity.this,"Added Successfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        signIn = (Button)findViewById(R.id.btn2);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passWord = password.getText().toString();

                boolean res = db.checkUser(userName,passWord);
                if(res == true){
                    Toast.makeText(LoginActivity.this,"Successfully logged in", Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(LoginActivity.this,SecondActivity.class);
                    startActivity(login);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete = (Button)findViewById(R.id.btn3);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();

                db.deleteInfo(userName);
                Toast.makeText(LoginActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
            }
        });

        update = (Button)findViewById(R.id.btn4);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = username.getText().toString();
                String passWord = password.getText().toString();

                db.updateInfo(userName,passWord);
                Toast.makeText(LoginActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
