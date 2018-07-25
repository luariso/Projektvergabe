package Proto.domain;

import Proto.dijkstra.Graph;
import Proto.dijkstra.PNode;
import Proto.dijkstra.SNode;
import Proto.gui.fx.MainWindow;
import Proto.persistence.Persistence;
import Proto.persistence.PersistenceTextFiles;

import java.util.*;

public class Control{

	private static Collection<Project> projects = new ArrayList<>();
	private static Collection<Student> students = new ArrayList<>();
	private static Collection<Supervisor> supervisors = new ArrayList<>();
	private static Collection<Admin> admins = new ArrayList<>();
	
	private static ArrayList<PNode> pNodes;
	private static ArrayList<SNode> sNodes;
	private static Persistence persistence = new PersistenceTextFiles();
	
	public static void main(String[] args){
		importSupervisors();
		importProjects();
		importStudents();
		admins.add(new Admin("Adminsen", "Admin"));
		MainWindow.open(args);
	}

	private static void importSupervisors() {
		supervisors = persistence.importSupervisors();
	}

	public static void importProjects(){
		projects = persistence.importProjects();
	}

	public static void importStudents(){
		students = persistence.importStudents();
	}
	
	public static void exportMatching(){
		StringBuilder out = new StringBuilder();
		for(Project p : projects){
			for(Student s : p.getMembers()){
				out.append(s.getId()).append("\t").append(s.getSurname()).append("\t").append(s.getName()).append("\t").append(s.getMatriculation());
				out.append("\t").append(p.getId()).append("\t").append(p.getTitle()).append("\n");
			}
		}
		persistence.exportMatching(out.toString());
	}
	
	//Liefert die durchschnittliche Zufriedenheit aller Studenten
	public static double getAverage(){
		double i = 0;
		for(Student s : students){
			i += s.getSatisfaction();
		}
		return i / students.size();
	}
	
	//Liefert das Project mit der übergebenen ID
	public static Project getProject(int key){
		for(Project p: projects){
			if(key == p.getId()){
				return p;
			}
		}
		throw new NullPointerException("Es existiert kein Project mit der ID " + key);
	}
	
	public static Student getStudent(int key){
		for(Student s: students){
			if(key == s.getId()){
				return s;
			}
		}
		throw new NullPointerException("Es existiert kein Student mit der ID " + key);
	}
	
	//Erstellt eine Liste von PNodes aus den vorhandenen Projekten mit zufälliger Reihenfolge
	public static void annonymizeProjects(){
		ArrayList<Project> emptying = new ArrayList<>(projects);
		ArrayList<PNode> result = new ArrayList<>();
		int counter = 0;
		
		while(!emptying.isEmpty()){
			int randomInt = (int)(Math.random()*emptying.size());
			result.add(counter, emptying.get(randomInt).makePNode(counter));
			emptying.remove(randomInt);
			++counter;
		}
		pNodes = result;
	}
	
	//Erstellt eine Liste von SNodes aus den vorhandenen Studenten mit zufälliger Reihenfolge
	public static void annonymizeStudents(){
		ArrayList<Student> emptying = new ArrayList<>(students);
		ArrayList<SNode> result = new ArrayList<>();
		int counter = 0;
		
		while(!emptying.isEmpty()){
			int randomInt = (int)(Math.random()*emptying.size());
			result.add(counter, emptying.get(randomInt).makeSNode(counter));
			emptying.remove(randomInt);
			++counter;
		}
		sNodes = result;
	}
	
	//Wendet den Dijkstra-Algorithmus auf die vorhandenen Listen aus PNodes und SNodes an und ordnet den Studenten ihr Project zu
	//Liefert eine TreeMap mit der annonymen ID des Studenten als Key und der annonymen ID des Projekts als Value
	public static TreeMap<Integer, Integer> match(){
		annonymizeProjects();
		annonymizeStudents();
		Graph graph = new Graph(pNodes, sNodes);
		TreeMap<Integer, Integer> matching = new TreeMap<>();
		
		int dist=0;
		for (int k=1; dist!=Integer.MAX_VALUE; ++k) {
			// Dijkstra-Algorithmus ausführen, um kürzeste Wege zu berechnen
			dist=graph.Dijkstra(false);
			matching = graph.getMatching();
		}
		
		for(int i = 0; i < matching.size(); ++i){
			sNodes.get(i).getStudent().setProject(pNodes.get(matching.get(i)).getProject());
			pNodes.get(matching.get(i)).getProject().addMember(sNodes.get(i).getStudent());
		}
		
		return matching;
	}
	
	//Liefert Daten für die gui
	public static Object[][] present(String what){
		Object[][] result;
		switch(what){
			case "students":
				result = new Object[students.size()][4];
				for(Student s : students){
					int i = s.getId();
					result[i][0] = s.getId();
					result[i][1] = s.getName();
					result[i][2] = s.getSurname();
					result[i][3] = s.getMatriculation();
				}
				return result;
			case "matching":
				result = new Object[students.size()][6];
				for(Student s : students){
						int i = s.getId();
						result[i][0] = s.getId();
						result[i][1] = s.getName();
						result[i][2] = s.getSurname();
						result[i][3] = s.getMatriculation();
						if(s.getProject() != null){
							result[i][4] = s.getProject().getTitle();
							result[i][5] = s.getSatisfaction();
						}
				}
				return result;
			case "projects":
				result = new Object[projects.size()][3];
				for(Project p : projects){
					int i = p.getId();
					result[i][0] = p.getId();
					result[i][1] = p.getTitle();
					result[i][2] = p.getMembers().size() + " von " +  p.getMaxMembers();
				}
				return result;
		}
		throw new IllegalArgumentException("Gueltige Argumente sind 'students', 'matching' und 'projects'");
	}
	
	public static Collection<Project> getProjects(){
		return projects;
	}
	
	public static Collection<Student> getStudents(){
		return students;
	}

	public static Collection<Supervisor> getSupervisors(){
		return supervisors;
	}
	
	public static Collection<Admin> getAdmins(){
		return admins;
	}

	public static void resetMatching() {
		for (Student s: students) {
			s.setProject(null);
		}
		for (Project p: projects) {
			p.resetMembers();
		}
	}
}