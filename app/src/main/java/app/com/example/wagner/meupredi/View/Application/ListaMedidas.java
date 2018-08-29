package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.R;

public class ListaMedidas extends Activity {

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

        editPeso.setRawInputType(Configuration.KEYBOARD_QWERTY);
        editCirc.setRawInputType(Configuration.KEYBOARD_QWERTY);

        MedidaController.getAllMedidas(paciente).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Medida> medidas = queryDocumentSnapshots.toObjects(Medida.class);
                adapter = new ArrayAdapter<String>(ListaMedidas.this, R.layout.lista_item_pesos,
                        R.id.text_item_lista_peso, adapterList(medidas));

                listaDePesos.setAdapter(adapter);

                addListeners(medidas);
            }
        });
    }

    private ArrayList<String> adapterList(List<Medida> medidas){

        ArrayList<String> medidasAux = new ArrayList<>();

        for(Medida medida : medidas){
            medidasAux.add(medida.toString());
        }

        return medidasAux;
    }

    private void addListeners(List<Medida> medidas){
        listaDePesos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = (String) adapter.getItem(position);

                Medida peso = medidas.get(position);

                alertaPesoSelecionado = new AlertDialog.Builder(ListaMedidas.this);
                alertaPesoSelecionado.setTitle("Alerta!");
                alertaPesoSelecionado.setMessage("Você deseja remover ou editar essa essa medição?\n" + value + "\n"
                                                + "Feita em " + peso.printingDate() + " às " + peso.printingTime());
                // Caso EDITAR
                alertaPesoSelecionado.setNegativeButton("EDITAR",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(ListaMedidas.this, "Mude os valores da medição e clique em ALTERAR", Toast.LENGTH_LONG).show();

                            String[] separados = value.split(" ");
                            editPeso.setHint(separados[1]);
                            editPeso.setEnabled(true);

                            editCirc.setHint(separados[5]);
                            editCirc.setEnabled(true);
                        }
                    });
                // Caso REMOVER
                alertaPesoSelecionado.setPositiveButton("REMOVER",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d("VALUE: ", value);
                            String aux = Integer.toString(position);
                            Log.d("AUX: ", aux);
                            adapter.remove(value);
                            adapter.notifyDataSetChanged();

                            //Log.d("AUX: ", aux);
                            MedidaController.eraseLastInfo(peso).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("Peso ", "Ultimo peso excluído!");
                                    Toast.makeText(ListaMedidas.this, "Medição removida com sucesso!", Toast.LENGTH_LONG).show();
                                }
                            });/*
                            Intent intent = new Intent(ListaMedidas.this, MedidaView.class);
                            intent.putExtra("Paciente", paciente);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*/
                            finish();
                        }
                    });

                alertaPesoSelecionado.create().show();

                botaoAlterarMedicao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String valorDiferentePeso = editPeso.getText().toString();
                        String valorDiferenteCirc = editCirc.getText().toString();

                        if(valorDiferentePeso.length() <= 0){
                            Log.d("HINTPESO: ", editPeso.getHint().toString());
                            valorDiferentePeso = editPeso.getHint().toString();
                        }

                        if(valorDiferenteCirc.length() <= 0){
                            Log.d("HINTCIRC: ", editCirc.getHint().toString());
                            valorDiferenteCirc = editCirc.getHint().toString();
                        }

                        //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                        valorDiferentePeso = valorDiferentePeso.replace(',', '.');
                        valorDiferenteCirc = valorDiferenteCirc.replace(',', '.');

                        editPeso.setEnabled(false);
                        editCirc.setEnabled(false);

                        // Essa variavel verifca se o usuario digitou dados inválidos(por exemplo: " ,", " . ", " .5", etc) ou não
                        boolean invalid_input = !valorDiferentePeso.matches("\\d+(\\.\\d+)?")
                                || !valorDiferenteCirc.matches("\\d+(\\.\\d+)?");


                        alertaAlterarPeso = new AlertDialog.Builder(ListaMedidas.this);
                        alertaAlterarPeso.setTitle("Alerta!");
                        // Esse if verifca se o usuario digitou dados inválidos(por exemplo: " ,", " . ", " .5", etc) ou não
                        if(invalid_input) {
                            alertaAlterarPeso.setMessage("Você digitou dados de forma incorreta, quer tentar novamente?");
                        }
                        else{
                            alertaAlterarPeso.setMessage("Você deseja alterar essa medição para?\n" + "Peso: " + valorDiferentePeso + " kg" +
                                    "\nCircunferência: " + valorDiferenteCirc + " cm");
                        }
                        // Caso Não
                        alertaAlterarPeso.setNegativeButton("NÃO",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editPeso.setText("");
                                        editCirc.setText("");
                                        editPeso.setHint("");
                                        editCirc.setHint("");
                                        Toast.makeText(ListaMedidas.this, "Operação cancelada", Toast.LENGTH_LONG).show();
                                    }
                                });
                        // Caso Sim
                        String finalValorDiferentePeso = valorDiferentePeso;
                        String finalValorDiferenteCirc = valorDiferenteCirc;
                        alertaAlterarPeso.setPositiveButton("SIM",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    if(invalid_input) {
                                        Toast.makeText(ListaMedidas.this, "Mude os valores da medição e clique em ALTERAR", Toast.LENGTH_LONG).show();

                                        String[] separados = value.split(" ");
                                        //editPeso.setText(separados[1]);
                                        editPeso.setText("");
                                        editPeso.setEnabled(true);

                                        //editCirc.setText(separados[5])
                                        editCirc.setText("");
                                        editCirc.setEnabled(true);
                                    } else {

                                        // FAZER CODIGO DE EDITAR AQUI

                                        double valorPeso = 0.0;
                                        double valorCirc = 0.0;

                                        try {
                                            valorPeso = Double.parseDouble(finalValorDiferentePeso);
                                            valorCirc = Double.parseDouble(finalValorDiferenteCirc);
                                        } catch (Exception e) {
                                            Toast.makeText(ListaMedidas.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                                            /*Intent intent = new Intent(ListaMedidas.this, MedidaView.class);
                                            intent.putExtra("Paciente", paciente);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);*/
                                            finish();
                                        }

                                        peso.setPeso(valorPeso);
                                        peso.setCircunferencia(valorCirc);
/*
                                        if (position == medidas.size() - 1) {
                                            // SIGNIFICA QUE É O PESO ATUAL QUE ELE ESTA EDITANDO, RECALCULE O IMC!
                                            double imc = paciente.getImc();
                                            paciente.setPeso(valorPeso);
                                            paciente.setCircunferencia(valorCirc);
                                        }
*/
                                        MedidaController.editMedida(peso);

                                        if (editCirc.getText().length() != 0){
                                            editCirc.setHint(String.format("%.2f", valorCirc));
                                            editCirc.setText("");
                                        }
                                        if(editPeso.getText().length() != 0) {
                                            editPeso.setHint(String.format("%.2f", valorPeso));
                                            editPeso.setText("");
                                        }

                                        /*Intent intent = new Intent(ListaMedidas.this, MedidaView.class);
                                        intent.putExtra("Paciente", paciente);
                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        */
                                        finish();

                                        Toast.makeText(ListaMedidas.this, "Valores alterados com sucesso", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        alertaAlterarPeso.create().show();

                    }
                });

            }
        });
    }

}
