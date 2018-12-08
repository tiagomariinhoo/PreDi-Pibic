package app.com.example.wagner.meupredi.Controller.CDA;

import java.sql.Date;
import java.util.ArrayList;

import br.com.multcare.bean.Authenticator;
import br.com.multcare.bean.Exams;
import br.com.multcare.bean.Header;
import br.com.multcare.bean.LaboratoryExams;
import br.com.multcare.bean.Patient;
import br.com.multcare.bean.Related;
import br.com.multcare.bean.ResponsibleParty;

import app.com.example.wagner.meupredi.Model.ModelClass.Paciente;

public class Parse {
	
	public Header headerParse(){
		Header header = new Header();
		header.setCode("410.9");
		Date d = new Date(System.currentTimeMillis());
		String date = d.toString();
		date = date.replace("-", "");
		header.setEfetiveTime(date);
		header.setExtension2("2.16.840.1.113883.3.933");
		return header;
		
	}
	
	public ResponsibleParty responsibleParty(){
		ResponsibleParty responsibleparty = new ResponsibleParty();
		responsibleparty.setDate("0");
		responsibleparty.setPostal("0");
		return responsibleparty;
	}
	
	public Authenticator authenticatorParse(){
		Authenticator authenticator = new Authenticator();
		authenticator.setCode("Multicare application");
		return authenticator;
	}
	
	
	public Patient patientParser(Paciente pacienteApp){
		Patient patient = new Patient();
	//	patient.setId(pacienteApp.getId());
		patient.setName(pacienteApp.getNome());
		patient.setBirth(pacienteApp.getNascimento());
		return patient;
		
	}
	
	public Related relatedParse(Paciente pacienteApp){
		Related related = new Related();
	//	related.setID(Integer.toString(pacienteApp.getId()));
		related.setExtension("BB35");
		related.setCode("RPLC");
		related.setVersion("1");
		return related;
	}
	
	
	public Exams examsParse(Paciente pacienteApp){
		Exams exams = new Exams();
		ArrayList<String> content = new ArrayList<String>();		
		if(pacienteApp.getCircunferencia() != -1)
		{
			content.add("Circunferencia: " + Double.toString(pacienteApp.getCircunferencia()) + " cm\n");			 
		}
		if(pacienteApp.getPeso() != -1)
		{
			content.add("Peso: " + Double.toString(pacienteApp.getPeso()) + " Kg\n");			 
		}
		if(pacienteApp.getAltura() != -1)
		{
			content.add("Altura: " + Double.toString(pacienteApp.getAltura()) + " m\n");			 
		}
		if(pacienteApp.getImc() != -1)
		{
			content.add("IMC: " + Double.toString(pacienteApp.getImc()) + "\n");
		}			
		exams.setContent(content);
		return exams;
	}
	
	public LaboratoryExams laboratoryExamsParse(Paciente pacienteApp){
		LaboratoryExams labExams = new LaboratoryExams();
		ArrayList<String> content = new ArrayList<String>();
		if(pacienteApp.getGlicoseJejum() != -1)
		{
			content.add("Glicose em jejum: " + Double.toString(pacienteApp.getGlicoseJejum()) + " mg/dL\n");
		}
		if(pacienteApp.getHemoglobinaGlicolisada() != -1)
		{
			content.add("Hemoglobina glicolisada: " + Double.toString(pacienteApp.getHemoglobinaGlicolisada()) + " %\n");			 
		}
		if(pacienteApp.getGlicose75g() != -1)
		{
			content.add("Glicose depois de 75g: " + Double.toString(pacienteApp.getGlicose75g()) + " mg/dL\n");			 
		}
		if(pacienteApp.getColesterol() != -1)
		{
			content.add("Colesterol: "+ Double.toString(pacienteApp.getColesterol()) + " mg/dL\n");		 
		}
		labExams.setContent(content);
		return labExams;
	}
	
	
	
	

}
