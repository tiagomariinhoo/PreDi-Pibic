package app.com.example.wagner.meupredi.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public class PacienteDAO {

    public static String createPaciente(Paciente paciente){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("paciente");

        myRef = myRef.child(Integer.toString(paciente.getId()));
        myRef.setValue(paciente);
        /*myRef.child("nome").setValue(paciente.getNome());
        myRef.child("senha").setValue(paciente.getSenha());
        myRef.child("email").setValue(paciente.getEmail());
        myRef.child("sexo").setValue(paciente.getSexo());
        myRef.child("nascimento").setValue(paciente.getNascimento());
        myRef.child("idade").setValue(paciente.getIdade());
        myRef.child("exTotal").setValue(paciente.getExTotal());
        //myRef.child("ultimaDica").setValue(paciente.get_ultimaDica());
        myRef.child("circunferencia").setValue(paciente.getCircunferencia());
        myRef.child("peso").setValue(paciente.getPeso()); // check
        myRef.child("altura").setValue(paciente.getAltura()); // check
        myRef.child("imc").setValue(paciente.getImc());
        myRef.child("hba1c").setValue(paciente.getHba1c());
        myRef.child("glicoseJejum").setValue(paciente.getGlicoseJejum());
        myRef.child("glicose75g").setValue(paciente.getGlicose75g());
        myRef.child("hemoglobinaGlicolisada").setValue(paciente.getHemoglobinaGlicolisada());
        myRef.child("colesterol").setValue(paciente.getColesterol());
        myRef.child("lipidograma").setValue(paciente.getLipidograma());
        myRef.child("hemograma").setValue(paciente.getHemograma());
        myRef.child("tireoide").setValue(paciente.getTireoide());
        */

        return "";
    }
    /*

    int _id;
    String _nome;
    String _senha;
    String _email;
    String _sexo;
    String _nascimento;
    int _idade;
    int _exTotal; //Total atual
    int _exMax; //Meta da semana
    int ultimaDica;
    double _circunferencia;
    double _peso;
    double _altura;
    double _imc;
    double _hba1c;
    double _glicosejejum;
    double _glicose75g;
    double _hemoglobinaglicolisada;
    double _colesterol;
    double _lipidograma; // Não está sendo usado
    double _hemograma; // Não está sendo usado
    double _tireoide;
    ArrayList<Float> _pesos

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
    */
}
