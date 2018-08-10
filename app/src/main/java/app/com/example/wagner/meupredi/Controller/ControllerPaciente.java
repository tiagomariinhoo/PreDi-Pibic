package app.com.example.wagner.meupredi.Controller;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import app.com.example.wagner.meupredi.Database.PacienteDAO;
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

    public boolean addPaciente(Paciente paciente){
        return PacienteDAO.createPaciente(paciente);
        //return db.modelAddPaciente(paciente).equals("Registro inserido com sucesso!");
    }

    public Task<QuerySnapshot> getAllUsers(){
        return PacienteDAO.queryAllPacientes();
        //return  db.modelGetAllUsers();
    }

    public Task<DocumentSnapshot> getPaciente(String email){
        return PacienteDAO.queryPaciente(email);
        //return db.modelGetPaciente(email);
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
        return PacienteDAO.updatePaciente(paciente);
        //return db.modelAtualizarPaciente(paciente);
    }

}
