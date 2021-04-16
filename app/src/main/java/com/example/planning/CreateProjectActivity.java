package com.example.planning;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateProjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_project);

        String[] investors = {"Petro", "Oleg", "Nataly"};
        int[] invest = {100, 150, 400};

        PieChart chart = findViewById(R.id.pie_Chart);

        ArrayList<PieEntry> cost = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            cost.add(new PieEntry(invest[i], investors[i]));
        }

        PieDataSet pieDataSet = new PieDataSet(cost, "Cost stream");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        chart.setData(pieData);
        chart.getDescription().setEnabled(false);
        chart.setCenterText("Cost stream");
        chart.animate();
    }
}