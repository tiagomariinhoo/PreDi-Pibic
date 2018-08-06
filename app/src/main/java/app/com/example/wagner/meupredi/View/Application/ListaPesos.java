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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.ControllerPaciente;
import app.com.example.wagner.meupredi.Controller.ControllerPeso;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.PesoClass;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.Perfil;
import app.com.example.wagner.meupredi.View.Application.MainViews.Peso;

public class ListaPesos extends Activity {

    private Paciente paciente;
    private android.widget.ListView listaDePesos;
    private ArrayAdapter<String> adapter;
    private Button botaoAlterarMedicao;
    private EditText editPeso, editCirc;
    private AlertDialog.Builder alertaPesoSelecionado, alertaAlterarPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pesos);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        listaDePesos = (android.widget.ListView) findViewById(R.id.lista_pesos);
        editPeso = (EditText) findViewById(R.id.edit_text_editar_peso_na_lista);
        editCirc = (EditText) findViewById(R.id.edit_text_editar_circunferencia_na_lista);
        botaoAlterarMedicao = (Button) findViewById(R.id.btn_editar_peso_lista_pesos);

        editPeso.setEnabled(false);
        editCirc.setEnabled(false);

        ControllerPeso pesoController = new ControllerPeso(ListaPesos.this);

        ArrayList<PesoClass> pesoList = pesoController.getAllInfos(paciente);

        for(int i=0;i<pesoList.size();i++) {
            Log.d("Peso value : ", String.valueOf(pesoList.get(i).getPeso()));
            Log.d("Data Peso : ", pesoList.get(i).getDatePeso());
            Log.d("Flag: ", String.valueOf(pesoList.get(i).getFlagPeso()));
        };

        //Collections.reverse(pesoList);

        adapter = new ArrayAdapter<String>(this, R.layout.lista_item_pesos, R.id.text_item_lista_peso, adapterList(pesoController));

        listaDePesos.setAdapter(adapter);

        listaDePesos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = (String) adapter.getItem(position);

                PesoClass peso = pesoList.get(position);

                alertaPesoSelecionado = new AlertDialog.Builder(ListaPesos.this);
                alertaPesoSelecionado.setTitle("Alerta!");
                alertaPesoSelecionado.setMessage("Você deseja remover ou editar essa essa medição?\n" + value + "\nFeita em " + peso.getDatePeso());
                // Caso Não
                alertaPesoSelecionado.setNegativeButton("EDITAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(ListaPesos.this, "Mude os valores da medição e clique em ALTERAR", Toast.LENGTH_LONG).show();

                                String[] separados = value.split(" ");
                                editPeso.setHint(separados[1]);
                                editPeso.setEnabled(true);

                                editCirc.setHint(separados[5]);
                                editCirc.setEnabled(true);
                            }
                        });
                // Caso Sim
                alertaPesoSelecionado.setPositiveButton("REMOVER",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d("VALUE: ", value);
                                String aux = Integer.toString(position);
                                Log.d("AUX: ", aux);
                                adapter.remove(value);
                                adapter.notifyDataSetChanged();

                                ControllerPaciente controllerPaciente = new ControllerPaciente(getApplicationContext());


                                //Log.d("AUX: ", aux);
                                boolean at = pesoController.eraseLastInfo(peso);
                                if(at) Log.d("Peso ", "Ultimo peso excluído!");

                                //Log.d("DATE PESO: ", peso.getDatePeso());

                                //pesoController.atualizarPeso(paciente);
                                controllerPaciente.atualizarPaciente(paciente);

                                Toast.makeText(ListaPesos.this, "Medição removida com sucesso!", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(ListaPesos.this, Peso.class);
                                intent.putExtra("Paciente", paciente);
                                //finish();
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);


                            }
                        });
                alertaPesoSelecionado.create().show();

                botaoAlterarMedicao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String valorDiferentePeso = editPeso.getText().toString();
                        String valorDiferenteCirc = editCirc.getText().toString();

                        /*valorDiferentePeso = valorDiferentePeso.replace(',', '.');

                        String pesoFormatado = String.format(Locale.ENGLISH, "%.2f",valorDiferentePeso);
                        Float pesoDoPaciente = Float.parseFloat(pesoFormatado);

                        String circuFormatado = String.format(Locale.ENGLISH, "%.2f", valorDiferenteCirc);
                        Float circuDoPaciente = Float.parseFloat(circuFormatado);
*/
                        editPeso.setEnabled(false);
                        editCirc.setEnabled(false);

                        alertaAlterarPeso = new AlertDialog.Builder(ListaPesos.this);
                        alertaAlterarPeso.setTitle("Alerta!");
                        alertaAlterarPeso.setMessage("Você deseja alterar essa medição para:?\n" + "Peso: " + valorDiferentePeso +
                                "\nCircunferência: " + valorDiferenteCirc);
                        // Caso Não
                        alertaAlterarPeso.setNegativeButton("CANCELAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(ListaPesos.this, "Operação cancelada", Toast.LENGTH_LONG).show();
                                    }
                                });
                        // Caso Sim
                        alertaAlterarPeso.setPositiveButton("ALTERAR",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // FAZER CODIGO DE EDITAR AQUI
                                        Intent intent = new Intent(ListaPesos.this, Peso.class);
                                        intent.putExtra("Paciente", paciente);
                                        startActivity(intent);
                                        Toast.makeText(ListaPesos.this, "Valores alterados com sucesso", Toast.LENGTH_SHORT).show(); }
                                });
                        alertaAlterarPeso.create().show();
                    }
                });

            }
        });


    }

    private ArrayList<String> adapterList(ControllerPeso pesoController){

        ArrayList<PesoClass> pesoList = pesoController.getAllInfos(paciente);

        ArrayList<String> pesoListAux = new ArrayList<>();

        for(PesoClass peso : pesoList){
            pesoListAux.add(peso.toString());
        }

        //Collections.reverse(pesoListAux);

        return pesoListAux;
    }

}
