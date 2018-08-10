package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import app.com.example.wagner.meupredi.Controller.ControllerPaciente;
import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.PesoClass;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.ListaPesos;

/**
 * Created by Tiago on 27/06/2017.
 */

public class Peso extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener {

    private LineChart mChart;
    private TextView dataUltimaMedicao, TextListaPesosTela;
    private EditText novoCirc, novoPeso;
    private ImageView chamadaListaPesos;
    private Button atualizarPeso;
    private CheckBox checkPeso, checkCircunferecia;
    private Paciente paciente;
    private double imc;
    private AlertDialog.Builder alertaNovaMedicao;

    private void inverterCheckBox(String atual, List<PesoClass> pesos){
        if(atual == "Peso") {
            checkCircunferecia.setChecked(!checkCircunferecia.isChecked());
            mudarGrafico(pesos);
        }
        else {
            checkPeso.setChecked(!checkPeso.isChecked());
            mudarGrafico(pesos);
        }
      }

    private void mudarGrafico(List<PesoClass> pesos){

        mChart = (LineChart) findViewById(R.id.linechart_tela_peso);

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // add data pesos
        setData(pesos);

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

        double h = paciente.getAltura();
        double pesoAux = 24.9 * h * h;
        LimitLine upper_limit;
        if(checkPeso.isChecked()) {
            upper_limit = new LimitLine((float) pesoAux, "Peso Ideal");
        }
        else{
            upper_limit = new LimitLine((float) pesoAux, "Circ. Ideal");
        }

        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextColor(R.color.colorAccent);
        upper_limit.setTextSize(10f);

        LimitLine lower_limit;
        if(checkPeso.isChecked()) {
            lower_limit = new LimitLine(50f, "Peso Ideal");
        }
        else{
            lower_limit = new LimitLine(50f, "Circ. Ideal");
        }

        lower_limit.setLineWidth(4f);
        lower_limit.enableDashedLine(10f, 10f, 0f);
        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lower_limit.setTextColor(R.color.colorAccent);
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

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        paciente.getInfo();
        ControllerPeso controllerPeso = new ControllerPeso(getApplicationContext());

        imc = (/*controllerPeso.getPeso(paciente)*/paciente.getPeso() / (paciente.getAltura() * paciente.getAltura()));

        TextListaPesosTela = (TextView) findViewById(R.id.text_chamada_lista_pesos_tela);
        chamadaListaPesos = (ImageView) findViewById(R.id.image_chamar_pesos_tela_peso);

        TextListaPesosTela = (TextView) findViewById(R.id.text_chamada_lista_pesos_tela);
        chamadaListaPesos = (ImageView) findViewById(R.id.image_chamar_pesos_tela_peso);

        dataUltimaMedicao = (TextView) findViewById(R.id.text_data_ultima_medicao_tela_peso);
        //pesoUltimaMedicao = (TextView) findViewById(R.id.text_hint_peso_ultima_medicao);

        //pega novo peso digitado pelo usuario
        novoPeso = (EditText) findViewById(R.id.text_registrar_valor_peso);
        novoCirc = (EditText) findViewById(R.id.text_registrar_valor_circunferencia);

        checkPeso = (CheckBox) findViewById(R.id.checkBox_graf_peso);
        checkCircunferecia = (CheckBox) findViewById(R.id.checkBox_circunferencia_graf);

        controllerPeso.getAllInfos(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                mudarGrafico(queryDocumentSnapshots.toObjects(PesoClass.class));
            }
        });

        Double peso_atual = paciente.getPeso();
        Double circ_atual = paciente.getCircunferencia();

        String pesoAtual = novoPeso.getText().toString();
        String circAtual = novoCirc.getText().toString();

        novoPeso.setHint(String.format("%.2f", peso_atual));
        novoCirc.setHint(String.format("%.2f", circ_atual));

        //TODO: criar calculo de meta
        //TODO: criar atributo de meta para guardar o peso que o paciente devera alcancar

        //meta = (TextView) findViewById(R.id.text_meta_valor_peso);

        novoPeso.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novoCirc.setRawInputType(Configuration.KEYBOARD_QWERTY);


        findViewById(R.id.tela_peso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novoPeso.getWindowToken(), 0);
                }
            }
        });

        chamadaListaPesos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Peso.this, ListaPesos.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });

        TextListaPesosTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Peso.this, ListaPesos.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });

        atualizarPeso = (Button) findViewById(R.id.btn_atualizar_peso);

        atualizarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pega string do peso e verifica tamanho
                String pesoAtual = novoPeso.getText().toString();
                String circAtual = novoCirc.getText().toString();

                // Verificar se algum dos campos não foi preenchido
                if (circAtual.length() == 0) {
                    circAtual = circ_atual.toString();
                }
                if (pesoAtual.length() == 0) {
                    pesoAtual = peso_atual.toString();
                }

                //pega string da data atual
                //Date dataRegistro = new Date.getInstance().getTime();
                GregorianCalendar dataRegistro = new GregorianCalendar();

                //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                pesoAtual = pesoAtual.replace(',', '.');
                circAtual = circAtual.replace(',', '.');
/*
                if(!pesoAtual.equals(".") && !circAtual.equals(".")){

                    Toast.makeText(Peso.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Peso.this, Perfil.class);
                    intent.putExtra("Paciente", paciente);
                    //finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
*/
                Log.d("CircAtual : ", circAtual);
                Log.d("Peso Atual : ", pesoAtual);
                Float pesoAtualizado = 0f;
                Float circAtualizado = 0f;
                try{
                    pesoAtualizado = Float.parseFloat(pesoAtual);
                    circAtualizado = Float.parseFloat(circAtual);

                } catch(Exception e){
                    Toast.makeText(Peso.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Peso.this, Perfil.class);
                    intent.putExtra("Paciente", paciente);
                    //finish();
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                String pesoFormatado = String.format(Locale.ENGLISH, "%.2f", pesoAtualizado);
                Float pesoDoPaciente = Float.parseFloat(pesoFormatado);

                String circuFormatado = String.format(Locale.ENGLISH, "%.2f", circAtualizado);
                Float circuDoPaciente = Float.parseFloat(circuFormatado);

                int dia = dataRegistro.get(GregorianCalendar.DAY_OF_MONTH);
                String mes = nomeDoMes(dataRegistro.get(GregorianCalendar.MONTH));
                int ano = dataRegistro.get(GregorianCalendar.YEAR);

                dataUltimaMedicao.setText(dia + ", " + mes + ", " + ano);

                alertaNovaMedicao = new AlertDialog.Builder(Peso.this);
                alertaNovaMedicao.setTitle("Atenção!");
                alertaNovaMedicao.setMessage("Verifique se as informações de sua medição estão corretas e confirme." +
                        "\n" + "Peso: " + pesoAtual + "kg\nCircunferência: " + circAtual + "cm\nData: " + dia +
                        "/" + mes + "/" + ano);

                // Caso Não
                alertaNovaMedicao.setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Peso.this, "Nova medicao cancelada", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Caso Sim
                alertaNovaMedicao.setPositiveButton("CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (pesoDoPaciente > 0) {
                                    //atualiza valor na tela
                                    if (pesoDoPaciente == null) {
                                        novoPeso.setText(String.valueOf(0));
                                    }

                                    //atualiza peso no objeto
                                    paciente.setPeso(pesoDoPaciente);
                                    paciente.set_pesos(pesoDoPaciente);
                                    paciente.setCircunferencia(circuDoPaciente);

                                    if (circuDoPaciente > 0) {
                                        paciente.setCircunferencia(circuDoPaciente);
                                    }

                                    //recalcula imc

                                    if (paciente.getPeso() > 0 && paciente.getAltura() > 0) {
                                        imc = pesoDoPaciente/(paciente.getAltura()*paciente.getAltura());
                                        String imcFormatado = String.format(Locale.ENGLISH, "%.2f", imc);
                                        imc = Double.parseDouble(imcFormatado);
                                        paciente.setImc(imc);
                                    } else {
                                        paciente.setImc(0);
                                    }

                                    //atualiza o peso e o imc do paciente no banco
                                    //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                    ControllerPeso controllerPeso = new ControllerPeso(getApplicationContext());
                                    ControllerPaciente controllerPaciente = new ControllerPaciente(getApplicationContext());

                                    controllerPeso.atualizarPeso(paciente);
                                    controllerPaciente.atualizarPaciente(paciente);

                                    Toast.makeText(getApplicationContext(), "Peso atualizado com sucesso!", Toast.LENGTH_SHORT).show();

                                    Log.d("MEDIDAS: ", String.valueOf(paciente.getCircunferencia()));

                                    Intent intent = new Intent(Peso.this, Perfil.class);
                                    intent.putExtra("Paciente", paciente);
                                    //finish();
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(getApplicationContext(), "Peso inválido!", Toast.LENGTH_SHORT).show();
                                }

                                novoPeso.setText("");

                                try {
                                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                } catch (NullPointerException e) {
                                    //caso o teclado ja esteja escondido
                                }
                            }
                        });
                alertaNovaMedicao.create().show();
            }
        });
        controllerPeso.getAllInfos(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                queryDocumentSnapshots.toObjects(PesoClass.class);
                checkPeso.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inverterCheckBox("Peso", queryDocumentSnapshots.toObjects(PesoClass.class));
                    }
                });

                checkCircunferecia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inverterCheckBox("Circunferencia", queryDocumentSnapshots.toObjects(PesoClass.class));

                    }
                });
            }
        });
    }

    private ArrayList<String> setXAxisValues(int tam) {
        ArrayList<String> xVals = new ArrayList<String>();
        int i = 0;
        for (i = 0; i < tam; i++) {
            xVals.add("");
        }

        return xVals;
    }
    private float doubleToFloat(double number){
        return Float.parseFloat(Double.toString(number));
    }
    private ArrayList<Entry> setYAxisValues(List<PesoClass> medidas) {

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        ControllerPeso pesoController = new ControllerPeso(getApplicationContext());
        List<Float> medidasAux;

        if(checkPeso.isChecked()){
            medidasAux = medidas.stream().map(medida -> doubleToFloat(medida.getPeso())).collect(Collectors.toList());
        }
        else{
            Log.d("CIRC: ", "ENTROU");
            medidasAux = medidas.stream().map(medida -> doubleToFloat(medida.getCircunferencia())).collect(Collectors.toList());
        }

        for (int i = 0; i < medidas.size(); i++) {
            float valor = medidasAux.get(i);
            Log.d("MEDIDAS: ", medidasAux.get(i).toString());
            yVals.add(new Entry(valor, i));
        }

        return yVals;
    }

    private void setData(List<PesoClass> medidas) {

        ArrayList<Entry> yVals = setYAxisValues(medidas);

        ArrayList<String> xVals = setXAxisValues(yVals.size());

        LineDataSet set1;

        // create a dataset and give it a type
        if(checkPeso.isChecked()){
            set1 = new LineDataSet(yVals, "Pesos");
        }
        else {
            set1 = new LineDataSet(yVals, "Circunferências");
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
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
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

    public String nomeDoMes(int M) {
        switch (M) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7:
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
        }
        return "";
    }

}