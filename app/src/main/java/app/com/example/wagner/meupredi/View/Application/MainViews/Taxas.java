package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerExames;
import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 25/07/2017.
 */

public class Taxas  extends AppCompatActivity implements OnChartGestureListener, OnChartValueSelectedListener {

    Paciente paciente;
    TextView  glicoseJejum, glicose75, hemoglobinaGlicolisada;
    EditText novaGlicose75, novaGlicoseJejum, novaHemoglobinaGlicolisada;
    Button atualizarTaxas;
    private LineChart mChart;

    FragmentTransaction transaction;
    FragmentManager fragmentManager;

    Class fragmentClasse = null;
    private Fragment MyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        Log.d("TELA TAXAS : " , "<<<<<");
        Log.d("GlicoseJejum : ", String.valueOf(paciente.get_glicosejejum()));
        Log.d("Glicose75g : ", String.valueOf(paciente.get_glicose75g()));
        Log.d("Colesterol : ", String.valueOf(paciente.get_colesterol()));
        Log.d("Lipidograma : ", String.valueOf(paciente.get_lipidograma()));
        Log.d("Hemograma : ", String.valueOf(paciente.get_hemograma()));
        Log.d("Tireoide : ", String.valueOf(paciente.get_tireoide()));

        glicoseJejum = (TextView) findViewById(R.id.text_glicoseJejumAtual_taxas);
        glicoseJejum.setText(String.valueOf(paciente.get_glicosejejum()) + " mg/dL");

        glicose75 = (TextView) findViewById(R.id.text_glicose75gAtual_taxas);
        glicose75.setText(String.valueOf(paciente.get_glicose75g()) + " mg/dL");

        hemoglobinaGlicolisada = (TextView) findViewById(R.id.text_hemoglobina_glicolisadaAtual_taxas);
        hemoglobinaGlicolisada.setText(String.valueOf(paciente.get_hemoglobinaglicolisada()) + " %");

        novaGlicoseJejum = (EditText) findViewById(R.id.edit_glicoseJejum_taxas);
        novaGlicoseJejum.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novaGlicose75 = (EditText) findViewById(R.id.edit_glicose75g_taxas);
        novaGlicose75.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novaHemoglobinaGlicolisada = (EditText) findViewById(R.id.edit_hemoglobina_glicolisada_taxas);
        novaHemoglobinaGlicolisada.setRawInputType(Configuration.KEYBOARD_QWERTY);

        findViewById(R.id.tela_taxas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novaGlicose75.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(novaGlicoseJejum.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(novaHemoglobinaGlicolisada.getWindowToken(), 0);
                }
            }
        });

        atualizarTaxas = (Button) findViewById(R.id.btn_atualizar_taxas);

        atualizarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(novaGlicoseJejum.getText().toString().length() != 0) {

                    String novaGJ = novaGlicoseJejum.getText().toString();

                    novaGJ = novaGJ.replace(',', '.');
                    Double gJAtualizada = Double.parseDouble(novaGJ);
                    String gJFormatada = String.format(Locale.ENGLISH, "%.2f", gJAtualizada);
                    Double gJDoPaciente = Double.parseDouble(gJFormatada);

                    glicoseJejum.setText(String.valueOf(gJDoPaciente) + " mg/dL");
                    Log.d("GJejum : ", glicoseJejum.getText().toString());

                    paciente.set_glicosejejum(gJDoPaciente);

                } else {
                    paciente.set_glicosejejum(0);
                }

                if(novaGlicose75.getText().toString().length() != 0) {

                    String novaG75 = novaGlicose75.getText().toString();

                    novaG75 = novaG75.replace(',' , '.');
                    Double g75Atualizada = Double.parseDouble(novaG75);
                    String g75Formatada = String.format(Locale.ENGLISH, "%.2f", g75Atualizada);
                    Double g75DoPaciente = Double.parseDouble(g75Formatada);

                    glicose75.setText(String.valueOf(g75DoPaciente) + " mg/dL");

                    Log.d("Gli75 : ", glicose75.getText().toString());

                    paciente.set_glicose75g(g75DoPaciente);

                } else {
                    paciente.set_glicose75g(0);
                }

                if(novaHemoglobinaGlicolisada.getText().toString().length() != 0) {

                    String novaHG = novaHemoglobinaGlicolisada.getText().toString();

                    novaHG = novaHG.replace(',', '.');
                    Double hgAtualizada = Double.parseDouble(novaHG);
                    String hgFormatada = String.format(Locale.ENGLISH, "%.2f", hgAtualizada);
                    Double hgDoPaciente = Double.parseDouble(hgFormatada);

                    hemoglobinaGlicolisada.setText(String.valueOf(hgDoPaciente) + " mg/dL");

                    Log.d("HG : ", hemoglobinaGlicolisada.getText().toString());

                    paciente.set_hemoglobinaglicolisada(hgDoPaciente);

                } else {
                    paciente.set_hemoglobinaglicolisada(0);
                }

                //atualiza dados no banco de taxas e nos dados do paciente
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                ControllerExames controllerExames = new ControllerExames(getApplicationContext());
                controllerExames.atualizarTaxas(paciente);

                Toast.makeText(getApplicationContext(),"Taxas atualizadas com sucesso!",Toast.LENGTH_SHORT).show();
                paciente.calculo_diabetes(getApplicationContext());
                novaGlicoseJejum.setText("");
                novaGlicose75.setText("");
                novaHemoglobinaGlicolisada.setText("");

                Intent intent = new Intent(Taxas.this, Perfil.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);

                /*
                Fragment fragment = new Fragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.image_dados_perfil, fragment);
                transaction.commit();

                finish();*/

            }
        });

        mChart = (LineChart) findViewById(R.id.linechart_taxas);
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // add data
        setData();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        mChart.setDescription("");
        mChart.setNoDataTextDescription("Você precisa inserir dados para gerar o gráfico");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);

        LimitLine upper_limit = new LimitLine(199f, "Glicose Ideal");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        /*LimitLine lower_limit = new LimitLine(144f, "Glicose Ideal");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);*/

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);
        //leftAxis.addLimitLine(lower_limit);
        leftAxis.setAxisMaxValue(220f);
        leftAxis.setAxisMinValue(-50f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChart.invalidate();
    }

    private ArrayList<String> setXAxisValues(int tam){
        ArrayList<String> xVals = new ArrayList<String>();
        int i = 0;
        for (i = 0; i < tam; i++){
            xVals.add("");
        }
        /*
        xVals.add("x");
        xVals.add("x");
        xVals.add("x");
        xVals.add("x");
        xVals.add("x");
        xVals.add("x");
*/
        return xVals;
    }

    private ArrayList<Entry> setYAxisValues(){

        //paciente = (Paciente) getIntent().getExtras().get("Paciente");

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        //ControllerPeso pesoController = new ControllerPeso(getApplicationContext());
        //ArrayList<Float> pesos = pesoController.getAllPesos(paciente);
        /*int i;
        for(i = 0; i < pesos.size(); i++){
            float valor = pesos.get(i);
            yVals.add(new Entry(valor, i));
        }*/

        yVals.add(new Entry(145f, 0));
        yVals.add(new Entry(149f, 2));
        yVals.add(new Entry(156f, 3));
        yVals.add(new Entry(148f, 4));
        yVals.add(new Entry(145f, 5));

        return yVals;
    }

    private void setData() {

        ArrayList<Entry> yVals = setYAxisValues();

        ArrayList<String> xVals = setXAxisValues(yVals.size());

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Glicose 75g");

        set1.setFillAlpha(90);
        //set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        //   set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}