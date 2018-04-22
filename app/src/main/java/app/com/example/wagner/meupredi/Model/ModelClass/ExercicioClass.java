package app.com.example.wagner.meupredi.Model.ModelClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pichau on 06/07/2017.
 */

public class ExercicioClass {
    String nome;
    int idPaciente;
    int data;

    int tempo;


    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
}
