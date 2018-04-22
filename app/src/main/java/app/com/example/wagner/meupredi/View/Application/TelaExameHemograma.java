package app.com.example.wagner.meupredi.View.Application;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerExames;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.HemogramaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;

/**
 * Created by LeandroDias1 on 28/09/2017.
 */

public class TelaExameHemograma  extends AppCompatActivity {

    private EditText hemoglobina_exame_hemograma;
    private EditText hematocrito_exame_hemograma;
    private EditText vgm_exame_hemograma;
    private EditText chcm_exame_hemograma;
    private EditText rwd_exame_hemograma;
    private EditText local_exame_hemograma;
    private EditText data_exame_hemograma;
    private Button atualizar;
    private Paciente paciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exame_hemograma);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        hemoglobina_exame_hemograma = (EditText) findViewById(R.id.edit_hemoglobina_exame_hemograma);
        hemoglobina_exame_hemograma.setRawInputType(Configuration.KEYBOARD_QWERTY);

        hematocrito_exame_hemograma = (EditText) findViewById(R.id.edit_hematocrito_exame_hemograma);
        hematocrito_exame_hemograma.setRawInputType(Configuration.KEYBOARD_QWERTY);

        vgm_exame_hemograma = (EditText) findViewById(R.id.edit_vgm_exame_hemograma);
        vgm_exame_hemograma.setRawInputType(Configuration.KEYBOARD_QWERTY);

        chcm_exame_hemograma = (EditText) findViewById(R.id.edit_chcm_exame_hemograma);
        chcm_exame_hemograma.setRawInputType(Configuration.KEYBOARD_QWERTY);

        rwd_exame_hemograma = (EditText) findViewById(R.id.edit_rwd_exame_hemograma);
        rwd_exame_hemograma.setRawInputType(Configuration.KEYBOARD_QWERTY);

        local_exame_hemograma = (EditText) findViewById(R.id.edit_local_exame_hemograma);

        data_exame_hemograma = (EditText) findViewById(R.id.edit_data_exame_hemograma);
        data_exame_hemograma.setRawInputType(Configuration.KEYBOARD_QWERTY);

        atualizar = (Button) findViewById(R.id.btn_atualizar_exame_hemograma);

        atualizar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String hemoglobina = hemoglobina_exame_hemograma.getText().toString();
                String hematocrito = hematocrito_exame_hemograma.getText().toString();
                String vgm = vgm_exame_hemograma.getText().toString();
                String chcm = chcm_exame_hemograma.getText().toString();
                String rwd = rwd_exame_hemograma.getText().toString();
                String dataExame = data_exame_hemograma.getText().toString();
                String local = local_exame_hemograma.getText().toString();

                if(dataExame.length() == 0){
                    Toast.makeText(getApplicationContext(), "Insira uma data!", Toast.LENGTH_SHORT).show();
                } else if(dataExame.length()!=8 || dataExame.contains("/")){
                    Toast.makeText(getApplicationContext(), "Data em formato incorreto! Digite como ddmmaaaa, sem barras.", Toast.LENGTH_SHORT).show();
                } else if(hemoglobina.length()==0 || hematocrito.length()==0 || vgm.length()==0 || chcm.length()==0 || rwd.length()==0 || local.length()==0){
                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                } else {
                    dataExame = dataExame.substring(0, 2) + "/" + dataExame.substring(2, 4) + "/" + dataExame.substring(4, dataExame.length());

                    hemoglobina = hemoglobina.replace(',','.');
                    hematocrito = hematocrito.replace(',','.');
                    vgm = vgm.replace(',','.');
                    chcm = chcm.replace(',','.');
                    rwd = rwd.replace(',','.');

                    Double hemoglobinaNova = Double.parseDouble(hemoglobina);
                    Double hematocritoNovo = Double.parseDouble(hematocrito);
                    Double vgmNovo = Double.parseDouble(vgm);
                    Double chcmNovo = Double.parseDouble(chcm);
                    Double rwdNovo = Double.parseDouble(rwd);

                    String hemoglobinaFormatada = String.format(Locale.ENGLISH, "%.2f", hemoglobinaNova);
                    String hematocritoFormatado = String.format(Locale.ENGLISH, "%.2f", hematocritoNovo);
                    String vgmFormatado = String.format(Locale.ENGLISH, "%.2f", vgmNovo);
                    String chcmFormatado = String.format(Locale.ENGLISH, "%.2f", chcmNovo);
                    String rwdFormatado = String.format(Locale.ENGLISH, "%.2f", rwdNovo);

                    Double hemoglobinaPaciente = Double.parseDouble(hemoglobinaFormatada);
                    Double hematocritoPaciente = Double.parseDouble(hematocritoFormatado);
                    Double vgmPaciente = Double.parseDouble(vgmFormatado);
                    Double chcmPaciente = Double.parseDouble(chcmFormatado);
                    Double rwdPaciente = Double.parseDouble(rwdFormatado);

                    HemogramaClass newHemograma = new HemogramaClass();

                    newHemograma.setDataHemograma(dataExame);
                    newHemograma.setHemoglobina(hemoglobinaPaciente);
                    newHemograma.setHematocrito(hematocritoPaciente);
                    newHemograma.setVgm(vgmPaciente);
                    newHemograma.setChcm(chcmPaciente);
                    newHemograma.setRwd(rwdPaciente);

                    paciente = (Paciente) getIntent().getExtras().get("Paciente");
                    newHemograma.setIdPacienteHemograma(paciente.get_id());

                    DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                    ControllerExames controllerExames = new ControllerExames(getApplicationContext());
                    String retorno = controllerExames.addHemograma(newHemograma);

                    Toast.makeText(getApplicationContext(), retorno,Toast.LENGTH_SHORT).show();

                    finish();

                }


            }
        });

    }

}