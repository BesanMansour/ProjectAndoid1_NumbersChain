package com.example.numbers_chain;

public class details_allGame_class {
    private int id;
    private String fullName;
    private   int grade;
    private   String date;


    public details_allGame_class(String fullName, int grade, String date) {
        this.fullName = fullName;
        this.grade = grade;
        this.date = date;
    }

    public details_allGame_class(int grade) {
        this.grade = grade;
    }
    public details_allGame_class(int id, String fullName, int grade, String date) {
        this.id = id;
        this.fullName = fullName;
        this.grade = grade;
        this.date = date;
    }

    public details_allGame_class(String full_name, CharSequence grade, String date_time) {
    }

    public details_allGame_class(String date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String  date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "details_allGame_class{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", grade=" + grade +
                ", date='" + date + '\'' +
                '}';
    }
}
