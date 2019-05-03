package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import app.com.example.wagner.meupredi.Controller.TaxasController;
import app.com.example.wagner.meupredi.Model.Paciente;
import app.com.example.wagner.meupredi.Model.Taxas;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.MainViews.PacienteUpdater;

public class CartaoTaxas extends Activity {

    private Paciente paciente;
    private EditText editJejum, edit75g, editGlicada;
    private ImageView btnEditar, btnExcluir;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_taxas);

        editJejum = findViewById(R.id.edit_text_editar_jejum_na_lista);
        editJejum.setRawInputType(Configuration.KEYBOARD_QWERTY);
        edit75g = findViewById(R.id.edit_text_editar_apos75g_na_lista);
        edit75g.setRawInputType(Configuration.KEYBOARD_QWERTY);
        editGlicada = findViewById(R.id.edit_text_editar_glicada_na_lista);
        editGlicada.setRawInputType(Configuration.KEYBOARD_QWERTY);

        btnEditar = findViewById(R.id.btn_editar_peso_lista_taxas);
        btnExcluir = findViewById(R.id.btn_excluir_peso_lista_taxas);

        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");
        String taxaId = (String) getIntent().getExtras().get("Id Taxas");
        TaxasController.getSpecificTaxas(paciente, taxaId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Taxas taxa = documentSnapshot.toObject(Taxas.class);

                editJejum.setHint(""+taxa.getGlicoseJejum());
                edit75g.setHint(""+taxa.getGlicose75g());
                editGlicada.setHint(""+taxa.getHemoglobinaGlico());

                btnExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TaxasController.eraseLastInfo(taxa).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("Taxas", "Ultimas taxas excluídas!");
                                Toast.makeText(CartaoTaxas.this, "Taxa removida com sucesso!", Toast.LENGTH_LONG).show();
                            }
                        });
                        finish();
                    }
                });

                btnEditar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            String valorDiferenteJejum = editJejum.getText().toString();
                            String valorDiferenteApos75g = edit75g.getText().toString();
                            String valorDiferenteGlicada = editGlicada.getText().toString();

                            if (valorDiferenteJejum.length() <= 0) {
                                Log.d("HINTJEJUM: ", editJejum.getHint().toString());
                                valorDiferenteJejum = editJejum.getHint().toString();
                            }

                            if (valorDiferenteApos75g.length() <= 0) {
                                Log.d("HINT75G: ", edit75g.getHint().toString());
                                valorDiferenteApos75g = edit75g.getHint().toString();
                            }

                            if (valorDiferenteGlicada.length() <= 0) {
                                Log.d("HINTGLICADA: ", editGlicada.getHint().toString());
                                valorDiferenteApos75g = editGlicada.getHint().toString();
                            }

                            //formata a string para transformar corretamente para double (substitui virgula por ponto e limita a uma casa decimal)
                            valorDiferenteJejum = valorDiferenteJejum.replace(',', '.');
                            valorDiferenteApos75g = valorDiferenteApos75g.replace(',', '.');
                            // Essa variavel verifca se o usuario digitou dados inválidos(por exemplo: " ,", " . ", " .5", etc) ou não

                            boolean invalid_input = !valorDiferenteJejum.matches("\\d+(\\.\\d+)?")
                                    || !valorDiferenteApos75g.matches("\\d+(\\.\\d+)?") || !valorDiferenteGlicada.matches("\\d+(\\.\\d+)?");

                            alerta = new android.support.v7.app.AlertDialog.Builder(CartaoTaxas.this);
                            alerta.setTitle("Alerta!");
                            // Esse if verifca se o usuario digitou dados inválidos(por exemplo: " ,", " . ", " .5", etc) ou não
                            if (invalid_input) {
                                alerta.setMessage("Você digitou dados de forma incorreta, quer tentar novamente?");
                            } else {
                                alerta.setMessage("Você deseja alterar essas taxas para?\n" + "Glicose Jejum: " + valorDiferenteJejum + " mg/dL" +
                                        "\nGlicose após 75g: " + valorDiferenteApos75g + " mg/dL" + "\nHemoglobina Glicada: " + valorDiferenteGlicada + " %");
                            }

                            //           Caso Não
                            alerta.setNegativeButton("NÃO",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            editJejum.setText("");
                                            edit75g.setText("");
                                            editGlicada.setText("");
                                            Toast.makeText(CartaoTaxas.this, "Operação cancelada", Toast.LENGTH_LONG).show();
                                        }
                                    });
                            // Caso Sim
                            String finalValorDiferenteJejum = valorDiferenteJejum;
                            String finalValorDiferente75g = valorDiferenteApos75g;
                            String finalValorDiferenteGlicada = valorDiferenteGlicada;
                            alerta.setPositiveButton("SIM",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if (invalid_input) {
                                                Toast.makeText(CartaoTaxas.this, "Mude os valores da medição e clique em ALTERAR", Toast.LENGTH_LONG).show();
                                                editJejum.setText("");
                                                edit75g.setText("");
                                                editGlicada.setText("");
                                            } else {

                                                // FAZER CODIGO DE EDITAR AQUI

                                                double valorJejum = 0.0;
                                                double valor75g = 0.0;
                                                double valorGlicada = 0.0;

                                                try {
                                                    valorJejum = Double.parseDouble(finalValorDiferenteJejum);
                                                    valor75g = Double.parseDouble(finalValorDiferente75g);
                                                    valorGlicada = Double.parseDouble(finalValorDiferenteGlicada);
                                                } catch (Exception e) {
                                                    Toast.makeText(CartaoTaxas.this, "Por favor, digite os dados corretamente!", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }

                                                taxa.setGlicoseJejum(valorJejum);
                                                taxa.setGlicose75g(valor75g);
                                                taxa.setHemoglobinaGlico(valorGlicada);

                                                TaxasController.editTaxas(taxa);
                                                finish();
                                                Toast.makeText(CartaoTaxas.this, "Valores alterados com sucesso", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            alerta.create().show();
                        }
                });

            }
        });
    }
}
