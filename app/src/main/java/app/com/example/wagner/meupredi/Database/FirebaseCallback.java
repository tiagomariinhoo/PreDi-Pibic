package app.com.example.wagner.meupredi.Database;

import java.util.List;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public interface FirebaseCallback<T>{
    /*public Object paciente;
    public List<Object> pacientes;

    FirebaseCallback(Object paciente, Class<?> type){
        this.paciente = paciente;
    }
    FirebaseCallback(List<Object> pacientes, Class<?> type){
        this.pacientes = pacientes;
    }*/

    public void onSuccess(T object);
    public void onFailure();
}