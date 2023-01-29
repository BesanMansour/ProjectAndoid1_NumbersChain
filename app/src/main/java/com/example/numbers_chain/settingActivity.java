package com.example.numbers_chain;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.numbers_chain.databinding.ActivitySettingBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Date;

public class settingActivity extends AppCompatActivity {
ActivitySettingBinding binding ;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static String date_time;
    public final String FULL_NAME = "fullName";
    public static String full_name = "fullName";
    public static int grade;
    public MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
        editor = sp.edit();

        binding.settingShowallgame.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                myDatabase = new MyDatabase(getBaseContext());
                myDatabase.getAllDetails();
                Intent intent = new Intent(getBaseContext(), AllGameActivity.class);
                startActivity(intent);
            }
        });
        binding.settingShowlastgamedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase = new MyDatabase(getBaseContext());
                String date = myDatabase.getDate();
                Toast.makeText(getBaseContext(), date , Toast.LENGTH_SHORT).show();
            }
        });
        binding.settingChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settingActivity.this);
                builder.setTitle("Confirm password change ?").
                setMessage("Are you sure ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent1 = new Intent(getBaseContext(), changePassword.class);
                                startActivity(intent1);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(settingActivity.this, "The password will not be changed", Toast.LENGTH_SHORT).show();
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(settingActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        binding.settingClearGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabase = new MyDatabase(getBaseContext());
                full_name = sp.getString(FULL_NAME, "null");
                grade = GameActivity.new_score;
                date_time = java.text.DateFormat.getDateTimeInstance().format(new Date());

                details_allGame_class d = new details_allGame_class(full_name, grade, date_time);
                boolean delete = myDatabase.delete(d);
                if (delete) {
                    Intent intent = new Intent(getBaseContext(), AllGameActivity.class);
                    startActivity(intent);
                    Toast.makeText(settingActivity.this, "Empty Activity", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}