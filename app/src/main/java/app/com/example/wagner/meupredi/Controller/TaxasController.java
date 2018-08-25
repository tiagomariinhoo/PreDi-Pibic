package app.com.example.wagner.meupredi.Controller;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Database.TaxasDAO;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxa;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public class TaxaController {
    DatabaseHandler db;

    public TaxaController(Context context) {
        db = new DatabaseHandler(context);
    }

    public static Task<Void> addTaxas(Paciente paciente){
        //db.modelAtualizarTaxas(paciente);
        return TaxasDAO.createTaxas(paciente);
    }

    public static Task<Void> editTaxas(Taxa taxa){
        return TaxasDAO.updateTaxas(taxa);
    }

    public static Task<QuerySnapshot> getUltimasTaxas(Paciente paciente){
        return TaxasDAO.getTaxas(paciente);
    }

    get getAll eraseLast

    public List<Taxa> getAllExames() throws ParseException {
        return db.modelGetAllExames();
    }

    /*public Paciente getUltimasTaxas(Paciente paciente){
        return db.modelGetUltimasTaxas(paciente);
    }*/

    public boolean eraseLastInfoTaxa(Taxa exame){ return db.eraseLastInfoTaxas(exame);}

    public ArrayList<Taxa> getAllExamesClass(Paciente paciente) throws Exception { return db.modelGetAllExameClass(paciente); }

    public boolean editExame(Taxa exame){return db.modelEditExame(exame);}

    public ArrayList<Float> getGlicosesJejum(Paciente paciente){
        return db.modelGetGlicosesJejum(paciente);
    }

    public ArrayList<Float> getGlicoses75g(Paciente paciente){
        return db.modelGetGlicoses75g(paciente);
    }

    public ArrayList<Float> getHemoglobinas(Paciente paciente){
        return db.modelGetHemoglobinas(paciente);
    }
}
