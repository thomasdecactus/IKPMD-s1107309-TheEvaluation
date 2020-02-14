package com.example.evaluation.models.main;

public class Status {

    private String userID;
    private String lessonID;
    private String status;

    public Status(){

    }

    public Status(String userID, String lessonID, String status) {
        this.userID = userID;
        this.lessonID = lessonID;
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
