package app.com.example.wagner.meupredi.Controller;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import app.com.example.wagner.meupredi.Database.PacienteDAO;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class ControllerPaciente {

    /*public ControllerPaciente(Context context) {
        db = new DatabaseHandler(context);
    }*/

    public static Task<Void> addPaciente(Paciente paciente){
        return PacienteDAO.createPaciente(paciente);//db.modelAddPaciente(paciente);
    }

    public static Task<Void> atualizarPaciente(Paciente paciente){
        return PacienteDAO.updatePaciente(paciente);//db.modelAtualizarPaciente(paciente);
    }

    public Task<QuerySnapshot> getAllUsers(){
      return PacienteDAO.getAllPacientes();//db.modelGetAllUsers();
    }

    public static Task<DocumentSnapshot> getPaciente(String email){
        return PacienteDAO.getPaciente(email);
    }
/*
    public void deleteAllPacientes(){
        db.modelDeletAllPacientes();
    }
*/
    public static Task<QuerySnapshot> verificarLogin(String email, String senha){
        return PacienteDAO.authPaciente(email, senha);
    }
/*
    public Paciente verificarEmail(String email){
        return PacienteDAO.getPaciente(email);//db.modelVerificarEmail(email);
    }
*/
}
