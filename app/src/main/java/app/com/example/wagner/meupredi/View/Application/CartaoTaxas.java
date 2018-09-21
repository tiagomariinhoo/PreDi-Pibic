package app.com.example.wagner.meupredi.View.Application;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;

import app.com.example.wagner.meupredi.Controller.MedidaController;
import app.com.example.wagner.meupredi.Controller.TaxasController;
import app.com.example.wagner.meupredi.Model.ModelClass.Medida;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.R;

public class CartaoTaxas extends Activity {

    private Paciente paciente;
    private Taxas taxa;
    private EditText editJejum, edit75g, editGlicada;
    private ImageView btnEditar, btnExcluir;
    private AlertDialog.Builder alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_taxas);

        paciente = (Paciente) getIntent().getExtras().get("Paciente");
        taxa = (Taxas) getIntent().getExtras().get("Medida");

        editJejum = findViewById(R.id.edit_text_editar_jejum_na_lista);
        edit75g = findViewById(R.id.edit_text_editar_apos75g_na_lista);
        editGlicada = findViewById(R.id.edit_text_editar_glicada_na_lista);

        editJejum.setHint(""+taxa.getGlicoseJejum());
        edit75g.setHint(""+taxa.getGlicose75g());
        editGlicada.setHint(""+taxa.getHemoglobinaGlico());

        btnEditar = findViewById(R.id.btn_editar_peso_lista_taxas);
        btnExcluir = findViewById(R.id.btn_excluir_peso_lista_taxas);

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxasController.eraseLastInfo(taxa).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Peso ", "Ultimo peso excluído!");
                        Toast.makeText(CartaoTaxas.this, "Taxa removida com sucesso!", Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
        });
    }
}
