package app.com.example.wagner.meupredi.View.Application.MainViews;

import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import app.com.example.wagner.meupredi.Controller.ConsultaController;
import app.com.example.wagner.meupredi.Model.ModelClass.Consulta;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.TelaLogin;
import app.com.example.wagner.meupredi.View.Application.Dicas;
import app.com.example.wagner.meupredi.View.Application.ListaTaxas;
import app.com.example.wagner.meupredi.View.Application.PopNotific;
import app.com.example.wagner.meupredi.View.Application.Sair;
import app.com.example.wagner.meupredi.View.Application.Tabs.Perfil.TabConsultas;
import app.com.example.wagner.meupredi.View.Application.Tabs.Perfil.TabCorpo;
import app.com.example.wagner.meupredi.View.Application.Tabs.Perfil.TabTaxas;

import static app.com.example.wagner.meupredi.R.layout.activity_perfil;


/**
 * Created by LeandroDias1 on 18/04/2017.
 */

@Deprecated
public class Perfil extends ActivityGroup {

    private TextView nomeUsuario;
    private ImageView coracao, configuracoes, notificacoes, iconeAlerta, iconeSair, informacao;
    private Paciente paciente;
    private AlertDialog.Builder alertaDuvidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(activity_perfil);

        coracao = (ImageView) findViewById(R.id.image_perfil_coracao);
        configuracoes = (ImageView) findViewById(R.id.image_perfil_dados);
        notificacoes = (ImageView) findViewById(R.id.notify_perfil_btm);
        iconeAlerta = (ImageView) findViewById(R.id.image_alerta_notificacoes_perfil);
        iconeSair = (ImageView) findViewById(R.id.image_sair_perfil);
        nomeUsuario = (TextView) findViewById(R.id.text_nome_usuario);
        informacao = (ImageView)findViewById(R.id.image_informacao_perfil);


        paciente = PacienteUpdater.getPaciente();//(Paciente) getIntent().getExtras().get("Paciente");

        nomeUsuario.setText(paciente.getNome().split(" ")[0]);
        /*
        *Difference between INVISIBLE and GONE.
        * INVISIBLE - The widget will be invisible but space for the widget will be show.
        * GONE - Both space and widget is invisible.
         */
        iconeAlerta.setVisibility(View.VISIBLE);
        int AlertaFlag = 1;
        if(AlertaFlag == 1){
            notificacoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Perfil.this, Dicas.class);
                    intent.putExtra("Paciente", paciente);
                    startActivity(intent);
                }
            });
        }

        ConsultaController.notifyConsulta(this, paciente);

        configuracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Dados.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });

        iconeSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, Sair.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
                finish();
            }
        });

        coracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil.this, PopNotific.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });

        TabHost abas = (TabHost) findViewById(R.id.tabhost);
        abas.setup(this.getLocalActivityManager());

        TabHost.TabSpec descritor = abas.newTabSpec("aba1");

        Intent intent = new Intent(this, TabTaxas.class);
        intent.putExtra("Paciente", paciente);

        descritor.setContent(intent);
        descritor.setIndicator("TAXAS");
        abas.addTab(descritor);

        descritor = abas.newTabSpec("aba2");

        intent = new Intent(this, TabCorpo.class);
        intent.putExtra("Paciente", paciente);

        descritor.setContent(intent);
        descritor.setIndicator("CORPO");
        abas.addTab(descritor);

        descritor = abas.newTabSpec("aba3");

        intent = new Intent(this, TabConsultas.class);
        intent.putExtra("Paciente", paciente);

        descritor.setContent(intent);
        descritor.setIndicator("CONSULTAS");
        abas.addTab(descritor);


        informacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaDuvidas = new AlertDialog.Builder(Perfil.this);
                alertaDuvidas.setTitle("Informativo");
                alertaDuvidas.setMessage("Clicando em alguma Taxa na lista, você poderá altera-la ou exclui-la\n");
                // Caso EDITAR
                alertaDuvidas.setNegativeButton("VOLTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(Perfil.this, "Clique para editar ou remover uma taxa da lista!", Toast.LENGTH_LONG).show();
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

    public void onNotify(Consulta consulta){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Notification.Builder notificationBuilder = new Notification.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_coracao)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_coracao))
                .setContentTitle("Notification from PreDi!")
                .setContentText("Você tem uma consulta em " + dateFormat.format(ConsultaController.getDateObject(consulta)) + ", dê uma verificada!");

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Perfil.this, TelaLogin.class);
        startActivity(intent);
    }

}

