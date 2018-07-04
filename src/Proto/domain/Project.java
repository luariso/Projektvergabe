package Proto.domain;

import Proto.dijkstra.PNode;

import java.util.*;

public class Project {

	private int id;
	private String title;
	private int maxMembers;
	private ArrayList<Student> members = new ArrayList<>();
	private Collection<Supervisor> supervisors;
	private PNode pNode;
	
	public Project(int id, String title, int maxMembers){
		this.id = id;
		this.title = title;
		this.maxMembers = maxMembers;
	}
	
	@Override
	public String toString(){
//		return this.id +
//				" " + title + " " +
//				" " + maxMembers +
//				"\n";
		return title;
	}
	
	//Erstellt eine PNode aus dem Project und "merkt" sich diese als Attribut, da die IDs unterschiedlich sind
	
	public PNode makePNode(int id){
		this.pNode = new PNode(id, this.maxMembers - this.members.size());
		this.pNode.setProject(this);
		return this.pNode;
	}
	
	public int getId(){
		return id;
	}
	
	public PNode getPNode(){
		return pNode;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setMaxMembers(int maxMembers){
		this.maxMembers = maxMembers;
	}
	
	public int getMaxMembers(){
		return maxMembers;
	}
	
	public void addMember(Student s){
		if(members.contains(s)){
			throw new IllegalArgumentException("Der Student " + s + " ist bereits Teilnehmer dieses Projekts.");
		}
		members.add(s);
	}
	
	public ArrayList<Student> getMembers(){
		return members;
	}

	public void resetMembers() {
		members = new ArrayList<>();
	}
}