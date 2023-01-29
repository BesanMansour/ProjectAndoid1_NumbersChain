package com.example.numbers_chain;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.numbers_chain.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ActivityMainBinding binding;
    public final String FULL_NAME = "fullName";
    public final String EMAIL_ADDRESS = "emailAddress";
    public final String USER_NAME = "userName";
    public final String PASS_WORD = "passWord";
    public final String REPASS_WORD = "repassWord";
    public final String BIRTH_DATE = "birthDate";
    public final String AGE_BIRTH = "age";
    public final String IMG_VIEW = "img";
    String img_save ;
    ActivityResultLauncher<String> ar1;

    public static String age;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // عشان المستخدم بضغط على الصورة وراح يختار صورة من المعرض وبترجع الصورة ع التطبيق فبنستخدم اكتيفيتي ريزلت لانشر
       ar1 = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    //GetContent: اختيار صورة من المعرض
                    @Override
                    public void onActivityResult(Uri result) {
                        binding.registerImg.setImageURI(result);
                        img_save = result.toString();
                    }
                });
        ActivityResultLauncher<String> permission =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if(result){
                            binding.registerImg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ar1.launch("image/*");
                                }
                            });
                        }
                        else {
                            Snackbar.make(binding.getRoot(),"Gallery will not be accessed",Snackbar.LENGTH_LONG).show();
//                            Toast.makeText(MainActivity.this, "You did not allow access to the gallery", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        permission.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

        binding.registerCalenderDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                // بجيب التاريخ الحالي
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        //عبارة عن ليسنر لمن اضغط على عنصر في الكليندر يروح ينفذلي اياه
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            // ليسنر على دالة أون ديت ست
                            // تستدعى لمن المستخدم يختار تاريخ
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                binding.registerCalenderDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                // الشهر زائد 1 لانو الشهر في الكلندر بيبدا من صفر

                                age = String.valueOf(now.get(Calendar.YEAR)-year);
                            }
                        },
                        // عشان اول ما يفتح الديت بيكر يفتح على الوقت الحالي
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
                // If you're calling this from a support Fragment
                dpd.show(getSupportFragmentManager(), "Datepickerdialog");
                // If you're calling this from an AppCompatActivity
                // dpd.show(getSupportFragmentManager(), "Datepickerdialog");
            }
        });
        String[] type = new String[]{"Palestine","Egypt","Syria","Lebanon","Jordan","Turkey"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.drop_down_item,
                type
        );
       binding.registerSpinner.setAdapter(adapter);

       // استخدمنا النوع التاني لانو انا بدي اياه بس على مستوى المشروع ما بدي حد من برا التطبيق يصلو
        // لارجاع مؤشر على الملف الخاص حسب الاسم المدخل
        sp = getSharedPreferences("register_activity", MODE_PRIVATE);
        // هات الايديتور الخاص بالملف هادا
        editor = sp.edit();

        binding.registerBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = binding.registerFullname.getText().toString();
                String userName = binding.registerUsername.getText().toString();
                String emailAddress = binding.registerEtEmailAddress.getText().toString();
                String passWord = binding.registerPassword.getText().toString();
                String repassWord = binding.registerRePassword.getText().toString();
                String birthDate = binding.registerCalenderDate.getText().toString();

                editor.putString(FULL_NAME, fullName);
                editor.putString(USER_NAME, userName);
                editor.putString(EMAIL_ADDRESS, emailAddress);
                editor.putString(PASS_WORD, passWord);
                editor.putString(REPASS_WORD, repassWord);
                editor.putString(BIRTH_DATE, birthDate);
                editor.putString(IMG_VIEW, img_save);
                editor.putString(AGE_BIRTH,age);
                editor.apply();

                if (passWord.equals(repassWord) && isValidEmail(emailAddress) && !fullName.isEmpty()
                        && !userName.isEmpty() && !TextUtils.isEmpty(img_save)) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    if (!passWord.equals(repassWord)){
                        binding.registerPassword.setError("Please check the Password");
                        binding.registerRePassword.setError("Please check the RePassword");
                    }
                    else if (!isValidEmail(emailAddress)) {
                        binding.registerEtEmailAddress.setError("Please check the email");
                    }
                    else if (fullName.isEmpty()) {
                        binding.registerFullname.setError("Check the full name");
                    }
                    else if (userName.isEmpty()) {
                        binding.registerUsername.setError("Check the user name");
                    }
                    else if (TextUtils.isEmpty(img_save)) {
                        Toast.makeText(MainActivity.this, "Please select a picture", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            // في كلاس Patterns هل الايميل مطابق للمتسلسل target
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}