package app.com.example.wagner.meupredi.View.Application;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.MenuPrincipal;

/**
 * Created by LeandroDias1 on 18/04/2017.
 */

public class Exames extends Fragment{

    private TextView chamadaLipidograma, chamadaHemograma;
    private Dialog myDialog;
    private Button popUpBut;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View view = inflater.inflate(R.layout.fragment_exames, container, false);

        chamadaLipidograma = (TextView) view.findViewById(R.id.text_tab_corpo_peso);
        chamadaLipidograma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paciente paciente = ((MenuPrincipal)getActivity()).pegarPacienteMenu();

                Intent novoExame = new Intent(getActivity(), TelaExameLipidograma.class);
                novoExame.putExtra("Paciente", paciente);
                startActivity(novoExame);
            }
        });

        chamadaHemograma = (TextView) view.findViewById(R.id.text_tab_corpo_imc);
        chamadaHemograma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paciente paciente = ((MenuPrincipal)getActivity()).pegarPacienteMenu();

                Intent novoExame = new Intent(getActivity(), TelaExameHemograma.class);
                novoExame.putExtra("Paciente", paciente);
                startActivity(novoExame);
            }
        });

        popUpBut = (Button) view.findViewById(R.id.showPop);
        popUpBut.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                TextView txtClose;

                myDialog = new Dialog(getContext());

                myDialog.setContentView(R.layout.popup_exames);
                myDialog.show();

                txtClose = (TextView) myDialog.findViewById(R.id.closepopUp);
                txtClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
            }
        });


        return view;
    }
/*
    private void ShowPopup(View v){
        myDialog.setContentView(R.layout.popup_exames);
        myDialog.show();
    }
*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Exames");
    }

}