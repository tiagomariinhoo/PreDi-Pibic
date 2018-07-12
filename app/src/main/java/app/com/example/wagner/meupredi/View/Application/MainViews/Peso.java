package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerPaciente;
import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by Tiago on 27/06/2017.
 */

public class Peso extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {

    private LineChart mChart;
    private TextView dataUltimaMedicao, pesoUltimaMedicao;
    private EditText novoCirc, novoPeso;
    private Button atualizarPeso;
    private Paciente paciente;
    private double imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        paciente.getInfo();

        imc = (paciente.get_peso()/(paciente.get_altura()*paciente.get_altura()));

        dataUltimaMedicao = (TextView) findViewById(R.id.text_ultima_medicao_tela_peso);
        pesoUltimaMedicao = (TextView) findViewById(R.id.text_hint_peso_ultima_medicao);

        //pega novo peso digitado pelo usuario
        novoPeso = (EditText) findViewById(R.id.text_registrar_valor_peso);
        novoCirc = (EditText) findViewById(R.id.text_registrar_valor_circunferencia);

        Double peso_atual = paciente.get_peso();
        Double circu_atual = paciente.get_circunferencia();
        String pesoAtual = novoPeso.getText().toString();
        if(pesoAtual.length() == 0){
            pesoUltimaMedicao.setText(String.format("%.2f", peso_atual));
        }
        else{
            pesoUltimaMedicao.setText("");
        }
        novoCirc.setHint(String.format("%.2f", circu_atual));

        //TODO: criar calculo de meta
        //TODO: criar atributo de meta para guardar o peso que o paciente devera alcancar

        //meta = (TextView) findViewById(R.id.text_meta_valor_peso);

        novoPeso.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novoCirc.setRawInputType(Configuration.KEYBOARD_QWERTY);

        findViewById(R.id.tela_peso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novoPeso.getWindowToken(), 0);
                }
            }
        });

        novoPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pesoUltimaMedicao.setText("");
            }
        });

        pesoUltimaMedicao.setText(String.format("%.2f", peso_atual));

        atualizarPeso = (Button) findViewById(R.id.btn_atualizar_peso);

        atualizarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pega string do peso e verifica tamanho
                String pesoAtual = novoPeso.getText().toString();
                String circu_atual = novoCirc.getText().toString();

                //pega string da data atual
                Date dataRegistro = Calendar.getInstance().getTime();


                if(pesoAtual.length() == 0) {
                    Toast.makeText(getApplicationContext(),"Preencha o campo correspondente!",Toast.LENGTH_SHORT).show();

                } else {

                    //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                    pesoAtual = pesoAtual.replace(',', '.');
                    circu_atual = pesoAtual.replace(',', '.');

                    Float pesoAtualizado = Float.parseFloat(pesoAtual);
                    Double circAtualizado = Double.parseDouble(circu_atual);

                    String pesoFormatado = String.format(Locale.ENGLISH, "%.2f", pesoAtualizado);
                    Float pesoDoPaciente = Float.parseFloat(pesoFormatado);

                    String circuFormatado = String.format(Locale.ENGLISH, "%.2f", circAtualizado);
                    Double circuDoPaciente =  Double.parseDouble(circuFormatado);

                    if(pesoDoPaciente > 0) {
                        //atualiza valor na tela
                        if(pesoDoPaciente == null){
                            novoPeso.setText(String.valueOf(0));
                        } else {
                           //  peso.setText(String.valueOf(pesoDoPaciente) + " kg");
                        }

                        //atualiza peso no objeto
                        paciente.set_peso(pesoDoPaciente);
                        paciente.set_pesos(pesoDoPaciente);

                        if (circuDoPaciente > 0){
                            paciente.set_circunferencia(circuDoPaciente);
                        }

                        //recalcula imc
                        if(paciente.get_peso() > 0 && paciente.get_altura() > 0) {

                            String imcFormatado = String.format(Locale.ENGLISH, "%.2f", imc);
                            imc = Double.parseDouble(imcFormatado);
                            paciente.set_imc(imc);
                        } else {
                            paciente.set_imc(0);
                        }

                        //atualiza o peso e o imc do paciente no banco
                        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        ControllerPeso controllerPeso = new ControllerPeso(getApplicationContext());
                        ControllerPaciente controllerPaciente = new ControllerPaciente(getApplicationContext());

                        controllerPeso.atualizarPeso(paciente);
                        controllerPaciente.atualizarPaciente(paciente);

                        Toast.makeText(getApplicationContext(),"Peso atualizado com sucesso!",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Peso.this, Perfil.class);
                        intent.putExtra("Paciente", paciente);
                        //finish();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(),"Peso inválido!",Toast.LENGTH_SHORT).show();
                    }

                    novoPeso.setText("");

                    try {
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch(NullPointerException e) {
                        //caso o teclado ja esteja escondido
                    }
                }
            }
        });

        mChart = (LineChart) findViewById(R.id.linechart_peso);
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

        double h = paciente.get_altura();
        double pesoAux = 24.9*h*h;

        LimitLine upper_limit = new LimitLine((float) pesoAux, "Peso Ideal");
        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit = new LimitLine(50f, "Peso Ideal");
        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextSize(10f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(upper_limit);
        leftAxis.addLimitLine(lower_limit);
        leftAxis.setAxisMaxValue(180f);
        leftAxis.setAxisMinValue(0f);
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

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        ControllerPeso pesoController = new ControllerPeso(getApplicationContext());
        ArrayList<Float> pesos = pesoController.getAllPesos(paciente);

        int i;
        for(i = 0; i < pesos.size(); i++){
            float valor = pesos.get(i);
            yVals.add(new Entry(valor, i));
        }

        return yVals;
    }

    private void setData() {

        ArrayList<Entry> yVals = setYAxisValues();

        ArrayList<String> xVals = setXAxisValues(yVals.size());

        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Pesos");

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
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture
                                            lastPerformedGesture) {

        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {

        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex()
                + ", high: " + mChart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin()
                + ", xmax: " + mChart.getXChartMax()
                + ", ymin: " + mChart.getYChartMin()
                + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }
}