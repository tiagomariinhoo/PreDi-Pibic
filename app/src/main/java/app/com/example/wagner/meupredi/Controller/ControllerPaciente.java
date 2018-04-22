package app.com.example.wagner.meupredi.Controller;

import android.content.Context;

import java.util.List;

import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public class ControllerPaciente {
    DatabaseHandler db;

    public ControllerPaciente(Context context) {
        db = new DatabaseHandler(context);
    }

    public String addPaciente(Paciente paciente){
        return db.modelAddPaciente(paciente);
    }

    public List<Paciente> getAllUsers(){
      return  db.modelGetAllUsers();
    }

    public Paciente getPaciente(String email){
        return db.modelGetPaciente(email);
    }

    public void deleteAllPacientes(){
        db.modelDeletAllPacientes();
    }

    public Paciente verificarLogin(String email, String senha){
        return db.modelVerificarLogin(email, senha);
    }

    public Paciente verificarEmail(String email){
        return db.modelVerificarEmail(email);
    }

    public boolean atualizarPaciente(Paciente paciente){
        return db.modelAtualizarPaciente(paciente);
    }

}
