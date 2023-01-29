package com.example.numbers_chain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.numbers_chain.databinding.ActivityChangePasswordBinding;

public class changePassword extends AppCompatActivity {
ActivityChangePasswordBinding binding;
    public final String PASS_WORD = "passWord";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
        editor = sp.edit();

        binding.changeBtnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sp_pass = sp.getString(PASS_WORD, "null");
                String old_pass = binding.changePassOld.getText().toString();
                String new_pass = binding.changePassNew.getText().toString();
                String new_repass = binding.changeRepassNew.getText().toString();

                if (old_pass.equals("") || new_pass.equals("") || new_repass.equals("")) {
                    Toast.makeText(getBaseContext(), "All Fields must be filled", Toast.LENGTH_LONG).show();
                } else {
                    if (old_pass.equals(sp_pass)) {
                        if (new_pass.equals(new_repass)) {
                            editor.putString(PASS_WORD,new_pass);
                            editor.apply();
                            Toast.makeText(getBaseContext(), "PIN Changed", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), settingActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getBaseContext(), "PIN's Mismatch", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Please Enter exact Old PIN", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });



    }
}