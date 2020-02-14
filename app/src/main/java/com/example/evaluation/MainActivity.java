package com.example.evaluation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.evaluation.models.main.Lesson;
import com.example.evaluation.models.main.Status;
import com.example.evaluation.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    static public String userID;
    static public  FirebaseDatabase firebaseDatabase;
    static private DatabaseReference lessonsDatabaseReference;
    static private DatabaseReference statusDatabaseReference;

    static private DatabaseReference counterDatabaseReference;
    static private DatabaseReference counterLikeDatabaseReference;
    static private DatabaseReference counterDislikeDatabaseReference;
    static private DatabaseReference counterNeutralDatabaseReference;

    static public int totalLikeCount = 0;
    static public int totalDislikeCount = 0;
    static public int totalNeutralCount = 0;

    static public ArrayList<Lesson> lessons = new ArrayList<>();
    static public ArrayList<Status> statusus = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        firebaseDatabase = FirebaseDatabase.getInstance();
        lessonsDatabaseReference = firebaseDatabase.getReference("Lessons");
        statusDatabaseReference = firebaseDatabase.getReference("Status");
        counterDatabaseReference = firebaseDatabase.getReference("Counter");
        counterLikeDatabaseReference = firebaseDatabase.getReference("Counter/like");
        counterDislikeDatabaseReference = firebaseDatabase.getReference("Counter/dislike");
        counterNeutralDatabaseReference = firebaseDatabase.getReference("Counter/neutral");

        readLessons();
        readStatus();
        readCounter();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        userID = sharedPref.getString("userID", null);

        System.out.println("SEARCHING FOR USER ID...");
        if(userID == null){
            System.out.println("No user ID found. Creating one!");
            generateUserID(sharedPref);
        }
        else{
            System.out.println("User ID found! UserID is: " + userID);
        }
    }

    public void generateUserID(SharedPreferences sharedPref){
        SharedPreferences.Editor editor = sharedPref.edit();
        String newUserID = UUID.randomUUID().toString();
        editor.putString("userID", newUserID);
        editor.commit();
        System.out.println("Created a new userID! UserID is: " + newUserID);
    }

    public void readLessons(){
        lessonsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Lessons:");

                lessons.removeAll(lessons);

                for(DataSnapshot lessonSnapshot : dataSnapshot.getChildren()){
                    Lesson les = lessonSnapshot.getValue(Lesson.class);
                    lessons.add(les);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readStatus(){
        statusDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("statusus");

                statusus.removeAll(statusus);

                for(DataSnapshot statusSnapshot : dataSnapshot.getChildren()){
                    Status status = statusSnapshot.getValue(Status.class);
                    statusus.add(status);
                    System.out.println("STATUSL ");
                    System.out.println(status);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static public void readCounter(){
        counterLikeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalLikeCount = Integer.parseInt(dataSnapshot.getValue().toString());
                System.out.println("Total Likes: " + totalLikeCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        counterDislikeDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalDislikeCount = Integer.parseInt(dataSnapshot.getValue().toString());
                System.out.println("Total Dislikes: " + totalDislikeCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        counterNeutralDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalNeutralCount = Integer.parseInt(dataSnapshot.getValue().toString());
                System.out.println("Total Neutral: " + totalNeutralCount);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static public void uploadNewLesson(Lesson newLesson){
        DatabaseReference newLessonDatabaseReference = firebaseDatabase.getReference("Lessons" + "/" + newLesson.getLessonID());
        newLessonDatabaseReference.setValue(newLesson);
    }

    static public void likeALesson(Status status){
        DatabaseReference likeLessonDatabaseReference = firebaseDatabase.getReference("Status" + "/" + status.getLessonID());
        likeLessonDatabaseReference.setValue(status);
        totalLikeCount++;
        counterLikeDatabaseReference.setValue(totalLikeCount);

        int numberOfLikes = 0;

        for(int i = 0; i < lessons.size(); i++ ){
            if(lessons.get(i).getLessonID().equals(status.getLessonID())){
                numberOfLikes = lessons.get(i).getLikesCount();
            }
        }

        DatabaseReference likeSpecificLessonDatabaseReference = firebaseDatabase.getReference("Lessons" + "/" + status.getLessonID() + "/" + "likesCount");
        likeSpecificLessonDatabaseReference.setValue(numberOfLikes + 1);
    }

    static public void unlikeALesson(Status status){
        totalLikeCount--;
        counterLikeDatabaseReference.setValue(totalLikeCount);

        int numberOfLikes = 0;

        for(int i = 0; i < lessons.size(); i++ ){
            if(lessons.get(i).getLessonID().equals(status.getLessonID())){
                numberOfLikes = lessons.get(i).getLikesCount();
            }
        }

        DatabaseReference likeSpecificLessonDatabaseReference = firebaseDatabase.getReference("Lessons" + "/" + status.getLessonID() + "/" + "likesCount");
        likeSpecificLessonDatabaseReference.setValue(numberOfLikes - 1);
    }

    static public void dislikeALesson(Status status){
        DatabaseReference dislikeLessonDatabaseReference = firebaseDatabase.getReference("Status" + "/" + status.getLessonID());
        dislikeLessonDatabaseReference.setValue(status);
        totalDislikeCount++;
        counterDislikeDatabaseReference.setValue(totalDislikeCount);

        int numberOfDislikes = 0;

        for(int i = 0; i < lessons.size(); i++ ){
            if(lessons.get(i).getLessonID().equals(status.getLessonID())){
                numberOfDislikes = lessons.get(i).getDislikesCount();
            }
        }

        DatabaseReference likeSpecificLessonDatabaseReference = firebaseDatabase.getReference("Lessons" + "/" + status.getLessonID() + "/" + "dislikesCount");
        likeSpecificLessonDatabaseReference.setValue(numberOfDislikes + 1);
    }

    static public void undislikeALesson(Status status){
        totalDislikeCount--;
        counterDislikeDatabaseReference.setValue(totalDislikeCount);

        int numberOfDislikes = 0;

        for(int i = 0; i < lessons.size(); i++ ){
            if(lessons.get(i).getLessonID().equals(status.getLessonID())){
                numberOfDislikes = lessons.get(i).getDislikesCount();
            }
        }

        DatabaseReference likeSpecificLessonDatabaseReference = firebaseDatabase.getReference("Lessons" + "/" + status.getLessonID() + "/" + "dislikesCount");
        likeSpecificLessonDatabaseReference.setValue(numberOfDislikes - 1);
    }

    static public void neutralALesson(Status status){
        DatabaseReference neurtralLessonDatabaseReference = firebaseDatabase.getReference("Status" + "/" + status.getLessonID());
        neurtralLessonDatabaseReference.setValue(status);
        totalNeutralCount++;
        counterNeutralDatabaseReference.setValue(totalNeutralCount);

        int numberOfNeutral = 0;

        for(int i = 0; i < lessons.size(); i++ ){
            if(lessons.get(i).getLessonID().equals(status.getLessonID())){
                numberOfNeutral = lessons.get(i).getNeutralCount();
            }
        }

        DatabaseReference likeSpecificLessonDatabaseReference = firebaseDatabase.getReference("Lessons" + "/" + status.getLessonID() + "/" + "neutralCount");
        likeSpecificLessonDatabaseReference.setValue(numberOfNeutral + 1);
    }

    static public void unneutralALesson(Status status){
        totalNeutralCount--;
        counterNeutralDatabaseReference.setValue(totalNeutralCount);

        int numberOfNeutral = 0;

        for(int i = 0; i < lessons.size(); i++ ){
            if(lessons.get(i).getLessonID().equals(status.getLessonID())){
                numberOfNeutral = lessons.get(i).getNeutralCount();
            }
        }

        DatabaseReference likeSpecificLessonDatabaseReference = firebaseDatabase.getReference("Lessons" + "/" + status.getLessonID() + "/" + "neutralCount");
        likeSpecificLessonDatabaseReference.setValue(numberOfNeutral - 1);
    }

    static public void deleteReviewLesson(Status status){
        DatabaseReference neurtralLessonDatabaseReference = firebaseDatabase.getReference("Status" + "/" + status.getLessonID());
        neurtralLessonDatabaseReference.setValue(status);
    }

    static public Status checkStatus(Status status){

        System.out.println("CHECKING STATUS!");

        for(int y = 0; y < statusus.size(); y++){
            System.out.println("CHECK " + y +": " + statusus.get(y).getLessonID() + " AND " + status.getLessonID());
            if(statusus.get(y).getLessonID().equals(status.getLessonID())){
                System.out.println("FOUND LESSONID!");
                if(statusus.get(y).getUserID().equals(status.getUserID()) ){
                    System.out.println("FOUND USERID, RETURNING STATUS!");
                    return statusus.get(y);
                }
            }
        }

        System.out.println("NO MATCH FOUND!!");

        return new Status(status.getUserID(),status.getLessonID(),null);
    }


}