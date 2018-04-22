package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.text.ParseException;
import java.util.ArrayList;

import app.com.example.wagner.meupredi.Controller.ControllerExercicios;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.ExercicioClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by wagne on 07/07/2017.
 */

public class Ginasio extends Activity {

    private ImageView chamadaNovoExercicio;
    private ListView listView;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> listaExercicios;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        ControllerExercicios controllerExercicios = new ControllerExercicios(getApplicationContext());

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ginasio);

        ArrayList<ExercicioClass> ExClass = new ArrayList<ExercicioClass>();
        listaExercicios = new ArrayList<>();

        try {
            ExClass = controllerExercicios.getAllExercicios(paciente);

        } catch(ParseException e) {
            e.printStackTrace();
        }

        if(ExClass!=null){
            for(int i=0;i<ExClass.size();i++){
                Log.d("Exercicio : " , ExClass.get(i).getNome());
            }
        }
        chamadaNovoExercicio = (ImageView) findViewById(R.id.image_nova_atividade);
        listView = (ListView) findViewById(R.id.lista_exercicios);
        //String[] items = {"Caminhada", "Tenis", "BIRLLLLL"};

        //listaExercicios = getIntent().getStringArrayListExtra("listaExercicios");
                for(int i=0;i<ExClass.size();i++){
                    listaExercicios.add(ExClass.get(i).getNome());
                }

        if(listaExercicios!=null){
            Log.d("ENTROU NOS TESTES", "TESTES");
            for(int i=0;i<listaExercicios.size();i++){
                Log.d("Names : ", listaExercicios.get(i));
            }
        }

        //listaExercicios = new ArrayList<>(Arrays.asList(items));
        if(listaExercicios!=null){
            adaptador = new ArrayAdapter<String>(this, R.layout.lista_item_exercicios, R.id.text_item_lista_exe, listaExercicios);
            listView.setAdapter(adaptador);
        }

        chamadaNovoExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ginasio.this, NovoExercicio.class);
                intent.putStringArrayListExtra("listaExercicios2", listaExercicios);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
                finish();
            }
        });
    }
}
