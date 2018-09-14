package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import android.support.v7.app.AlertDialog.Builder;

public class CartaoMedida extends Activity {

    private Paciente paciente;
    private Medida medida;
    private EditText editPeso, editCirc;
    private ImageView btnEditar, btnExcluir;
    private Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_medida);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) ( width*.8), (int) (height*.5));

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        medida = (Medida) getIntent().getExtras().get("Medida");

        editPeso = findViewById(R.id.edit_text_editar_peso_na_lista);
        editCirc = findViewById(R.id.edit_text_editar_circunferencia_na_lista);

        btnEditar = findViewById(R.id.btn_editar_peso_lista_pesos);
        btnExcluir = findViewById(R.id.btn_excluir_peso_lista_pesos);

        editPeso.setHint(""+medida.getPeso());
        editCirc.setHint(""+medida.getCircunferencia());
        editPeso.setRawInputType(Configuration.KEYBOARD_QWERTY);
        editCirc.setRawInputType(Configuration.KEYBOARD_QWERTY);

        btnEditar.setOnClickListener(new View.OnClickListener() {
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
                // Essa variavel verifca se o usuario digitou dados inválidos(por exemplo: " ,", " . ", " .5", etc) ou não
                boolean invalid_input = !valorDiferentePeso.matches("\\d+(\\.\\d+)?")
                        || !valorDiferenteCirc.matches("\\d+(\\.\\d+)?");

                alerta = new android.support.v7.app.AlertDialog.Builder(CartaoMedida.this);
                alerta.setTitle("Alerta!");
                // Esse if verifca se o usuario digitou dados inválidos(por exemplo: " ,", " . ", " .5", etc) ou não
                if(invalid_input) {
                    alerta.setMessage("Você digitou dados de forma incorreta, quer tentar novamente?");
                }
                else{
                    alerta.setMessage("Você deseja alterar essa medição para?\n" + "Peso: " + valorDiferentePeso + " kg" +
                            "\nCircunferência: " + valorDiferenteCirc + " cm");
                }

                //           Caso Não
                                alerta.setNegativeButton("NÃO",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                editPeso.setText("");
                                                editCirc.setText("");
                                                editPeso.setHint("");
                                                editCirc.setHint("");
                                Toast.makeText(CartaoMedida.this, "Operação cancelada", Toast.LENGTH_LONG).show();
                            }
                        });
                // Caso Sim
                String finalValorDiferentePeso = valorDiferentePeso;
                String finalValorDiferenteCirc = valorDiferenteCirc;
                alerta.setPositiveButton("SIM",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(invalid_input) {
                                    Toast.makeText(CartaoMedida.this, "Mude os valores da medição e clique em ALTERAR", Toast.LENGTH_LONG).show();
                                    editPeso.setText("");
                                    editCirc.setText("");
                                } else {

                                    // FAZER CODIGO DE EDITAR AQUI

                                    double valorPeso = 0.0;
                                    double valorCirc = 0.0;

                                    try {
                                        valorPeso = Double.parseDouble(finalValorDiferentePeso);
                                        valorCirc = Double.parseDouble(finalValorDiferenteCirc);
                                    } catch (Exception e) {
                                        Toast.makeText(CartaoMedida.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                                        finish();
                                    }

                                    medida.setPeso(valorPeso);
                                    medida.setCircunferencia(valorCirc);

                                    MedidaController.editMedida(medida);
                                    finish();
                                    Toast.makeText(CartaoMedida.this, "Valores alterados com sucesso", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                alerta.create().show();
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedidaController.eraseLastInfo(medida).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Peso ", "Ultimo peso excluído!");
                        Toast.makeText(CartaoMedida.this, "Medição removida com sucesso!", Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
        });

    }
}
