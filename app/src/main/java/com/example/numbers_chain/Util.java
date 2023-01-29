package com.example.numbers_chain;

public class Util {
    public static int number;
    // احنا استخدمنا مصفوفة ثنائية عشان بنقدر من خلالها نحدد عددالصفوف والاعمدة
    public static Question generateQuestion(){
        // generateQuestion : هادي الدالة عشان تعالج المصفوفة مع الرقم المخفي وترجع أوبجكت اسمو أكوسشن
        // [3] اول قوس يمثل عدد الصفوف وتاني قوس يمثل عدد الاعمدة
        String [] [] x = new String[3][3];
        int startNumber= (int) ((Math.random()*10)+1);
        int incStartNumber= (int) (Math.random()*5)+1;
        int staredNumber;
        number=-1;
        for (int i=0 ; i<x.length ; i++){
            for (int j=0 ; j<x[i].length ; j++){
                staredNumber=startNumber + incStartNumber;
                if (i==1 && j==1){
                    // عشان المستخدم هو اللي يدخل الرقم المجهول
                    x[i][j] = "??";
                    number=staredNumber;
                }
                else {
                    x[i][j]=staredNumber + "";
                }
                // بيعمل زيادة بكل خانة زائد 2
                incStartNumber+=2;
                startNumber=staredNumber;
            }
        }
        // الاكس : كل الارقام اللي في المصفوفة
        // number :الاجابة الصحيحة اللي راح نفحصهاهل نفس الاجابة اللي دخلها المستخدم ولا لا
        return new Question(x,number);

    }
}
