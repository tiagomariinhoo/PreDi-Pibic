package app.com.example.wagner.meupredi.Controller;

import android.content.Context;

import java.text.ParseException;
import java.util.List;

import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.ExameClass;
import app.com.example.wagner.meupredi.Model.ModelClass.HemogramaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.LipidogramaClass;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public class ControllerExames {
    DatabaseHandler db;

    public ControllerExames(Context context) {
        db = new DatabaseHandler(context);
    }

    public String addExames(ExameClass exame){
        return db.modelAddExame(exame);
    }

    public String addLipidograma(LipidogramaClass lipidograma){
        return db.modelAddLipidograma(lipidograma);
    }

    public String addHemograma(HemogramaClass hemograma){
        return db.modelAddHemograma(hemograma);
    }

    public List<ExameClass> getAllExames() throws ParseException {
        return db.modelGetAllExames();
    }

    public void atualizarTaxas(Paciente paciente){
        db.modelAtualizarTaxas(paciente);
    }

    public Paciente getUltimasTaxas(Paciente paciente){
        return db.modelGetUltimasTaxas(paciente);
    }

}
