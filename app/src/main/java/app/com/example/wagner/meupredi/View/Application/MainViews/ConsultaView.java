package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.firestore.ListenerRegistration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ConsultaController;
import app.com.example.wagner.meupredi.Model.Consulta;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 18/04/2017.
 */

public class ConsultaView extends Activity implements LiveUpdateHelper<Consulta>{

    private Calendar dataTime = Calendar.getInstance();
    private TextView btnMarcarData, btnMarcarHorario, contadorConsultas;
    private Button agendarNovaConsulta;
    private EditText nomeNovaConsulta, tipoNovaConsulta;
    private AlertDialog.Builder alertaNovaConsulta;
    private String date = "-", time = "-", local = "-", shortDate = "-", shortTime = "-",  diaEscolhido = "", mesEscolhido = "", anoEscolhido = "", tipoExame = "";
    private Date newDate;
    private Paciente paciente;

    private ListView listaDeConsultas;
    private ListenerRegistration listListener;

    private List<Consulta> consultas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        // Função abaixo impede que o teclado seja chamado para o edit text quando a tela abrir
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        nomeNovaConsulta = (EditText) findViewById(R.id.editText_nome_nova_consulta);
        tipoNovaConsulta = (EditText) findViewById(R.id.text_consultas_tipo_exame);
        btnMarcarData = (TextView) findViewById(R.id.btn_data_consulta_marcada);
        btnMarcarHorario = (TextView) findViewById(R.id.btn_horario_consulta_marcada);
        contadorConsultas = (TextView) findViewById(R.id.text_contador_consultas);
        agendarNovaConsulta = findViewById(R.id.btn_agendar_nova_consulta);
        listaDeConsultas = (ListView) findViewById(R.id.lista_consultas);

        contadorConsultas.setText("Consultas Anteriores");
        listaDeConsultas.setAdapter(new ArrayAdapter<String>(this, R.layout.lista_consultas_item,
                                    R.id.text_consulta_item, adapterList(new ArrayList<Consulta>())));

        listListener = ConsultaController.getLivePastConsultas(this, paciente);

        listaDeConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Consulta consulta = consultas.get(position);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                dataTime.setTime(consulta.getDate().toDate());
                date = dateFormat.format(consulta.getDate().toDate());
                shortTime = timeFormat.format(consulta.getDate().toDate());
                time = shortTime + ":00";

                nomeNovaConsulta.setText(consulta.getLocal());
                btnMarcarData.setText(date);
                btnMarcarHorario.setText(shortTime);
                tipoNovaConsulta.setText(consulta.getTitulo());
            }
        });

        agendarNovaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                local = nomeNovaConsulta.getText().toString();
                tipoExame = tipoNovaConsulta.getText().toString();
                nomeNovaConsulta.setHint("Hospital/Clínica");
                tipoNovaConsulta.setHint("Exame de Glicemia, TTG, ...");

                if (tipoExame.length() == 0
                || local.length() == 0
                || date.length() == 0
                || time.length() == 0) {
                    Toast.makeText(ConsultaView.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                } else if(!date.matches("\\d\\d/\\d\\d/\\d\\d\\d\\d")) {
                    Toast.makeText(ConsultaView.this, "Por favor, digite uma data válida!", Toast.LENGTH_LONG).show();
                } else if(!time.matches("\\d\\d:\\d\\d:\\d\\d")){
                    Toast.makeText(ConsultaView.this, "Por favor, digite um horário válido!", Toast.LENGTH_LONG).show();
                } else {
                    alertaNovaConsulta = new AlertDialog.Builder(ConsultaView.this);

                    alertaNovaConsulta.setTitle("Atenção!");

                    alertaNovaConsulta.setMessage("Verifique se as informações da sua nova consulta estão corretas e confirme." +
                            "\n" + "Nova Consulta em " + local + ", " + date + " às " + shortTime + ".");

                    // Caso Não
                    alertaNovaConsulta.setNegativeButton("CANCELAR",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ConsultaView.this, "Nova consulta cancelada", Toast.LENGTH_SHORT).show();
                                }
                            });

                    // Caso Sim
                    alertaNovaConsulta.setPositiveButton("CONFIRMAR",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(ConsultaView.this, "Nova consulta agendada!", Toast.LENGTH_SHORT).show();

                                    newDate = convertDate();
                                    Consulta consulta = new Consulta(paciente.getEmail(), tipoExame, local, newDate);

                                    ConsultaController.addEvento(paciente, consulta);
                                    finish();
                                }
                            });

                    alertaNovaConsulta.create().show();
                }
            }
        });

        btnMarcarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        btnMarcarHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });

    }

    private ArrayList<String> adapterList(List<Consulta> consultas){
        ArrayList<String> auxConsultas = new ArrayList<>();
        for(Consulta consulta : consultas){
            auxConsultas.add(consulta.toString());
        }
        return auxConsultas;
    }

    private void updateData(){
        new DatePickerDialog(ConsultaView.this, d, dataTime.get(Calendar.YEAR), dataTime.get(Calendar.MONTH), dataTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(ConsultaView.this, t, dataTime.get(Calendar.HOUR_OF_DAY), dataTime.get(Calendar.MINUTE), true ).show();

    }

    private Date convertDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        Log.d("DATE INICIO : ", date);
        Log.d("TIME INICIO: ", time);
        String ans = date + " " + time;
        try{
            return dateFormat.parse(ans);
        } catch (ParseException e){
            Log.d("Parse Exception", " ");
        }

        return null;
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            diaEscolhido = String.format("%02d", dayOfMonth);
            mesEscolhido = String.format("%02d", month+1);
            anoEscolhido = Integer.toString(year);
            date = diaEscolhido + "/" + mesEscolhido + "/" + anoEscolhido;
            shortDate = diaEscolhido + "/" + mesEscolhido;
            btnMarcarData.setText(diaEscolhido + "/" + mesEscolhido + "/" + anoEscolhido);
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time = "";
            shortTime = "";
            String horaEscolhida = "";
            String minutosEscolhidos = "";
            if(hourOfDay < 10 && hourOfDay >= 0) horaEscolhida = "0";
            if(minute < 10 && minute >= 0) minutosEscolhidos = "0";
            horaEscolhida = horaEscolhida + Integer.toString(hourOfDay);
            minutosEscolhidos = minutosEscolhidos + Integer.toString(minute);
            time = horaEscolhida + ":" + minutosEscolhidos + ":00";
            shortTime = horaEscolhida + ":" + minutosEscolhidos;
            btnMarcarHorario.setText(horaEscolhida + ":" + minutosEscolhidos);
        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        if(isFinishing()){
            if(listListener != null) listListener.remove();
        }
        super.onPause();
    }

    @Override
    public void onReceiveData(List<Consulta> consultas) {
        this.consultas = consultas;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConsultaView.this, R.layout.lista_consultas_item,
                R.id.text_consulta_item, adapterList(consultas));

        listaDeConsultas.setAdapter(adapter);
        contadorConsultas.setText("Consultas Anteriores ("+adapterList(consultas).size()+")");
    }
}