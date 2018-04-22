package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 01/03/2018.
 */

//https://github.com/PhilJay/MPAndroidChart


public class GraphTest extends Activity {

    private static final String TAG = "MainActivity";
    private LineChart nChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_test_layout);
        nChart = (LineChart) findViewById(R.id.lineChart);

        Log.d("ENTROU NO GRAPH TEST", "AIJDIAWDAWD");
        //nChart.setOnChartGestureListener(GraphTest.this);
        //nChart.setOnChartValueSelectedListener(GraphTest.this);

        nChart.setDragEnabled(true);
        nChart.setScaleEnabled(false);

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
        set1.setLineWidth(3f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        nChart.setData(data);


    }
}
