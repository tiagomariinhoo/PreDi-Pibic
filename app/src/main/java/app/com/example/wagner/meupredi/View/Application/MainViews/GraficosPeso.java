package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.R;

public class GraficosPeso extends AppCompatActivity{

    private static final String TAG = "Evolução Peso";
    private LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficos_peso);

        mChart = (LineChart) findViewById(R.id.linechart_peso);

        //mChart.setOnChartGestureListener(GraficosPeso.this);
        //mChart.setOnChartValueSelectedListener(GraficosPeso.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0, 60f));
        yValues.add(new Entry(1, 50f));
        yValues.add(new Entry(2, 70f));
        yValues.add(new Entry(3, 30f));
        yValues.add(new Entry(4, 50f));
        yValues.add(new Entry(5, 60f));
        yValues.add(new Entry(6, 65f));

        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

        set1.setFillAlpha(110);
        set1.setColor(Color.RED);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
    }
}
