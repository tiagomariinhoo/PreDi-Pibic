package app.com.example.wagner.meupredi.Controller.CDA;

import android.util.Log;

import java.io.IOException;

import br.com.multcare.ClinicalDocument;
import br.com.multcare.bean.Authenticator;
import br.com.multcare.bean.Exams;
import br.com.multcare.bean.Header;
import br.com.multcare.bean.LaboratoryExams;
import br.com.multcare.bean.Patient;
import br.com.multcare.bean.Related;
import br.com.multcare.bean.ResponsibleParty;

import app.com.example.wagner.meupredi.Model.Paciente;

public class createCDA {

    private ClinicalDocument cda;
    private Patient patient;
    private Header header;
    private LaboratoryExams labExams;
    private Authenticator authenticator;
    private Exams exams;
    private Parse parse;
    private Related related;
    private ResponsibleParty responsibleParty;

    public createCDA(){
        this.cda = new ClinicalDocument();
        this.parse = new Parse();
        this.patient = new Patient();
        this.header = new Header();
        this.labExams = new LaboratoryExams();
        this.exams = new Exams();
        this.authenticator = new Authenticator();
        this.related = new Related();
        this.responsibleParty = new ResponsibleParty();

    }

    public void Cda(Paciente paciente, String path) throws IOException{
        patient = parse.patientParser(paciente);
        labExams = parse.laboratoryExamsParse(paciente);
        exams = parse.examsParse(paciente);
        header = parse.headerParse();
        //	authenticator = parse.authenticatorParse();
        related = parse.relatedParse(paciente);
        responsibleParty = parse.responsibleParty();
        cda.setResponsibleParty(responsibleParty);
        cda.setHeader(header);
        cda.setPatient(patient);
        cda.setAuthenticator(authenticator);
        cda.setRelated(related);
        cda.setLaboratoryExams(labExams);
        cda.setExams(exams);
        cda.generateCDAFile(path);
        Log.d("CDA", "cda criado");
//        ValidateCDA vcda = new ValidateCDA();
//		System.out.println(vcda.validationCDAFile("2056.xml"));


    }

}
