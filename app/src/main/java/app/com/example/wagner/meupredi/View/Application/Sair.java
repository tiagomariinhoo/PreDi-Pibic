package app.com.example.wagner.meupredi.View.Application;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Account.MenuPrincipal;
import app.com.example.wagner.meupredi.View.Account.TelaLogin;

/**
 * Created by LeandroDias1 on 18/04/2017.
 */

public class Sair extends Fragment  implements View.OnClickListener {

    CheckBox manterConectado;
    Button sair;
    TextView voltar;
    Paciente paciente;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //change R.layout.yourlayoutfilename for each of your fragments

        final View view = inflater.inflate(R.layout.fragment_sair, container, false);
        View telaLoginView = inflater.inflate(R.layout.activity_tela_login, container, false);
        sair = (Button) view.findViewById(R.id.btn_sair_sim_fragment);
        voltar = (TextView) view.findViewById(R.id.btn_sair_nao_fragment);
        manterConectado = (CheckBox) telaLoginView.findViewById(R.id.checkBox_manter_conectado_login);
        manterConectado.setChecked(false);

        final SharedPreferences settings = this.getActivity().getSharedPreferences(TelaLogin.PREFS_NAME, 0);
        final SharedPreferences.Editor editor = settings.edit();

        voltar.setOnClickListener(new View.OnClickListener()
        {
            //private Fragment fragment = new Perfil();
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MenuPrincipal.class);
                paciente = ((MenuPrincipal)getActivity()).pegarPacienteMenu();
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
            }
        });

        sair.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editor.clear();
                editor.commit();

                Intent intent = new Intent(getActivity(), TelaLogin.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Sair");
    }

    @Override
    public void onClick(View v) {

    }
}
