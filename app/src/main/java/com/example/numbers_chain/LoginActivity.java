package com.example.numbers_chain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.numbers_chain.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    public static String user_login, pass_login;
    Intent intent;
    public final String USER_NAME = "userName";
    public final String PASS_WORD = "passWord";
    public final String REMEMBER_ME = "remember";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
        editor = sp.edit();

        binding.loginBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        // حطينا الكود هادا في المين عشان في كل مرة يفتح الشاشة هادي يشوف الشيرد شو مخزنة ويعرف ينقل ولا لا
        // هات القيمة اللي خزناها في الشيرد
        String checkbox_remember = sp.getString(REMEMBER_ME, "null");
        if (checkbox_remember.equals("true")) {
            // اذا كانت ترو ينتقل على شاشة الالعاب
            Intent intent1 = new Intent(getBaseContext(), GameActivity.class);
            startActivity(intent1);
        } else if (checkbox_remember.equals("false")) {
            // اذا كان خطا يطلع توست
            Toast.makeText(this, "Please sign in", Toast.LENGTH_SHORT).show();
        }
        binding.loginBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sp_user = sp.getString(USER_NAME, "null");
                String sp_pass = sp.getString(PASS_WORD, "null");

                user_login = binding.loginUsername.getText().toString();
                pass_login = binding.loginPassword.getText().toString();

                if (sp_user.equals(user_login) && sp_pass.equals(pass_login)) {
                    Intent intent1 = new Intent(getBaseContext(), GameActivity.class);
                    startActivity(intent1);
                } else if (!sp_user.equals(user_login) && !sp_pass.equals(pass_login)) {
                    binding.loginUsername.setError("Please confirm your username");
                    binding.loginPassword.setError("Please confirm your password");
                } else if (!sp_user.equals(user_login) && sp_pass.equals(pass_login)) {
                    binding.loginUsername.setError("Please confirm your username");
                } else if (sp_user.equals(user_login) && !sp_pass.equals(pass_login)) {
                    binding.loginPassword.setError("Please confirm your password");
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.loginCheckboxRememmber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                user_login = binding.loginUsername.getText().toString();
                pass_login = binding.loginPassword.getText().toString();
                if (user_login.isEmpty() && pass_login.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please confirm your username and password", Toast.LENGTH_SHORT).show();
                } else {
                    //compoundButton : هادا المربع اللي بكون في مربع الاختيار
                    if (compoundButton.isChecked()) {
                        // اذا ضغط على الشيكد بوكس حزن في الشيرد ترو
                        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString(REMEMBER_ME, "true");
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                    } else if (!compoundButton.isChecked()) {
                        // اذا ضغط على الشيكد بوكس حزن في الشيرد فولس
                        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
                        editor = sp.edit();
                        editor.putString(REMEMBER_ME, "false");
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}