package Proto.domain;

import Proto.dijkstra.Projekt;

public class Bewertung {

	private Student student;
	private Projekt projekt;
	private int note;
	
	public Bewertung(Student student, Projekt projekt, int note){
		this.student = student;
		this.projekt = projekt;
		this.note = note;
	}
	
	@Override
	public String toString(){
		return "(" + projekt.getId() + ", " + note + ")";
	}
	
	public int getNote(){
		return note;
	}
	
	public Projekt getProjekt(){
		return projekt;
	}
}