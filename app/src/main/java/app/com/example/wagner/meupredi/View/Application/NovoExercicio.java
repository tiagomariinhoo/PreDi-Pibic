package app.com.example.wagner.meupredi.View.Application;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import app.com.example.wagner.meupredi.Controller.ControllerExercicios;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by wagne on 09/07/2017.
 */

public class NovoExercicio extends AppCompatActivity {

    private Button adicionar;
    private ArrayList<String> listaExercicios;
    private RadioButton checkBoxAcademia, checkBoxCorrida, checkBoxCiclismo, checkBoxFutebol, checkBoxNatacao, checkBoxTenis, checkBoxOutra;
    private RadioGroup radioGroup;
    private EditText metaDiaria;
    private Paciente paciente;
    String metaDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_exercicio);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        checkBoxAcademia = (RadioButton) findViewById(R.id.checkBox_academia);
        checkBoxCiclismo = (RadioButton) findViewById(R.id.checkBox_ciclismo);
        checkBoxCorrida = (RadioButton) findViewById(R.id.checkBox_corrida);
        checkBoxFutebol = (RadioButton) findViewById(R.id.checkBox_futebol);
        checkBoxTenis = (RadioButton) findViewById(R.id.checkBox_tenis);
        checkBoxNatacao = (RadioButton) findViewById(R.id.checkBox_natacao);
        checkBoxOutra = (RadioButton) findViewById(R.id.checkBox_outra);
        metaDiaria = (EditText) findViewById(R.id.edit_novo_exe_meta_diaria);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group_exercicios);

        Log.d("TEMA 4", " TEMA 4");
        listaExercicios = getIntent().getStringArrayListExtra("listaExercicios2");
        if(listaExercicios == null){
            listaExercicios = new ArrayList<>();
        }

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        adicionar = (Button) findViewById(R.id.btn_salvar_novo_exe);



        /*if(checkBoxAcademia.isChecked()){
            checkBoxAcademia.toggle();
        } else if(checkBoxCiclismo.isChecked()){
            checkBoxCiclismo.toggle();
        } else if(checkBoxCorrida.isChecked()){
            checkBoxCorrida.toggle();
        }*/


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            }
        });

        /*checkBoxAcademia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //checkBoxAcademia.toggle();
                checkBoxCiclismo.setChecked(false);
                checkBoxCorrida.setChecked(false);
                checkBoxFutebol.setChecked(false);
                checkBoxTenis.setChecked(false);
                checkBoxNatacao.setChecked(false);
                checkBoxOutra.setChecked(false);

            }
        });

        checkBoxCiclismo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //checkBoxCiclismo.toggle();
               /* checkBoxAcademia.setChecked(false);
                checkBoxCorrida.setChecked(false);
                checkBoxFutebol.setChecked(false);
                checkBoxTenis.setChecked(false);
                checkBoxNatacao.setChecked(false);
                checkBoxOutra.setChecked(false);
            }
        });

        checkBoxCorrida.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxCiclismo.setChecked(false);
                checkBoxAcademia.setChecked(false);
                checkBoxFutebol.setChecked(false);
                checkBoxTenis.setChecked(false);
                checkBoxNatacao.setChecked(false);
                checkBoxOutra.setChecked(false);
            }
        });

        checkBoxFutebol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxCiclismo.setChecked(false);
                checkBoxCorrida.setChecked(false);
                checkBoxAcademia.setChecked(false);
                checkBoxTenis.setChecked(false);
                checkBoxNatacao.setChecked(false);
                checkBoxOutra.setChecked(false);
            }
        });

        checkBoxTenis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxCiclismo.setChecked(false);
                checkBoxCorrida.setChecked(false);
                checkBoxFutebol.setChecked(false);
                checkBoxAcademia.setChecked(false);
                checkBoxNatacao.setChecked(false);
                checkBoxOutra.setChecked(false);
            }
        });

        checkBoxNatacao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxCiclismo.setChecked(false);
                checkBoxCorrida.setChecked(false);
                checkBoxFutebol.setChecked(false);
                checkBoxTenis.setChecked(false);
                checkBoxAcademia.setChecked(false);
                checkBoxOutra.setChecked(false);
            }
        });

        checkBoxOutra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxCiclismo.setChecked(false);
                checkBoxCorrida.setChecked(false);
                checkBoxFutebol.setChecked(false);
                checkBoxTenis.setChecked(false);
                checkBoxNatacao.setChecked(false);
                checkBoxAcademia.setChecked(false);
            }
        });*/

        adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Adicionar", "entrei");
                metaDia = metaDiaria.getText().toString();

                if(metaDia.length()>0) {
                    if (checkBoxAcademia.isChecked()) {
                        adicionarExercicio("Academia");
                    } else if (checkBoxCiclismo.isChecked()) {
                        adicionarExercicio("Ciclismo");
                    } else if (checkBoxCorrida.isChecked()) {
                        adicionarExercicio("Corrida");
                    } else if (checkBoxFutebol.isChecked()) {
                        adicionarExercicio("Futebol");
                    } else if (checkBoxTenis.isChecked()) {
                        adicionarExercicio("Tenis");
                    } else if (checkBoxNatacao.isChecked()) {
                        adicionarExercicio("Natação");
                    } else if (checkBoxOutra.isChecked()) {
                        adicionarExercicio("Outra");
                    }

                    Intent intent = new Intent(NovoExercicio.this, Ginasio.class);
                    intent.putStringArrayListExtra("listaExercicios", listaExercicios);
                    intent.putExtra("Paciente", paciente);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Por favor, digite um valor válido!",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void adicionarExercicio(String novoExercicio){

        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        ControllerExercicios controllerExercicios = new ControllerExercicios(getApplicationContext());
        listaExercicios.add(novoExercicio);
        String msg = controllerExercicios.addExercicio(Integer.parseInt(metaDia), novoExercicio, paciente);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        /*setContentView(R.layout.activity_ginasio);
        listView = (ListView) findViewById(R.id.lista_exercicios);

        listaExercicios = new ArrayList<>();
        adaptador = new ArrayAdapter<String>(this, R.layout.lista_item_exercicios, R.id.text_item_lista_exe, listaExercicios);


        listView.setAdapter(adaptador);

        listaExercicios.add(novoExercicio);*/
    }

}
