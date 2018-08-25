package app.com.example.wagner.meupredi.Controller;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import app.com.example.wagner.meupredi.Database.TaxasDAO;
import app.com.example.wagner.meupredi.Model.DatabaseHandler;
import app.com.example.wagner.meupredi.Model.ModelClass.Taxas;
import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

/**
 * Created by tico_ on 31/01/2018.
 */

public abstract class TaxasController {

    public static Task<Void> addTaxas(Paciente paciente){
        //db.modelAtualizarTaxas(paciente);
        return TaxasDAO.createTaxas(paciente);
    }

    public static Task<Void> editTaxas(Taxas taxas){
        return TaxasDAO.updateTaxas(taxas);
    }

    public static Task<QuerySnapshot> getLastInfoTaxas(Paciente paciente){
        return TaxasDAO.getTaxas(paciente);
    }

    public static Task<QuerySnapshot> getAllTaxas(Paciente paciente){
        return TaxasDAO.getAllTaxas(paciente);
    }

    public static Task<Void> eraseLastInfoTaxas(Taxas taxas){
        return TaxasDAO.deleteTaxas(taxas);
    }

}
