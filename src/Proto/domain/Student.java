package Proto.domain;

import Proto.dijkstra.Project;
import Proto.dijkstra.SNode;

import java.util.*;

public class Student extends User{
	
	private int id;
	private ArrayList<Rating> ratings = new ArrayList<>();
	private Project project;
	private String major;
	private String matriculation;
	private SNode sNode;
	
	public Student(int id, String surename, String name, String matriculation){
		super(surename, name);
		this.id = id;
		this.matriculation = matriculation;
	}
	
	public void addRating(int project, int grade){
		this.ratings.add(new Rating(this, Control.getProject(project), grade));
	}
	
	@Override
	public String toString(){
		String ergebnis = this.name + " " + this.surname;
//		if(this.sNode != null){
//			ergebnis += " " + this.sNode.getId();
//		}
//		if(this.project != null){
//			ergebnis += " " + this.project.getTitle() +
//					" " + this.getSatisfaction();
//
//		}
//		ergebnis += '\n';
		return ergebnis;
	}
	
	//Erstellt eine SNode aus dem Student und "merkt" sich diese als Attribut, da die IDs unterschiedlich sind
	public SNode makeSNode(int id){
		this.sNode = new SNode(id, getRatingGrades());
		this.sNode.setStudent(this);
		return this.sNode;
	}
	
	//Erstellt eine nach den IDs der PNodes geordnete Liste von Noten aus den vorhandenen Bewertungen
	public ArrayList<Integer> getRatingGrades(){
		ArrayList<Integer> result = new ArrayList<>();
		for(int i = 0; i < ratings.size(); ++i){
			for(Rating b : ratings){
				if(b.getProject().getPNode().getId() == i){
					result.add(i,b.getGrade());
				}
			}
		}
		return closeGaps(result);
	}
	
	public ArrayList<Rating> getRatings(){
		return ratings;
	}
	
	public void setProject(Project project){
		this.project = project;
	}
	
	//Schließt die Lücken in der übergebenen Liste (1, 5, 5) -> (1, 2, 2)
	public static ArrayList<Integer> closeGaps(ArrayList<Integer> input){
		int relation = 1;
		boolean gap = true;
		int distance = 1;
		while(relation < 6){
			for(int bewertung : input){
				if(relation == bewertung){
					gap = false;
				}
			}
			if(gap && distance < 5){
//				System.out.println("gap bei relation " + relation);
				for(int i = 0; i < input.size(); ++i){
					if(relation + distance == input.get(i)){
						input.remove(i);
						input.add(i, relation);
					}
				}
				++distance;
			}
			else{
				++relation;
				distance = 1;
				gap = true;
			}
		}
		return input;
	}
	
	//Liefert die Note, die der Student dem Project gegeben hat, dass ihm zugewiesen wurde
	public int getSatisfaction(){
		if(this.project == null){
			throw new NullPointerException("Dem folgenden Studenten wurde kein Project zugewiesen:\n" + this);
		}
		return this.ratings.get(this.project.getId()).getGrade();
	}
	
	public int getId(){
		return id;
	}
	
	public String getMatriculation(){
		return matriculation;
	}
	
	public String getMajor(){
		return major;
	}
	
	public Project getProject(){
		return project;
	}
}