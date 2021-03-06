package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.firestore.ListenerRegistration;

import java.util.List;

import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.Model.Medida;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.LiveUpdateHelper;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class ListaMedidas extends Activity implements LiveUpdateHelper<Medida>{

    private Paciente paciente;
    private ImageView informacao;
    private android.widget.ListView listaDePesos;
    private RadioGroup radioGroupMedidas;
    private ListaAdapter<Medida> adapter;
    private List<Medida> medidas;
    private AlertDialog.Builder alertaDuvidas, chamadaCartaoMedida;
    private ListenerRegistration listListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pesos);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        listaDePesos = findViewById(R.id.lista_pesos);
        informacao = findViewById(R.id.image_informacao_lista_medidas);
        radioGroupMedidas = findViewById(R.id.radioGroupMedidas);

        radioGroupMedidas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onChangedAdapter(medidas);
                listaDePesos.setAdapter(adapter);
            }
        });

        listListener = MedidaController.getDadosGrafico(this, paciente);

        informacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaDuvidas = new AlertDialog.Builder(ListaMedidas.this);
                alertaDuvidas.setTitle("Informativo");
                alertaDuvidas.setMessage("Clicando em alguma Medida na lista, você poderá altera-la ou exclui-la\n");
                // Caso EDITAR
                alertaDuvidas.setNegativeButton("VOLTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ListaMedidas.this, "Clique para editar ou remover uma taxa da lista!", Toast.LENGTH_LONG).show();
                            }
                        });
                /* Caso REMOVER
                alertaPesoSelecionado.setPositiveButton("REMOVER",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });*/
                alertaDuvidas.create().show();
            }
        });

    }

    private void addListeners(List<Medida> medidas){
        listaDePesos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Medida medida = medidas.get(position);

                chamadaCartaoMedida = new AlertDialog.Builder(ListaMedidas.this);
                chamadaCartaoMedida.setTitle("Atenção!");
                chamadaCartaoMedida.setMessage("Você gostaria de editar ou excluir essa medição?\n");

                // Caso EDITAR
                chamadaCartaoMedida.setNegativeButton("NÃO",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ListaMedidas.this, "Clique para editar ou remover uma medida da lista!", Toast.LENGTH_LONG).show();
                            }
                        });
                chamadaCartaoMedida.setNegativeButton("SIM",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ListaMedidas.this, CartaoMedida.class);
                                intent.putExtra("Id Medida", medida.getId());
                                startActivity(intent);
                            }
                        });
                chamadaCartaoMedida.create().show();
            }
        });
    }

    private void onChangedAdapter(List<Medida> medidas){

        adapter = new ListaAdapter(ListaMedidas.this, R.layout.lista_item, medidas, Medida.class);
        switch (radioGroupMedidas.getCheckedRadioButtonId()){
            case R.id.radioPeso:
                adapter.setType("Peso");
                break;
            case R.id.radioCircufenrencia:
                adapter.setType("Circunferencia");
                break;
        }
        listaDePesos.setAdapter(adapter);
    }

    @Override
    public void onReceiveData(List<Medida> data){
        this.medidas = data;
        //Collections.reverse(medidas);
        addListeners(medidas);
        onChangedAdapter(medidas);
    }

    @Override
    protected void onPause() {
        if(isFinishing()){
            Log.d("Listener ", "Removido");
            if(listListener != null) listListener.remove();
        }
        super.onPause();
    }
}
