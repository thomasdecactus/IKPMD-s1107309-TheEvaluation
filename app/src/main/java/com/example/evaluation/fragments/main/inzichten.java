package com.example.evaluation.fragments.main;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.evaluation.MainActivity;
import com.example.evaluation.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class inzichten extends Fragment {

    private View viewObject;
    private PieChart mChart;
    private CombinedChart bChart;
    public static int aantalLikes;
    public static int aanntalDislikes;
    public static int aantalNeutraal;
    public static int totaalAantalSubmits;

    public inzichten() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewObject = inflater.inflate(R.layout.fragment_inzichten, container, false);

        //Piechart 1
        setupPieChart(viewObject);
        setData(0);

        return viewObject;
    }

    protected void setupPieChart(View viewObject){
        mChart = (PieChart) viewObject.findViewById(R.id.chart);
        Description d = new Description();
        d.setText("Inzicht likes, neutraal en dislikes");
        d.setTextSize(20f);
        mChart.setDescription(d);
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
    }


    public void setData(int aantal) {
        aantalLikes = MainActivity.totalLikeCount;
        aanntalDislikes = MainActivity.totalDislikeCount;
        aantalNeutraal = MainActivity.totalNeutralCount;
        totaalAantalSubmits = aantalLikes + aanntalDislikes + aantalNeutraal;

        List<PieEntry> yValues = new ArrayList<>();
        List<PieEntry> xValues = new ArrayList<>();

        yValues.add(new PieEntry(aantalLikes, 1));
        xValues.add(new PieEntry(aantalLikes, "Aantal likes"));

        yValues.add(new PieEntry(aanntalDislikes, 2));
        xValues.add(new PieEntry(aanntalDislikes, "Aantal dislikes"));

        yValues.add(new PieEntry(aantalNeutraal, 3));
        xValues.add(new PieEntry(aantalNeutraal, "Aantal neutraal"));

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.rgb(26,255,26));
        colors.add(Color.rgb(214,214,194));
        colors.add(Color.rgb(255,26,26));


        PieDataSet dataSet = new PieDataSet(yValues, "Reviews");
        dataSet.setColors(colors);//colors);

        PieData data = new PieData(dataSet);
        mChart.setData(data);
    }


}
