package app.com.example.wagner.meupredi.View.Account;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import app.com.example.wagner.meupredi.Controller.ControllerPaciente;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;
import app.com.example.wagner.meupredi.R;
import app.com.example.wagner.meupredi.View.Application.Consultas;
import app.com.example.wagner.meupredi.View.Application.Exames;
import app.com.example.wagner.meupredi.View.Application.Exercicios;
import app.com.example.wagner.meupredi.View.Application.Medicamentos;
import app.com.example.wagner.meupredi.View.Application.Perfil;
import app.com.example.wagner.meupredi.View.Application.Sair;

public class MenuPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Paciente paciente;
    private TextView nome;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        //DEBUG: imprime informacoes do paciente, atualizadas, para verificar se estao corretas
        Log.d("Infos finais: ", "menu principal");
        Log.d("Nome : ", paciente.get_nome());
        Log.d("Senha : ", paciente.get_senha());
        Log.d("Email: ", paciente.get_email());
        Log.d("Sexo: ", String.valueOf(paciente.get_sexo()));
        Log.d("Nascimento: ", paciente.get_nascimento());
        Log.d("Idade : ", String.valueOf(paciente.get_idade()));
        Log.d("Circunferencia : ", String.valueOf(paciente.get_circunferencia()));
        Log.d("Peso : ", String.valueOf(paciente.get_peso()));
        Log.d("Altura : ", String.valueOf(paciente.get_altura()));
        Log.d("IMC : ", String.valueOf(paciente.get_imc()));
        Log.d("HBA1C : ", String.valueOf(paciente.get_hba1c()));
        Log.d("GlicoseJejum : ", String.valueOf(paciente.get_glicosejejum()));
        Log.d("Glicose75g : ", String.valueOf(paciente.get_glicose75g()));
        Log.d("Colesterol : ", String.valueOf(paciente.get_colesterol()));
        Log.d("Lipidograma : ", String.valueOf(paciente.get_lipidograma()));
        Log.d("Hemograma : ", String.valueOf(paciente.get_hemograma()));
        Log.d("Tireoide : ", String.valueOf(paciente.get_tireoide()));


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.getBackground().setAlpha(60);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        nome = (TextView) header.findViewById(R.id.nomePacienteNavigation);
        nome.setText(paciente.get_nome());

        email = (TextView) header.findViewById(R.id.emailPacienteNavigation);
        email.setText((paciente.get_email()));

        displaySelectedScreen(R.id.nav_perfil);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        //inicializa o fragmento selecionado
        switch (itemId) {
            case R.id.nav_perfil:
                Intent intentPerfil = new Intent(MenuPrincipal.this, Perfil.class);
                intentPerfil.putExtra("Paciente", paciente);
                startActivity(intentPerfil);
                break;
            case R.id.nav_exames:
                fragment = new Exames();
                break;
            case R.id.nav_consultas:
                Intent intent = new Intent(MenuPrincipal.this, Consultas.class);
                intent.putExtra("Paciente", paciente);
                startActivity(intent);
                break;
            case R.id.nav_exercicios:
                fragment = new Exercicios();
                break;
            case R.id.nav_medicamentos:
                fragment = new Medicamentos();
                break;
            case R.id.nav_sair:
                fragment = new Sair();
                break;
        }

        //substitui a tela atual
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public Paciente pegarPacienteMenu(){

        //pega dados atualizados do paciente no banco
        //DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        ControllerPaciente controllerPaciente = new ControllerPaciente(getApplicationContext());
        paciente = controllerPaciente.getPaciente(paciente.get_email());
        paciente.calculo_diabetes(getApplicationContext());
        paciente = (Paciente) getIntent().getExtras().get("Paciente");

        return paciente;
    }
}
