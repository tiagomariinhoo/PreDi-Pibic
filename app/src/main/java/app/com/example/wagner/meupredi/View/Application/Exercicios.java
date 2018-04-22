package app.com.example.wagner.meupredi.View.Application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.MenuPrincipal;

/**
 * Created by LeandroDias1 on 18/04/2017.
 */

public class Exercicios extends Fragment {

    private ImageView chamadaDesempenho;
    private ImageView chamadaGinasio;
    private ImageView start, reset, pause;
    private Chronometer cronometro;
    private long ultimaPausa;
    private TextView finalizar;
    private Paciente paciente;
    private long tempoCorrido;
    private int horas, minutos, segundos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_exercicios, container, false);

        start = (ImageView) view.findViewById(R.id.btn_start_exercicios);
        pause = (ImageView) view.findViewById(R.id.btn_pause_exercicios);
        reset = (ImageView) view.findViewById(R.id.btn_reset_exercicios);
        cronometro = (Chronometer) view.findViewById(R.id.crn_cronometro_exercicio);
        finalizar = (TextView) view.findViewById(R.id.text_finalizar_treino);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ultimaPausa != 0) {
                    cronometro.setBase(cronometro.getBase() + SystemClock.elapsedRealtime() - ultimaPausa);
                }
                else{
                    cronometro.setBase(SystemClock.elapsedRealtime());
                }
                start.setVisibility(view.INVISIBLE);
                pause.setVisibility(view.VISIBLE);
                cronometro.start();
                start.setEnabled(false);
                pause.setEnabled(true);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ultimaPausa = SystemClock.elapsedRealtime();
                cronometro.stop();
                pause.setVisibility(view.INVISIBLE);
                pause.setEnabled(false);
                start.setVisibility(view.VISIBLE);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cronometro.stop();
                cronometro.setBase(SystemClock.elapsedRealtime());
                ultimaPausa = 0;
                pause.setEnabled(false);
                start.setEnabled(true);
            }
        });

        finalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CODIGO PARA PAUSAR O CRONOMETRO
                ultimaPausa = SystemClock.elapsedRealtime();
                cronometro.stop();
                pause.setEnabled(false);
                start.setEnabled(true);

                new AlertDialog.Builder(getActivity()).setTitle("Finalizar Treino")
                        .setMessage("Deseja realmente finalizar o seu treino?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // CODIGO PARA RESETAR O CRONOMETRO
                                cronometro.stop();
                                cronometro.setBase(SystemClock.elapsedRealtime());
                                ultimaPausa = 0;
                                pause.setEnabled(false);
                                start.setEnabled(true);
                                Toast.makeText(getActivity(), "Seu treino foi finalizado com sucesso!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // CODIGO PARA VOLTAR A RODAR O CRONOMETRO

                                    if(ultimaPausa != 0) {
                                        cronometro.setBase(cronometro.getBase() + SystemClock.elapsedRealtime() - ultimaPausa);
                                    }

                                    else{
                                        cronometro.setBase(SystemClock.elapsedRealtime());
                                    }
                                    cronometro.start();
                                    start.setEnabled(false);
                                    pause.setEnabled(true);

                            }
                        }).show();

                // PEGANDO O PROGRESSO DO PACIENTE AO FINALIZAR O TREINO
                tempoCorrido = ultimaPausa - cronometro.getBase();
                horas = (int) (tempoCorrido / 3600000);
                minutos = (int) (tempoCorrido - horas * 3600000) / 60000;
                segundos = (int) (tempoCorrido - horas * 3600000 - minutos * 60000) / 1000;

                Log.d("hora: ",String.valueOf(horas));
                Log.d("minutos: ",String.valueOf(minutos));
                Log.d("segundos: ",String.valueOf(segundos));
            }
        });


        chamadaGinasio = (ImageView) view.findViewById(R.id.image_chamada_ginasio);
        chamadaGinasio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paciente = ((MenuPrincipal)getActivity()).pegarPacienteMenu();
                Intent telaGinasio = new Intent(getActivity(), Ginasio.class);
                telaGinasio.putExtra("Paciente", paciente);
                startActivity(telaGinasio);
            }
        });

        chamadaDesempenho = (ImageView) view.findViewById(R.id.text_veja_seu_desempenho);
        chamadaDesempenho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent desempenho = new Intent(getActivity(), Desempenho.class);
                startActivity(desempenho);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Exercicios");
    }

}