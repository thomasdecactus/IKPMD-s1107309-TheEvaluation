package com.example.evaluation.fragments.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.evaluation.LessonActivity;
import com.example.evaluation.MainActivity;
import com.example.evaluation.R;
import com.example.evaluation.models.main.Lesson;
import com.example.evaluation.ui.main.PageViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */

public class bijeenkomsten extends Fragment {

    public static final String LESSON_NAME = "com.evaluation.application.LESSON_NAME";
    public static final String LESSON_ID = "com.evaluation.application.LESSON_ID";
    public static final String LESSON_DATE = "com.evaluation.application.LESSON_DATE";
    public static final String LESSON_TIME = "com.evaluation.application.LESSON_TIME";
    public static final String LESSON_TEACHER = "com.evaluation.application.LESSON_TEACHER";

    private String lessonName;
    private String lessonID;
    private String lessonDate;
    private String lessonTime;
    private String lessonTeacher;

    private ArrayList<Lesson> lessons;
    private PageViewModel pageViewModel;
    private View viewObject;

    public bijeenkomsten() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewObject = inflater.inflate(R.layout.fragment_bijeenkomsten, container, false);

        setupData();
        System.out.println("LESSSSONS LOADED:");
        System.out.println(lessons);

        LinearLayout ll = (LinearLayout) viewObject.findViewById(R.id.layout_sub);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int i=0;i< lessons.size() ;i++){
            final Button myButton = new Button(getContext());
            myButton.setText(lessons.get(i).getName());
            myButton.setId(i);

            final Lesson newLesson = new Lesson(lessons.get(i).getLessonID(), lessons.get(i).getName(), lessons.get(i).getDate(), lessons.get(i).getTime(), lessons.get(i).getTeacher());

            myButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //openLessonActivity(lessonName, lessonID, lessonDate, lessonTime, lessonTeacher);
                            openLessonActivity(newLesson);
                        }
                    }
            );
            ll.addView(myButton, lp);
        }

        return viewObject;
    }

    public void setupData(){
        this.lessons = MainActivity.lessons;
    }


    public void openLessonActivity(Lesson les){
        Intent intent = new Intent(getContext(), LessonActivity.class);
        intent.putExtra(LESSON_NAME, les.getName());
        intent.putExtra(LESSON_ID, les.getLessonID());
        intent.putExtra(LESSON_DATE, les.getDate());
        intent.putExtra(LESSON_TIME, les.getTime());
        intent.putExtra(LESSON_TEACHER, les.getTeacher());
        startActivity(intent);
    }




}
