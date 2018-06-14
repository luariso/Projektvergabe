package Proto.domain;

import Proto.dijkstra.Projekt;
import Proto.dijkstra.SNode;

import java.util.*;

public class Student extends Nutzer {
	
	private int id;
	private ArrayList<Bewertung> bewertungen = new ArrayList<>();
	private Projekt projekt;
	private String studienfach;
	private String matrikelNummer;
	private SNode sNode;
	
	public Student(int id, String surename, String name, String matrikelNummer){
		super(surename, name);
		this.id = id;
		this.matrikelNummer = matrikelNummer;
	}
	
	public void addBewertungen(int projekt, int note){
		this.bewertungen.add(new Bewertung(this, Verwaltung.getProjekt(projekt), note));
	}
	
	@Override
	public String toString(){
		String ergebnis = this.name + " " +
				" " + this.name;
//		if(this.sNode != null){
//			ergebnis += " " + this.sNode.getId();
//		}
//		if(this.projekt != null){
//			ergebnis += " " + this.projekt.getTitel() +
//					" " + this.getZufriedenheit();
//
//		}
//		ergebnis += '\n';
		return ergebnis;
	}
	
	//Erstellt eine SNode aus dem Student und "merkt" sich diese als Attribut, da die IDs unterschiedlich sind
	public SNode makeSNode(int id){
		this.sNode = new SNode(id, getBewertungsNoten());
		this.sNode.setStudent(this);
		return this.sNode;
	}
	
	//Erstellt eine nach den IDs der PNodes geordnete Liste von Noten aus den vorhandenen Bewertungen
	public ArrayList<Integer> getBewertungsNoten(){
		ArrayList<Integer> ergebnis = new ArrayList<>();
		for(int i = 0; i < bewertungen.size(); ++i){
			for(Bewertung b : bewertungen){
				if(b.getProjekt().getPNode().getId() == i){
					ergebnis.add(i,b.getNote());
				}
			}
		}
		return schliesseLuecken(ergebnis);
	}
	
	public ArrayList<Bewertung> getBewertungen(){
		return bewertungen;
	}
	
	public void setProjekt(Projekt projekt){
		this.projekt = projekt;
	}
	
	//Schließt die Lücken in der übergebenen Liste (1, 5, 5) -> (1, 2, 2)
	public static ArrayList<Integer> schliesseLuecken(ArrayList<Integer> input){
		int vergleich = 1;
		boolean luecke = true;
		int abstand = 1;
		while(vergleich < 6){
			for(int bewertung : input){
				if(vergleich == bewertung){
					luecke = false;
				}
			}
			if(luecke && abstand < 5){
//				System.out.println("luecke bei vergleich " + vergleich);
				for(int i = 0; i < input.size(); ++i){
					if(vergleich + abstand == input.get(i)){
						input.remove(i);
						input.add(i, vergleich);
					}
				}
				++abstand;
			}
			else{
				++vergleich;
				abstand = 1;
				luecke = true;
			}
		}
		return input;
	}
	
	//Liefert die Note, die der Student dem Projekt gegeben hat, dass ihm zugewiesen wurde
	public int getZufriedenheit(){
		if(this.projekt == null){
			throw new NullPointerException("Dem folgenden Studenten wurde kein Projekt zugewiesen:\n" + this);
		}
		return this.bewertungen.get(this.projekt.getId()).getNote();
	}
	
	public int getId(){
		return id;
	}
	
	public String getMatrikelNummer(){
		return matrikelNummer;
	}
	
	public String getStudienfach(){
		return studienfach;
	}
	
	public Projekt getProjekt(){
		return projekt;
	}
}