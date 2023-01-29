package com.example.numbers_chain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    public static final String DB_GAME = "data_game";
    public static final int DB_VERSION = 1;

    public static final String TB_GAME = "tb_game";
    public static final String ID = "id";
    public static final String FULL_NAME = "full_name";
    public static final String GRADE_score = "grade";
    public static final String DATE_TIME = "date_time";
    String date_time_column;


    public MyDatabase(Context context) {
        super(context, DB_GAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TB_GAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + FULL_NAME + " TEXT ," + GRADE_score + " INTEGER " +
                "," + DATE_TIME + " TEXT )");
     }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean insertGame(details_allGame_class details) {
        //مؤشر على الداتابيز
        SQLiteDatabase sql = getWritableDatabase();
        //عشان اضيف البيانات
        ContentValues values = new ContentValues();

        values.put(FULL_NAME, details.getFullName());
        values.put(GRADE_score, details.getGrade());
        values.put(DATE_TIME, details.getDate());
        Long result = sql.insert(TB_GAME, null, values);
        // رجعلي اذا كان لا يساوي -1
        return result != -1;
    }
    // داله الاسترجاع
    // بترجعلي اياهم على شكل مصفوفة لانو انا بدي استرجع مجموعة سيارات مش سيارة واحدة0
    //وبنستخدم أرري ليست لانها افضل من ناحية الحجم
    public ArrayList<details_allGame_class> getAllDetails() {
        ArrayList<details_allGame_class> details = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        //rawQuery : داله بتاخد شوية اعدادات اخرى وبترجع اوبجكت نوعو كيرسر
        // الكيرسر حاجة شبيهة بالمصفوفة
        //ما حطينا شرط لانو انا بدي اجيب كل البيانات فمش محتاجة لشرط
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TB_GAME, null);
        //كود التعامل مع الكيرسر وتحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي على بيانات ام لا
        if (cursor.moveToFirst()) {
            do {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID));
                String fullName = cursor.getString(cursor.getColumnIndexOrThrow(FULL_NAME));
                int grade = cursor.getInt(cursor.getColumnIndexOrThrow(GRADE_score));
                String date_time = cursor.getString(cursor.getColumnIndexOrThrow(DATE_TIME));

           details.add(new details_allGame_class(fullName,grade,date_time));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return details;
    }
    public String getDate() {
//        ArrayList<details_allGame_class> details = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        //rawQuery : داله بتاخد شوية اعدادات اخرى وبترجع اوبجكت نوعو كيرسر
        // الكيرسر حاجة شبيهة بالمصفوفة
        //ما حطينا شرط لانو انا بدي اجيب كل البيانات فمش محتاجة لشرط
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + DATE_TIME + " FROM " + TB_GAME, null);
        //كود التعامل مع الكيرسر وتحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي على بيانات ام لا
        if (cursor.moveToLast()) {
            date_time_column = cursor.getString(cursor.getColumnIndexOrThrow(DATE_TIME));
//                details_allGame_class c = new details_allGame_class(date_time);
//                details.add(c);
        }
        else {
            date_time_column = " " ;
        }
        cursor.close();
        return date_time_column;
    }
    public int getScore() {
        int score ;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        //rawQuery : داله بتاخد شوية اعدادات اخرى وبترجع اوبجكت نوعو كيرسر
        // الكيرسر حاجة شبيهة بالمصفوفة
        //ما حطينا شرط لانو انا بدي اجيب كل البيانات فمش محتاجة لشرط
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT " + GRADE_score + " FROM " + TB_GAME, null);
        //كود التعامل مع الكيرسر وتحويله لمصفوفة من نوع كار
        //فحص هل الكيرسر يحتوي على بيانات ام لا
        if (cursor.moveToLast()) {
            score = cursor.getInt(cursor.getColumnIndexOrThrow(GRADE_score));
        }
        else {
            score = 0 ;
        }
        cursor.close();
        return score;
    }
    // داله الحذف
    // داله الحذف : هي عملية تعديل
    public boolean delete(details_allGame_class detailsDelete) {
        SQLiteDatabase sql = getWritableDatabase();
        int result = sql.delete(TB_GAME, null, null);
        sql.close();
        // بترجع 0 لمن ما يحذف ولا جدول
        return result > 0;
    }

}
