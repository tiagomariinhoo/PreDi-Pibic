package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import app.com.example.wagner.meupredi.Controller.ControllerAgenda;
import app.com.example.wagner.meupredi.Model.ModelClass.AgendaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 18/04/2017.
 */

public class Consultas extends Activity {

    private DateFormat formatacaoData = DateFormat.getDateInstance();
    private Calendar dataTime = Calendar.getInstance();
    private TextView btnMarcarData, btnMarcarHorario, contadorConsultas;
    private ImageView agendarNovaConsulta;
    private EditText nomeNovaConsulta;
    private AlertDialog.Builder alertaNovaConsulta;
    private String date = "-", time = "-", nome = "-", shortDate = "-", shortTime = "-",  diaEscolhido = "", mesEscolhido = "", anoEscolhido = "";
    private Paciente paciente;
    ArrayList<AgendaClass> agendaList = new ArrayList<>();

    private ListView listaDeConsultas;

    private ArrayList<String> arraylist;
    private ArrayAdapter<String> adapter;
    private String[] items = {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas);
        // Função abaixo impede que o teclado seja chamado para o edit text quando a tela abrir
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        nomeNovaConsulta = (EditText) findViewById(R.id.editText_nome_nova_consulta);
        btnMarcarData = (TextView) findViewById(R.id.btn_data_consulta_marcada);
        btnMarcarHorario = (TextView) findViewById(R.id.btn_horario_consulta_marcada);
        contadorConsultas = (TextView) findViewById(R.id.text_contador_consultas);
        agendarNovaConsulta = (ImageView) findViewById(R.id.btn_agendar_nova_consulta);
        listaDeConsultas = (ListView) findViewById(R.id.lista_consultas);


        ControllerAgenda controllerAgenda = new ControllerAgenda(Consultas.this);

        //arraylist = new ArrayList<>(Arrays.asList(items));
        adapter = new ArrayAdapter<String>(this, R.layout.lista_consultas_item, R.id.text_consulta_item, adapterList(controllerAgenda));

        listaDeConsultas.setAdapter(adapter);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        nome = "";
        agendarNovaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = nomeNovaConsulta.getText().toString();
                nomeNovaConsulta.setHint("Hospital/Clínica");
                btnMarcarData.setHint("yyyy/MM/dd");
                btnMarcarHorario.setHint("00:00");

                alertaNovaConsulta = new AlertDialog.Builder( Consultas.this );

                alertaNovaConsulta.setTitle("Atenção!");

                alertaNovaConsulta.setMessage("Verifique se as informações da sua nova consulta está corretas e confirme."+
                        "\n"+"Nova Consulta em " + nome + ", " + date + ", as "+ shortTime + ".");

                // Caso Não
                alertaNovaConsulta.setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Consultas.this, "Nova consulta cancelada", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Caso Sim
                alertaNovaConsulta.setPositiveButton("CONFIRMAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Consultas.this, "Nova consulta agendada!", Toast.LENGTH_SHORT).show();
                                // FAZER FUNÇÃO DE ADICIONAR NOVA CONSULTA EM LISTA DE CONSULTAS MARCADAS

                                //arraylist.add(nome+" - "+shortDate+" - "+shortTime);
                                AgendaClass agendaClass = new AgendaClass(nome, nome, anoEscolhido + "/" + mesEscolhido + "/" + diaEscolhido, shortTime);
                                ControllerAgenda controllerAgenda = new ControllerAgenda(Consultas.this);
                                controllerAgenda.adicionarEvento(paciente, agendaClass);

                                adapter.notifyDataSetChanged();

                                Log.d("NOME DO PACIENTE : " , paciente.get_nome());
                                try {
                                    //AgendaClass agenda = new AgendaClass(nome, "3293", date, time);

                                    //ControllerAgenda controllerAgenda = new ControllerAgenda(getApplicationContext());
                                    //Log.d(controllerAgenda.adicionarEvento(paciente, agenda), "");
                                    //agendaList = controllerAgenda.getAllEventos(paciente);
                                    //controllerAgenda.getAllEventos(paciente);

                                    //Log.d("Adicionando : ", controllerAgenda.adicionarEvento(paciente, agenda));
                                } catch (Exception e) {
                                    Log.d("N n n n n", " N FOI POSSIVEL ADICIONAR EVENTO");
                                    e.printStackTrace();
                                }
                            }
                        });

                alertaNovaConsulta.create().show();
            }
        });

        contadorConsultas.setText("Consultas ("+adapterList(controllerAgenda).size()+")");

        btnMarcarData .setOnClickListener(new View.OnClickListener() {
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

    private ArrayList<String> adapterList(ControllerAgenda controllerAgenda){
        agendaList = controllerAgenda.getAllEventos(paciente);
        ArrayList<String> agendaList2 = new ArrayList<>(Arrays.asList(items));
        for(AgendaClass agenda : agendaList){
            agendaList2.add(agenda.getTitulo() + " - " + agenda.getDate() + " - " + agenda.getTime());
        }
        return agendaList2;
    }

    private void updateData(){
        new DatePickerDialog(Consultas.this, d, dataTime.get(Calendar.YEAR), dataTime.get(Calendar.MONTH), dataTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateTime(){
        new TimePickerDialog(Consultas.this, t, dataTime.get(Calendar.HOUR_OF_DAY), dataTime.get(Calendar.MINUTE), true ).show();

    }

   /* private Date convertDate() throws ParseException {
        SimpleDateFormat convertData = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss", Locale.US);
        Log.d("DATE INICIO : ", date);
        Log.d("TIME INICIO: ", time);
        String ans = date + " " + time;
        try{
            //Date testData = convertData.parse(ans);
            //Log.d("Testdata : ", testData.toString());
            //return testData;
        } catch (ParseException e){
            Log.d("Não foi possível transformar", " ");
        }

        String a = "dd/mm/yyyy hh:mm:ss";
        Date test = convertData.parse(a);

        return test;
    }*/

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            date = "";
            shortDate = "";
            diaEscolhido = "";
            mesEscolhido = "";
            anoEscolhido = "";
            if(dayOfMonth < 10) diaEscolhido = "0";
            if(month < 10) mesEscolhido = "0";
            diaEscolhido = diaEscolhido + Integer.toString(dayOfMonth);
            mesEscolhido = mesEscolhido + Integer.toString(month+1);
            anoEscolhido = anoEscolhido + Integer.toString(year);
            date = anoEscolhido + "/" + mesEscolhido + "/" + diaEscolhido;
            shortDate = mesEscolhido + "/" + diaEscolhido;
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

    public class AdapterCursosPersonalizado extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

}