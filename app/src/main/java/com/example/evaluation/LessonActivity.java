package com.example.evaluation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.evaluation.fragments.main.bijeenkomsten;
import com.example.evaluation.models.main.Status;
import com.google.android.material.snackbar.Snackbar;

public class LessonActivity extends AppCompatActivity {

    private Status status;

    private String lessonID;
    private String lessonName;
    private String lessonDate;
    private String lessonTime;
    private String lessonTeacher;

    private Button buttonBack;
    private Button buttonLiked;
    private Button buttonNeutral;
    private Button buttonDisliked;

    private boolean buttonLikedClicked;
    private boolean buttonNeutralClicked;
    private boolean buttonDislikedClicked;

    private Snackbar likeSnackbar;
    private Snackbar neutralSnackbar;
    private Snackbar dislikeSnackbar;

    private Snackbar reviewDeleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        buttonLikedClicked = false;
        buttonNeutralClicked = false;
        buttonDislikedClicked = false;

        loadData();
        setDataInView();
        status = new Status(MainActivity.userID, lessonID, null);


        likeSnackbar = Snackbar.make(findViewById(android.R.id.content), "Uw heeft de les geliked!", Snackbar.LENGTH_LONG);
        neutralSnackbar = Snackbar.make(findViewById(android.R.id.content), "Uw heeft de les neutraal gewardeerd!", Snackbar.LENGTH_LONG);
        dislikeSnackbar = Snackbar.make(findViewById(android.R.id.content), "Uw heeft de les gedisliked!", Snackbar.LENGTH_LONG);

        reviewDeleted = Snackbar.make(findViewById(android.R.id.content), "Uw heeft uw review verwijderd...", Snackbar.LENGTH_LONG);

        buttonBack = (Button) findViewById(R.id.button_terug);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage();
            }
        });

        buttonLiked = (Button) findViewById(R.id.button_like);
        buttonLiked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeButtonClicked();
            }
        });

        buttonNeutral = (Button) findViewById(R.id.button_neutral);
        buttonNeutral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                neutralButtonClicked();
            }
        });

        buttonDisliked = (Button) findViewById(R.id.button_dislike);
        buttonDisliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dislikedButtonClicked();
            }
        });

        checkStatus(status);
    }

    private void checkStatus(Status uncheckedStatus){
        status = MainActivity.checkStatus(uncheckedStatus);

        if(status.getStatus() != null){
            if(status.getStatus().equals("like")){
                System.out.println("LIKED!");
                buttonLikedClicked = true;
                buttonNeutral.setEnabled(false);
                buttonDisliked.setEnabled(false);
            }
            if(status.getStatus().equals("dislike")){
                System.out.println("DISLIKED!");
                buttonDislikedClicked = true;
                buttonLiked.setEnabled(false);
                buttonNeutral.setEnabled(false);
            }
            if(status.getStatus().equals("neutral")){
                System.out.println("NEUTRAL!");
                buttonNeutralClicked = true;
                buttonLiked.setEnabled(false);
                buttonDisliked.setEnabled(false);
            }
        }

    }

    private void backToHomePage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void likeButtonClicked(){
        if(!buttonLikedClicked){
            likeSnackbar.show();
            buttonLikedClicked = true;
            buttonNeutral.setEnabled(false);
            buttonDisliked.setEnabled(false);
            status.setStatus("like");
            MainActivity.likeALesson(status);
        }else{
            reviewDeleted.show();
            buttonLikedClicked = false;
            buttonNeutral.setEnabled(true);
            buttonDisliked.setEnabled(true);
            status.setStatus(null);
            MainActivity.deleteReviewLesson(status);
            MainActivity.unlikeALesson(status);
        }
    }

    private void neutralButtonClicked(){
        if(!buttonNeutralClicked){
            neutralSnackbar.show();
            buttonNeutralClicked = true;
            buttonLiked.setEnabled(false);
            buttonDisliked.setEnabled(false);
            status.setStatus("neutral");
            MainActivity.neutralALesson(status);
        }else{
            reviewDeleted.show();
            buttonNeutralClicked = false;
            buttonLiked.setEnabled(true);
            buttonDisliked.setEnabled(true);
            status.setStatus(null);
            MainActivity.deleteReviewLesson(status);
            MainActivity.unneutralALesson(status);
        }
    }

    private void dislikedButtonClicked(){
        if(!buttonDislikedClicked){
            dislikeSnackbar.show();
            buttonDislikedClicked = true;
            buttonLiked.setEnabled(false);
            buttonNeutral.setEnabled(false);
            status.setStatus("dislike");
            MainActivity.dislikeALesson(status);
        }else{
            reviewDeleted.show();
            buttonDislikedClicked = false;
            buttonLiked.setEnabled(true);
            buttonNeutral.setEnabled(true);
            status.setStatus(null);
            MainActivity.deleteReviewLesson(status);
            MainActivity.undislikeALesson(status);
        }

    }

    private void loadData(){
        Intent intent = getIntent();
        lessonID = intent.getStringExtra(bijeenkomsten.LESSON_ID);
        lessonName = intent.getStringExtra(bijeenkomsten.LESSON_NAME);
        lessonDate = intent.getStringExtra(bijeenkomsten.LESSON_DATE);
        lessonTime = intent.getStringExtra(bijeenkomsten.LESSON_TIME);
        lessonTeacher = intent.getStringExtra(bijeenkomsten.LESSON_TEACHER);
    }

    private void setDataInView(){
        TextView nameText = findViewById(R.id.lesson_text);
        nameText.setText(lessonName);

        TextView idText = findViewById(R.id.lesson_id);
        idText.setText(lessonID);

        TextView dateText = findViewById(R.id.date_text);
        dateText.setText(lessonDate);

        TextView timeText = findViewById(R.id.time_text);
        timeText.setText(lessonTime);

        TextView teacherText = findViewById(R.id.teacher_text);
        teacherText.setText(lessonTeacher);
    }

}
