package com.example.numbers_chain;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.numbers_chain.databinding.ActivityAllGameBinding;

import java.util.ArrayList;
import java.util.Date;

public class AllGameActivity extends AppCompatActivity {
    ActivityAllGameBinding binding ;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static final String FULL_NAME = "fullName";
    public static String full_name;
    public static int grade;
    public static String date_time;
    public MyDatabase myDatabase;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
        editor = sp.edit();

        myDatabase = new MyDatabase(this);
        full_name = sp.getString(FULL_NAME, "null");
        grade = GameActivity.new_score;
        date_time = java.text.DateFormat.getDateTimeInstance().format(new Date());

        ArrayList<details_allGame_class> details = myDatabase.getAllDetails();
        // أوبجكت من الرسايكل اللي عملناها
        // وبدي ابعت ليست البيانات اللي بدي استخدمها اللي انا جيباها من الداتا
        recycle_view adapter = new recycle_view(details, this);
        binding.recycleViewXml.setAdapter(adapter);
        // هل الريسايكل اللي عندي حجمها ثابت ولا ممكن يتغير
        binding.recycleViewXml.setHasFixedSize(true);
        //كلاس هو اللي بكون مدير جوا الادابتر اللي نا عملتو .. هو اللي بيستدعي دالة on create view holder وهو بيعرف متى يستدعي on bind
        binding.recycleViewXml.setLayoutManager(new LinearLayoutManager(this));

    }
}