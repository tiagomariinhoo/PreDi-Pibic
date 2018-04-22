package app.com.example.wagner.meupredi.View.Application;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerExames;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.LipidogramaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 28/09/2017.
 */

public class TelaExameLipidograma extends AppCompatActivity {

    private EditText dataLipidograma, localLipidograma;
    private EditText hdl, ldl, colesterolTotal, triglicerides;
    private Button atualizar;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame_lipidograma);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        dataLipidograma = (EditText) findViewById(R.id.edit_data_exame_lipidograma);
        dataLipidograma.setRawInputType(Configuration.KEYBOARD_QWERTY);
        localLipidograma = (EditText) findViewById(R.id.edit_local_exame_lipidograma);

        hdl = (EditText) findViewById(R.id.edit_taxa_hdl_exame_lipidograma);
        hdl.setRawInputType(Configuration.KEYBOARD_QWERTY);
        ldl = (EditText) findViewById(R.id.edit_taxa_ldl_exame_lipidograma);
        ldl.setRawInputType(Configuration.KEYBOARD_QWERTY);
        colesterolTotal = (EditText) findViewById(R.id.edit_colesterol_total_exame_lipidograma);
        colesterolTotal.setRawInputType(Configuration.KEYBOARD_QWERTY);
        triglicerides = (EditText) findViewById(R.id.edit_chcm_exame_hemograma);
//        triglicerides.setRawInputType(Configuration.KEYBOARD_QWERTY);

        atualizar = (Button) findViewById(R.id.btn_atualizar_exame_lipidograma);

        findViewById(R.id.tela_exame_lipidograma).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(dataLipidograma.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(localLipidograma.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(hdl.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(ldl.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(colesterolTotal.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(triglicerides.getWindowToken(), 0);
                }
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dataExame = dataLipidograma.getText().toString();
                String localExame = localLipidograma.getText().toString();
                String hdlExame = hdl.getText().toString();
                String ldlExame = ldl.getText().toString();
                String colesterolExame = colesterolTotal.getText().toString();
                String trigliceridesExame = triglicerides.getText().toString();

                if(dataExame.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Insira uma data!", Toast.LENGTH_SHORT).show();
                } else if(dataExame.length() != 8 || dataExame.contains("/")) {
                    Toast.makeText(getApplicationContext(), "Data em formato incorreto! Digite como ddmmaaaa, sem barras.", Toast.LENGTH_SHORT).show();
                } else if(hdlExame.length() == 0 || ldlExame.length() == 0 || colesterolExame.length() == 0  || trigliceridesExame.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {

                    dataExame = dataExame.substring(0, 2) + "/" + dataExame.substring(2, 4) + "/" + dataExame.substring(4, dataExame.length());

                    hdlExame = hdlExame.replace(',', '.');
                    ldlExame = ldlExame.replace(',', '.');
                    colesterolExame = colesterolExame.replace(',', '.');
                    trigliceridesExame = trigliceridesExame.replace(',', '.');

                    Double hdlNova = Double.parseDouble(hdlExame);
                    Double ldlNova = Double.parseDouble(ldlExame);
                    Double colesterolNovo = Double.parseDouble(colesterolExame);
                    Double trigliceridesNova = Double.parseDouble(trigliceridesExame);

                    String hdlFormatada = String.format(Locale.ENGLISH, "%.2f", hdlNova);
                    String ldlFormatada = String.format(Locale.ENGLISH, "%.2f", ldlNova);
                    String colesterolFormatado = String.format(Locale.ENGLISH, "%.2f", colesterolNovo);
                    String trigliceridesFormatada = String.format(Locale.ENGLISH, "%.2f", trigliceridesNova);

                    Double hdlDoPaciente = Double.parseDouble(hdlFormatada);
                    Double ldlDoPaciente = Double.parseDouble(ldlFormatada);
                    Double colesterolDoPaciente = Double.parseDouble(colesterolFormatado);
                    Double trigliceridesDoPaciente = Double.parseDouble(trigliceridesFormatada);

                    LipidogramaClass novoLipidograma = new LipidogramaClass();

                    novoLipidograma.setDataLipidograma(dataExame);
                    novoLipidograma.setHDL(hdlDoPaciente);
                    novoLipidograma.setLDL(ldlDoPaciente);
                    novoLipidograma.setColesterolTotal(colesterolDoPaciente);
                    novoLipidograma.setTriglicerides(trigliceridesDoPaciente);

                    if(localExame.length() != 0) {
                        novoLipidograma.setLocalLipidograma(localExame);
                    } else {
                        novoLipidograma.setLocalLipidograma("Local não informado.");
                    }

                    paciente = (Paciente) getIntent().getExtras().get("Paciente");

                    novoLipidograma.setIdPacienteLipidograma(paciente.get_id());

                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    ControllerExames controllerExames = new ControllerExames(getApplicationContext());
                    String retorno = controllerExames.addLipidograma(novoLipidograma);

                    Toast.makeText(getApplicationContext(), retorno,Toast.LENGTH_SHORT).show();

                    finish();
                }
            }

        });

        }

    }
