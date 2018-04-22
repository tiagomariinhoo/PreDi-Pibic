package app.com.example.wagner.meupredi.View.Application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.ParseException;
import java.util.ArrayList;

import app.com.example.wagner.meupredi.Controller.ControllerExercicios;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.ExercicioClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 26/07/2017.
 */

public class Graphics extends AppCompatActivity{


    BarChart barChart;
    Paciente paciente;
    ArrayList<ExercicioClass> exClass;
    ArrayList<BarEntry> barEntries = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_test);
        barChart = (BarChart) findViewById(R.id.bargraph);
        //DatabaseHandler db = new DatabaseHandler (getApplicationContext());

        ControllerExercicios controllerExercicios = new ControllerExercicios(getApplicationContext());

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        Log.d(paciente.get_nome(), " Nome do paciente");
        try {
            exClass = controllerExercicios.getAllExercicios(paciente);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(exClass.size()>0){
            for(int i=0;i<exClass.size();i++){
                barEntries.add(new BarEntry((float)exClass.get(i).getTempo(),i));
            }
        }

        /*barEntries.add(new BarEntry(44f, 0));
        barEntries.add(new BarEntry(88f, 1));
        barEntries.add(new BarEntry(66f, 2));
        barEntries.add(new BarEntry(12f, 3));
        barEntries.add(new BarEntry(19f, 4));
        barEntries.add(new BarEntry(91f, 5));*/

        //ArrayList<IBarDataSet> barDataSet = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(barEntries, "Dates");
        //barDataSet.add((IBarDataSet) barEntries);

        ArrayList<String> theDates = new ArrayList<>();
        /*theDates.add("April");
        theDates.add("May");
        theDates.add("June");
        theDates.add("July");
        theDates.add("August");
        theDates.add("September");*/

        for(int i=0;i<exClass.size();i++){
            theDates.add(String.valueOf(i+1));
        }
/*
        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);

        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
*/
    }

}
