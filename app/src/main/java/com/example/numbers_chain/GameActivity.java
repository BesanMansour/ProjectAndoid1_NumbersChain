package com.example.numbers_chain;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.numbers_chain.databinding.ActivityGameBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class GameActivity extends AppCompatActivity {
    ActivityGameBinding binding;
    public final String FULL_NAME = "fullName";
    public final String AGE_BIRTH = "age";
    public static int grade;
    public static String date_time;
    public static int new_score;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public MyDatabase myDatabase;
    Question question;
    String user;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // عشان اهرف ال tool bar اللي عملناه هو الافتراضي
        setSupportActionBar(binding.toolBar);
        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
        editor = sp.edit();
        myDatabase = new MyDatabase(getBaseContext());

        user = sp.getString(FULL_NAME, "null");
        binding.gameName.setText(user);

        String age = sp.getString(AGE_BIRTH, "null");
        binding.gameAge.setText("[" + age + "]");

        new_game();

        binding.btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_game();
            }
        });
        // لتحويل ملف الصوت لاوبجكت جافا
        final MediaPlayer mediaPlayer_fail = MediaPlayer.create(this, R.raw.fail_sound);
        final MediaPlayer mediaPlayer_win = MediaPlayer.create(this, R.raw.win_sound);

        binding.btnCheckResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int score = Integer.parseInt(binding.gameScoreGrade.getText().toString());
                if (binding.etEnterNumberResult.getText().toString().equals(String.valueOf(question.getHiddenNumber()))) {
                    new_score = score + 1;
                    mediaPlayer_win.start();
                    // بدنا نحول ملف تصميم الكستم توست لأوبجكت  جافا
                    // تحول أوبجكت التصميم لأوبجكت جافا
                    View v = getLayoutInflater().inflate(R.layout.custom_toast, null, false);
                    TextView tv = view.findViewById(R.id.custom_tv_green);
                    //  عملنا أوبجكت من التوس تبعي
                    Toast t = new Toast(getBaseContext());
                    // بتغير الفيو القديمة وبتحط الفيو الجديدة اللي عملناها
                    t.setView(v);
                    // المدة اللي بدنا اياها
                    t.setDuration(Toast.LENGTH_LONG);
                    // عشان نظهر التوست عند تشغيلها
                    t.show();
                    new_game();
                } else {
                    new_score = score;
                    mediaPlayer_fail.start();
                    View v1 = getLayoutInflater().inflate(R.layout.custom_toast_error, null, false);
                    Toast t = new Toast(getBaseContext());
                    t.setView(v1);
                    t.setDuration(Toast.LENGTH_LONG);
                    t.show();
                }
                binding.gameScoreGrade.setText(String.valueOf(new_score));
                myDatabase = new MyDatabase(getBaseContext());
                date_time = java.text.DateFormat.getDateTimeInstance().format(new Date());
                grade = new_score;
                details_allGame_class d = new details_allGame_class(user, grade, date_time);
                myDatabase.insertGame(d);
            }
        });
        int score_data = myDatabase.getScore();
        binding.gameScoreGrade.setText(String.valueOf(score_data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // تحويل اوبجت التصميم لاوبجكت جافا
        MenuInflater inflater = getMenuInflater();
        // بتاخد التصميم من هنا R.menu.menu_main وتحولو لاوبجكت جافا وبتركبو في menu
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // getItemId : يعني هات الأي دي تبع العنصر الل يعملناه
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intent = new Intent(getBaseContext(), settingActivity.class);
                startActivity(intent);
                return true;
            case R.id.Log_out:
                editor.clear();
                editor.apply();
                myDatabase = new MyDatabase(getBaseContext());
                grade = new_score;
                date_time = DateFormat.getDateTimeInstance().format(new Date());
                details_allGame_class d = new details_allGame_class(user, grade, date_time);
                myDatabase.delete(d);
                Intent intent1 = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent1);
                return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDatabase = new MyDatabase(this);
        date_time = DateFormat.getDateTimeInstance().format(new Date());
        details_allGame_class d = new details_allGame_class(user, grade, date_time);
        myDatabase.insertGame(d);
    }
    public void new_game() {
        // عشان اول ما تشتغل الشاشة يكون موجود ارقام عشوائية
        question = Util.generateQuestion();
        String[][] data = question.getData();
        binding.gameNum1.setText(data[0][0]);
        binding.gameNum2.setText(data[0][1]);
        binding.gameNum3.setText(data[0][2]);

        binding.gameNum4.setText(data[1][0]);
        binding.gameNum5Required.setText(data[1][1]);
        binding.gameNum6.setText(data[1][2]);

        binding.gameNum7.setText(data[2][0]);
        binding.gameNum8.setText(data[2][1]);
        binding.gameNum9.setText(data[2][2]);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}