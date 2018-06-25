package Proto.domain;

import Proto.dijkstra.PNode;
import Proto.domain.Student;
import Proto.domain.Supervisor;

import java.util.*;

public class Project {

	private int id;
	private String titel;
	private int maxTeilnehmer;
	private ArrayList<Student> teilnehmer = new ArrayList<>();
	private Collection<Supervisor> verantwortliche;
	private PNode pNode;
	
	public Project(int id, String titel, int maxTeilnehmer){
		this.id = id;
		this.titel = titel;
		this.maxTeilnehmer = maxTeilnehmer;
	}
	
	@Override
	public String toString(){
		return this.id +
				" " + titel + " " +
				" " + maxTeilnehmer +
				"\n";
	}
	
	//Erstellt eine PNode aus dem Project und "merkt" sich diese als Attribut, da die IDs unterschiedlich sind
	
	public PNode makePNode(int id){
		this.pNode = new PNode(id, this.maxTeilnehmer - this.teilnehmer.size());
		this.pNode.setProjekt(this);
		return this.pNode;
	}
	
	public int getId(){
		return id;
	}
	
	public PNode getPNode(){
		return pNode;
	}
	
	public String getTitel(){
		return titel;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setTitel(String titel){
		this.titel = titel;
	}
	
	public void setMaxTeilnehmer(int maxTeilnehmer){
		this.maxTeilnehmer = maxTeilnehmer;
	}
	
	public int getMaxTeilnehmer(){
		return maxTeilnehmer;
	}
	
	public void addTeilnehmer(Student s){
		if(teilnehmer.contains(s)){
			throw new IllegalArgumentException("Der Student " + s + " ist bereits Teilnehmer dieses Projekts.");
		}
		teilnehmer.add(s);
	}
	
	public ArrayList<Student> getTeilnehmer(){
		return teilnehmer;
	}
}