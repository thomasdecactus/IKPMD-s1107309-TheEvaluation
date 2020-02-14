package com.example.evaluation.models.main;

public class Lesson {

    private String lessonID;
    private String name;
    private String date;
    private String time;
    private String teacher;
    private int likesCount;
    private int neutralCount;
    private int dislikesCount;

    public Lesson(){

    }

    public Lesson(String date, int dislikeCount, String lessonID, int likesCount, String name, int neutralCount, String teacher, String time) {
        this.date = date;
        this.dislikesCount = dislikeCount;
        this.lessonID = lessonID;
        this.likesCount = likesCount;
        this.name = name;
        this.neutralCount = neutralCount;
        this.teacher = teacher;
        this.time = time;
    }

    public Lesson(String lessonID, String name, String date, String time, String teacher) {
        this.lessonID = lessonID;
        this.name = name;
        this.date = date;
        this.time = time;
        this.teacher = teacher;
        this.likesCount = 0;
        this.neutralCount = 0;
        this.dislikesCount = 0;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getNeutralCount() {
        return neutralCount;
    }

    public void setNeutralCount(int neutralCount) {
        this.neutralCount = neutralCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        this.dislikesCount = dislikesCount;
    }
}
