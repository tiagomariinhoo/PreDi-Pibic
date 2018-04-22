package app.com.example.wagner.meupredi.View.Application;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerExames;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.MenuPrincipal;

/**
 * Created by LeandroDias1 on 25/07/2017.
 */

public class Taxas  extends AppCompatActivity {

    Paciente paciente;
    TextView  glicoseJejum, glicose75, hemoglobinaGlicolisada;
    EditText novaGlicose75, novaGlicoseJejum, novoColesterol;
    Button atualizarTaxas;

    FragmentTransaction transaction;
    FragmentManager fragmentManager;

    Class fragmentClasse = null;
    private Fragment MyFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxas);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        Log.d("TELA TAXAS : " , "<<<<<");
        Log.d("GlicoseJejum : ", String.valueOf(paciente.get_glicosejejum()));
        Log.d("Glicose75g : ", String.valueOf(paciente.get_glicose75g()));
        Log.d("Colesterol : ", String.valueOf(paciente.get_colesterol()));
        Log.d("Lipidograma : ", String.valueOf(paciente.get_lipidograma()));
        Log.d("Hemograma : ", String.valueOf(paciente.get_hemograma()));
        Log.d("Tireoide : ", String.valueOf(paciente.get_tireoide()));

        glicoseJejum = (TextView) findViewById(R.id.text_glicoseJejumAtual_taxas);
        glicoseJejum.setText(String.valueOf(paciente.get_glicosejejum()) + " mg/dL");

        glicose75 = (TextView) findViewById(R.id.text_glicose75gAtual_taxas);
        glicose75.setText(String.valueOf(paciente.get_glicose75g()) + " mg/dL");

        hemoglobinaGlicolisada = (TextView) findViewById(R.id.text_hemoglobina_glicolisadaAtual_taxas);
        hemoglobinaGlicolisada.setText(String.valueOf(paciente.get_colesterol()) + " mg/dL");

        novaGlicoseJejum = (EditText) findViewById(R.id.edit_glicoseJejum_taxas);
        novaGlicoseJejum.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novaGlicose75 = (EditText) findViewById(R.id.edit_glicose75g_taxas);
        novaGlicose75.setRawInputType(Configuration.KEYBOARD_QWERTY);
        novoColesterol = (EditText) findViewById(R.id.edit_hemoglobina_glicolisada_taxas);
        novoColesterol.setRawInputType(Configuration.KEYBOARD_QWERTY);

        findViewById(R.id.tela_taxas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCurrentFocus()!=null && getCurrentFocus() instanceof EditText){
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(novaGlicose75.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(novaGlicoseJejum.getWindowToken(), 0);
                    imm.hideSoftInputFromWindow(novoColesterol.getWindowToken(), 0);
                }
            }
        });

        atualizarTaxas = (Button) findViewById(R.id.btn_atualizar_taxas);

        atualizarTaxas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(novaGlicoseJejum.getText().toString().length() != 0) {

                    String novaGJ = novaGlicoseJejum.getText().toString();

                    novaGJ = novaGJ.replace(',', '.');
                    Double gJAtualizada = Double.parseDouble(novaGJ);
                    String gJFormatada = String.format(Locale.ENGLISH, "%.2f", gJAtualizada);
                    Double gJDoPaciente = Double.parseDouble(gJFormatada);

                    glicoseJejum.setText(String.valueOf(gJDoPaciente) + " mg/dL");
                    Log.d("GJejum : ", glicoseJejum.getText().toString());

                    paciente.set_glicosejejum(gJDoPaciente);

                } else {
                    paciente.set_glicosejejum(0);
                }

                if(novaGlicose75.getText().toString().length() != 0) {

                    String novaG75 = novaGlicose75.getText().toString();

                    novaG75 = novaG75.replace(',' , '.');
                    Double g75Atualizada = Double.parseDouble(novaG75);
                    String g75Formatada = String.format(Locale.ENGLISH, "%.2f", g75Atualizada);
                    Double g75DoPaciente = Double.parseDouble(g75Formatada);

                    glicose75.setText(String.valueOf(g75DoPaciente) + " mg/dL");

                    Log.d("Gli75 : ", glicose75.getText().toString());

                    paciente.set_glicose75g(g75DoPaciente);

                } else {
                    paciente.set_glicose75g(0);
                }

                if(novoColesterol.getText().toString().length() != 0) {

                    String novoC = novoColesterol.getText().toString();

                    novoC = novoC.replace(',', '.');
                    Double colesterolAtualizado = Double.parseDouble(novoC);
                    String colesterolFormatado = String.format(Locale.ENGLISH, "%.2f", colesterolAtualizado);
                    Double colesterolDoPaciente = Double.parseDouble(colesterolFormatado);

                    hemoglobinaGlicolisada.setText(String.valueOf(colesterolDoPaciente) + " mg/dL");

                    Log.d("Col : ", hemoglobinaGlicolisada.getText().toString());

                    paciente.set_colesterol(colesterolDoPaciente);

                } else {
                    paciente.set_colesterol(0);
                }

                //atualiza dados no banco de taxas e nos dados do paciente
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                ControllerExames controllerExames = new ControllerExames(getApplicationContext());
                controllerExames.atualizarTaxas(paciente);

                Toast.makeText(getApplicationContext(),"Taxas atualizadas com sucesso!",Toast.LENGTH_SHORT).show();
                paciente.calculo_diabetes(getApplicationContext());
                novaGlicoseJejum.setText("");
                novaGlicose75.setText("");
                novoColesterol.setText("");

                Intent intent = new Intent(Taxas.this, Perfil.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);

                /*
                Fragment fragment = new Fragment();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.image_dados_perfil, fragment);
                transaction.commit();

                finish();*/

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

}