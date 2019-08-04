package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.TaxasController;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.Model.Taxas;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.ListaTaxas;
import app.com.example.wagner.meupredi.View.Application.PopGlicoses;
import app.com.example.wagner.meupredi.View.Application.TaxasListener;

/**
 * Created by LeandroDias1 on 25/07/2017.
 */

public class TaxasView extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener, TaxasListener, LiveUpdateHelper<Taxas> {

    private Paciente paciente;
    private RadioGroup radioGroupGraficoTaxas;
    private TextView  glicoseJejum, glicose75, hemoglobinaGlicolisada;
    private TextView tituloJejum, titulo75g, tituloGlicada;
    private EditText novaGlicose75, novaGlicoseJejum, novaHemoglobinaGlicolisada;
    private ImageView chamadaInformativo, listarTaxas;
    private Button atualizarTaxas;
    private LineChart mChart;
    private ListenerRegistration graphListener;
    private List<Taxas> taxas = new ArrayList<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxas);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        Log.d("TELA TAXAS : " , "<<<<<");
        Log.d("GlicoseJejum : ", String.valueOf(paciente.getGlicoseJejum()));
        Log.d("Glicose75g : ", String.valueOf(paciente.getGlicose75g()));
        Log.d("Colesterol : ", String.valueOf(paciente.getColesterol()));

        listarTaxas = findViewById(R.id.btn_chamada_listar_taxas);

        glicoseJejum = findViewById(R.id.text_glicoseJejumAtual_taxas);

        if(!Double.isNaN(paciente.getGlicose75g())) {
            glicoseJejum.setText(String.format(Locale.getDefault(), "%.2f  mg/dL", paciente.getGlicoseJejum()));
        } else glicoseJejum.setText("-- mg/dL");

        glicose75 = findViewById(R.id.text_glicose75gAtual_taxas);

        if(!Double.isNaN(paciente.getGlicose75g())) {
            glicose75.setText(String.format(Locale.getDefault(), "%.2f  mg/dL", paciente.getGlicose75g()));
        } else glicose75.setText("-- mg/dL");

        hemoglobinaGlicolisada = findViewById(R.id.text_hemoglobina_glicolisadaAtual_taxas);

        if(!Double.isNaN(paciente.getHemoglobinaGlicolisada())) {
            hemoglobinaGlicolisada.setText(String.format(Locale.getDefault(), "%.2f  %%", paciente.getHemoglobinaGlicolisada()));
        } else hemoglobinaGlicolisada.setText("-- %");

        chamadaInformativo = findViewById(R.id.image_informativo_glicoses);

        novaGlicoseJejum = findViewById(R.id.edit_glicoseJejum_taxas);
        novaGlicoseJejum.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novaGlicose75 = findViewById(R.id.edit_glicose75g_taxas);
        novaGlicose75.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novaHemoglobinaGlicolisada = findViewById(R.id.edit_hemoglobina_glicolisada_taxas);
        novaHemoglobinaGlicolisada.setRawInputType(Configuration.KEYBOARD_QWERTY);

        tituloJejum = findViewById(R.id.text_titulo_jejum_tela_axas);
        titulo75g = findViewById(R.id.text_titulo_75g_tela_taxas);
        tituloGlicada = findViewById(R.id.text_glicada_titulo_tela_taxas);

        PacienteUpdater.addListener(this);

        radioGroupGraficoTaxas = findViewById(R.id.radioGroupGraficoTaxas);

        graphListener = TaxasController.getDadosGrafico(this, paciente);

        //carrega o gráfico vazio pra evitar delay para inicializar a tela
        mudarGrafico();

        findViewById(R.id.tela_taxas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(novaGlicose75.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(novaGlicoseJejum.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(novaHemoglobinaGlicolisada.getWindowToken(), 0);
                    }
                    tituloJejum.setTextColor(getResources().getColor(R.color.colorBranco));
                    titulo75g.setTextColor(getResources().getColor(R.color.colorBranco));
                    tituloGlicada.setTextColor(getResources().getColor(R.color.colorBranco));
                }
            }
        });

        novaGlicoseJejum.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()) {
                    tituloJejum.setTextColor(getResources().getColor(R.color.colorConfirm));
                    titulo75g.setTextColor(getResources().getColor(R.color.colorBranco));
                    tituloGlicada.setTextColor(getResources().getColor(R.color.colorBranco));
                }
                return false;
            }
        });

        novaGlicose75.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()) {
                    tituloJejum.setTextColor(getResources().getColor(R.color.colorBranco));
                    titulo75g.setTextColor(getResources().getColor(R.color.colorConfirm));
                    tituloGlicada.setTextColor(getResources().getColor(R.color.colorBranco));
                }
                return false;
            }
        });

        novaHemoglobinaGlicolisada.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(MotionEvent.ACTION_UP == motionEvent.getAction()) {
                    tituloJejum.setTextColor(getResources().getColor(R.color.colorBranco));
                    titulo75g.setTextColor(getResources().getColor(R.color.colorBranco));
                    tituloGlicada.setTextColor(getResources().getColor(R.color.colorConfirm));
                }
                return false;
            }
        });

        listarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaxasView.this, ListaTaxas.class);
                startActivity(intent, ActivityOptions.makeScaleUpAnimation(view, 0, 0, view.getWidth(), view.getHeight()).toBundle());
            }
        });

        atualizarTaxas = findViewById(R.id.btn_atualizar_taxas);

        atualizarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(novaGlicoseJejum.getText().toString().length() != 0) {

                    String novaGJ = novaGlicoseJejum.getText().toString();

                    novaGJ = novaGJ.replace(',', '.');
                    Double gJAtualizada = 0d;
                    try{
                        gJAtualizada = Double.parseDouble(novaGJ);
                    } catch(Exception e){
                        Toast.makeText(TaxasView.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    String gJFormatada = String.format(Locale.ENGLISH, "%.2f", gJAtualizada);
                    Double gJDoPaciente = Double.parseDouble(gJFormatada);

                    //glicoseJejum.setText(String.valueOf(gJDoPaciente) + " mg/dL");
                    Log.d("GJejum : ", glicoseJejum.getText().toString());

                    paciente.setGlicoseJejum(gJDoPaciente);

                }

                if(novaGlicose75.getText().toString().length() != 0) {

                    String novaG75 = novaGlicose75.getText().toString();

                    novaG75 = novaG75.replace(',' , '.');

                    Double g75Atualizada = 0d;
                    try{
                        g75Atualizada = Double.parseDouble(novaG75);
                    } catch(Exception e){
                        Toast.makeText(TaxasView.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    String g75Formatada = String.format(Locale.ENGLISH, "%.2f", g75Atualizada);
                    Double g75DoPaciente = Double.parseDouble(g75Formatada);

                    //glicose75.setText(String.valueOf(g75DoPaciente) + " mg/dL");

                    Log.d("Gli75 : ", glicose75.getText().toString());

                    paciente.setGlicose75g(g75DoPaciente);

                }

                if(novaHemoglobinaGlicolisada.getText().toString().length() != 0) {

                    String novaHG = novaHemoglobinaGlicolisada.getText().toString();

                    novaHG = novaHG.replace(',', '.');

                    Double hgAtualizada = 0.0;
                    try{
                        hgAtualizada = Double.parseDouble(novaHG);
                    } catch(Exception e){
                        Toast.makeText(TaxasView.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    String hgFormatada = String.format(Locale.ENGLISH, "%.2f", hgAtualizada);
                    Double hgDoPaciente = Double.parseDouble(hgFormatada);

                    //hemoglobinaGlicolisada.setText(String.valueOf(hgDoPaciente) + " %%");

                    Log.d("HG : ", hemoglobinaGlicolisada.getText().toString());

                    paciente.setHemoglobinaGlicolisada(hgDoPaciente);

                }

                //atualiza dados no banco de taxas e nos dados do paciente
                TaxasController.addTaxas(paciente);

                Toast.makeText(getApplicationContext(),"Taxas atualizadas com sucesso!",Toast.LENGTH_SHORT).show();
                novaGlicoseJejum.setText("");
                novaGlicose75.setText("");
                novaHemoglobinaGlicolisada.setText("");

                //finish();

                /*
                Fragment fragment = new Fragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.image_dados_perfil, fragment);
                transaction.commit();
                finish();*/
            }
        });

        chamadaInformativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaxasView.this, PopGlicoses.class);
                startActivity(intent);
            }
        });

        radioGroupGraficoTaxas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mudarGrafico();
            }
        });
    }

    private void mudarGrafico(){

        mChart = (LineChart) findViewById(R.id.linechart_taxas);

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // add data pesos
        if(!taxas.isEmpty())
            setData();

        // no description text
        mChart.setDescription("");
        mChart.setNoDataText("Você precisa inserir dados para gerar o gráfico");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);
        mChart.setPinchZoom(true);
        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);

        /*LimitLine upper_limit = new LimitLine(140f, "Glicose75g Ideal");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);*/

        /*LimitLine lower_limit = new LimitLine(144f, "Glicose Ideal");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);*/

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        //leftAxis.addLimitLine(upper_limit);
        //leftAxis.addLimitLine(lower_limit);
        //altera valores máximos e mínimos do gráfico de acordo com o tipo de dado
        switch (radioGroupGraficoTaxas.getCheckedRadioButtonId()) {
            case R.id.radioApos75g_grafico_taxas:
                changeGraphLimits(200f, 60f);
            break;

            case R.id.radioGlicada_grafico_taxas:
                changeGraphLimits(10f, 0f);
            break;

            default:
                changeGraphLimits(180f, 40f);
            break;
        }
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

    private void changeGraphLimits(float max, float min){
        mChart.getAxisLeft().mAxisRange = max-min;
        mChart.getAxisRight().mAxisRange = max-min;
        //mChart.setVisibleYRangeMaximum(180f, YAxis.AxisDependency.LEFT);
        //mChart.setVisibleYRangeMaximum(180f, YAxis.AxisDependency.RIGHT);
        mChart.getAxisLeft().setAxisMaxValue(max);
        mChart.getAxisLeft().setAxisMinValue(min);

        mChart.getAxisRight().setAxisMaxValue(max);
        mChart.getAxisRight().setAxisMinValue(min);
    }

    @Override
    public void onReceiveData(List<Taxas> data) {
        this.taxas = data;
        Collections.reverse(taxas);
        mudarGrafico();
    }

    private void setData() {

        List<Entry> yVals = new ArrayList<>();
        List<String> xVals = new ArrayList<>();

        for (int i = 0; i < taxas.size(); ++i) {
            float valor;
            switch (radioGroupGraficoTaxas.getCheckedRadioButtonId()){
                case R.id.radioApos75g_grafico_taxas:
                    valor = (float) taxas.get(i).getGlicose75g();
                break;

                case R.id.radioGlicada_grafico_taxas:
                    valor = (float) taxas.get(i).getHemoglobinaGlico();
                break;

                default:
                    valor = (float) taxas.get(i).getGlicoseJejum();
                break;
            }

            yVals.add(new Entry(valor, i));
            xVals.add("");
        }

        LineDataSet set1;

        // create a dataset and give it a type
        switch (radioGroupGraficoTaxas.getCheckedRadioButtonId()){
            case R.id.radioApos75g_grafico_taxas:
                set1 = new LineDataSet(yVals, "Glicose após 75g");
            break;

            case R.id.radioGlicada_grafico_taxas:
                set1 = new LineDataSet(yVals, "Hemoglobina Glicada");
            break;

            default:
                set1 = new LineDataSet(yVals, "Glicose em Jejum");
            break;
        }

        set1.setFillAlpha(80);
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

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);
    }

    @Override
    public void onChangeTaxas(Taxas taxas) {
        glicoseJejum.setText(taxas.printGlicoseJejum());
        glicose75.setText(taxas.printGlicose75g());
        hemoglobinaGlicolisada.setText(taxas.printHemoglobinaGlico());
    }

    @Override
    protected void onPause() {
        if(isFinishing()){
            Log.d("Listener ", "Removido");
            PacienteUpdater.removeListener(this);
            if(graphListener != null) graphListener.remove();
        }
        super.onPause();
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