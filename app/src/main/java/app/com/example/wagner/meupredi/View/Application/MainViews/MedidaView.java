package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
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
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Model.Medida;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.ListaMedidas;
import app.com.example.wagner.meupredi.View.Application.MedidaListener;
import app.com.example.wagner.meupredi.View.Application.Popups.PopConquista;
import app.com.example.wagner.meupredi.View.Application.Popups.PopPesoIdeal;

/**
 * Created by Tiago on 27/06/2017.
 */

public class MedidaView extends AppCompatActivity implements OnChartGestureListener,
        OnChartValueSelectedListener, MedidaListener, LiveUpdateHelper<Medida> {

    private LineChart mChart;
    private TextView dataUltimaMedicao, TextListaPesosTela, txtPesoIdeal;
    private EditText novoCirc, novoPeso;
    private ImageView chamadaListaPesos;
    private Button atualizarPeso;
    private CheckBox checkPeso, checkCircunferecia;
    private Paciente paciente;
    private List<Medida> medidas = new ArrayList<>();
    private ListenerRegistration graphListener;
    private AlertDialog.Builder alertaNovaMedicao;

    private void inverterCheckBox(String atual){
        if(atual.equals("Peso")) {
            checkPeso.setChecked(true);
            checkCircunferecia.setChecked(false);
            mudarGrafico(paciente);
        }
        else {
            checkPeso.setChecked(false);
            checkCircunferecia.setChecked(true);
            //mChart.setData(new LineData());
            mudarGrafico(paciente);
        }
      }

    private void mudarGrafico(Paciente paciente){

        mChart = (LineChart) findViewById(R.id.linechart_tela_peso);

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // add data pesos
        if(!medidas.isEmpty())
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

        double h = paciente.getAltura();
        double pesoAux = 24.9 * h * h;
        LimitLine upper_limit;
        if(checkPeso.isChecked()) {
            upper_limit = new LimitLine((float) pesoAux, "Peso Ideal");
        }
        else{
            if(paciente.getSexo().equals("M")){
                upper_limit = new LimitLine(90, "Circ. Ideal");
            }
            else {
                upper_limit = new LimitLine(80, "Circ. Ideal");
            }

        }

        upper_limit.setLineWidth(4f);
        upper_limit.enableDashedLine(10f, 10f, 0f);
        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upper_limit.setTextColor(R.color.colorAccent);
        upper_limit.setTextSize(10f);

        pesoAux = 18.6 * h * h;
        LimitLine lower_limit;
        if(checkPeso.isChecked()) {
            lower_limit = new LimitLine((float) pesoAux, "");
        }
        else{
            lower_limit = new LimitLine(-1, "");
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

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        txtPesoIdeal = findViewById(R.id.txt_medidas_peso_ideal);

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

        graphListener = MedidaController.getDadosGrafico(this, paciente);

        //carrega o gráfico vazio pra evitar delay para inicializar a tela
        mudarGrafico(paciente);

        PacienteUpdater.addListener(this);

        novoPeso.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novoCirc.setRawInputType(Configuration.KEYBOARD_QWERTY);

        txtPesoIdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedidaView.this, PopPesoIdeal.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.tela_peso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null) {
                        imm.hideSoftInputFromWindow(novoPeso.getWindowToken(), 0);
                    }
                }
            }
        });

        chamadaListaPesos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedidaView.this, ListaMedidas.class);
                startActivity(intent, ActivityOptions.makeScaleUpAnimation(v, 0, 0, v.getWidth(), v.getHeight()).toBundle());
            }
        });

        TextListaPesosTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedidaView.this, ListaMedidas.class);
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
                if (pesoAtual.length() == 0) {
                    pesoAtual = novoPeso.getHint().toString();
                }
                if (circAtual.length() == 0) {

                    circAtual = novoCirc.getHint().toString();
                }

                //pega string da data atual
                //Date dataRegistro = new Date.getInstance().getTime();
                GregorianCalendar dataRegistro = new GregorianCalendar();

                //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                pesoAtual = pesoAtual.replace(',', '.');
                circAtual = circAtual.replace(',', '.');

                Log.d("CircAtual : ", circAtual);
                Log.d("Peso Atual : ", pesoAtual);
                double pesoAtualizado = Double.NaN;
                double circAtualizado = Double.NaN;
                try{
                    pesoAtualizado = Double.parseDouble(pesoAtual);
                    circAtualizado = Double.parseDouble(circAtual);

                } catch(Exception e){
                    Toast.makeText(MedidaView.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                    finish();
                }

                Medida medida = new Medida();

                medida.initDate();

                if(!Double.isNaN(pesoAtualizado)) {
                    medida.setPeso(pesoAtualizado);
                } else{
                    medida.setPeso(paciente.getPeso());
                }

                if(!Double.isNaN(circAtualizado)) {
                    medida.setCircunferencia(circAtualizado);
                } else{
                    medida.setCircunferencia(paciente.getCircunferencia());
                }

                alertaNovaMedicao = new AlertDialog.Builder(MedidaView.this);
                alertaNovaMedicao.setTitle("Atenção!");
                alertaNovaMedicao.setMessage("Verifique se as informações de sua medição estão corretas e confirme.\n" +
                        "Peso: " + medida.printPeso() + "\n" +
                        "Circunferência: " + medida.printCircunferencia() + "\n" +
                        "Data: " + medida.printDate());

                // Caso Não
                alertaNovaMedicao.setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MedidaView.this, "Nova medição cancelada", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Caso Sim
                alertaNovaMedicao.setPositiveButton("CONFIRMAR",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if(!Double.isNaN(medida.getPeso()) || !Double.isNaN(medida.getCircunferencia())) {

                                paciente.setMedida(medida);

                                //atualiza o peso e a circ do paciente no banco
                                MedidaController.addMedida(paciente);

                            } else {
                                Toast.makeText(getApplicationContext(), "Medidas inválidas!", Toast.LENGTH_SHORT).show();
                            }

                            novoPeso.setText("");
                            novoCirc.setText("");

                            try {
                                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            } catch (NullPointerException e) {
                                //caso o teclado ja esteja escondido
                            }
                            Intent intent = new Intent(MedidaView.this, PopConquista.class);
                            startActivity(intent);
                        }
                    });
                alertaNovaMedicao.create().show();
            }
        });

        checkPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inverterCheckBox("Peso");
            }
        });

        checkCircunferecia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inverterCheckBox("Circunferencia");
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
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
    public void onReceiveData(List<Medida> data){
        this.medidas = data;
        Collections.reverse(medidas);
        mudarGrafico(paciente);
    }

    private void setData() {

        List<Entry> yVals = new ArrayList<>();
        List<String> xVals = new ArrayList<>();

        for (int i = 0, n = 0; i < medidas.size(); ++i) {
            double valor;
            if (checkPeso.isChecked()) {
                valor = medidas.get(i).getPeso();
            } else {
                valor = medidas.get(i).getCircunferencia();
            }
            Log.d("MEDIDAS: ", medidas.get(i).toString());
            if(!Double.isNaN(valor)) {
                yVals.add(new Entry((float) valor, n));
                xVals.add("");
                ++n;
            }
        }

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

    @Override
    public void onChangeMedida(Medida medida) {
        novoPeso.setHint(medida.stringPeso());
        novoCirc.setHint(medida.stringCircunferencia());
        String novaData = medida.printDate();
        String aux[] = novaData.split("/");
        //TODO: definir o fotmato correto dessa data
        novaData = aux[0]+", "+nomeDoMes(Integer.valueOf(aux[1])-1)+", "+aux[2];
        dataUltimaMedicao.setText(novaData);
    }
}