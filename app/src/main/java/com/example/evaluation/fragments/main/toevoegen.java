package com.example.evaluation.fragments.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.evaluation.MainActivity;
import com.example.evaluation.R;
import com.example.evaluation.models.main.Lesson;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 */
public class toevoegen extends Fragment{

    private View viewObject;
    private TextInputLayout name_lesson;
    private TextInputLayout date;
    private TextInputLayout time;
    private TextInputLayout name_teacher;
    private Button submit_button;
    private Snackbar snackbar;

    public toevoegen() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewObject = inflater.inflate(R.layout.fragment_toevoegen, container, false);

        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "Uw les is toegevoegd!", Snackbar.LENGTH_LONG);

        name_lesson = viewObject.findViewById(R.id.input_text_name_lesson);
        date = viewObject.findViewById(R.id.input_text_date);
        time = viewObject.findViewById(R.id.input_text_time);
        name_teacher = viewObject.findViewById(R.id.input_text_name_teacher);
        submit_button = viewObject.findViewById(R.id.submit_button);

        submit_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                System.out.println("Lesson data recieved");
                createLesson();
                System.out.println("Lesson created");
                formInputSucceded();
                System.out.println("Lesson data saved");
             }
         }
        );
        return viewObject;
    }

    public void createLesson(){
        String lessonID = UUID.randomUUID().toString();
        String lessonName = name_lesson.getEditText().getText().toString();
        String lessonDate = date.getEditText().getText().toString();
        String lessonTime = time.getEditText().getText().toString();
        String lessonTeacher = name_teacher.getEditText().getText().toString();
        Lesson newLes = new Lesson(lessonID,lessonName,lessonDate ,lessonTime, lessonTeacher);
        MainActivity.uploadNewLesson(newLes);
    }

    public void formInputSucceded(){
        snackbar.show();
        name_lesson.getEditText().getText().clear();
        date.getEditText().getText().clear();
        time.getEditText().getText().clear();
        name_teacher.getEditText().getText().clear();
    }

}
