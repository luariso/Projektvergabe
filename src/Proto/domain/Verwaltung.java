package Proto.domain;

import Proto.dijkstra.Graph;
import Proto.dijkstra.PNode;
import Proto.dijkstra.Projekt;
import Proto.dijkstra.SNode;
import Proto.gui.fx.MainWindow;
import Proto.gui.swing.SwingGUI;
import Proto.persistence.Persistenz;
import Proto.persistence.PersistenzTextDateien;
import java.util.*;

public class Verwaltung {

	private static Collection<Projekt> projekte = new ArrayList<>();
	private static Collection<Student> studenten = new ArrayList<>();
	private static Collection<Verantwortlicher> supervisors = new ArrayList<>();
	
	private static ArrayList<PNode> pNodes;
	private static ArrayList<SNode> sNodes;
	private static Persistenz persistenz = new PersistenzTextDateien();
	private static SwingGUI gui = new SwingGUI();
	
	public static void main(String[] args){
		importiereProjekte();
		importiereStudenten();
//		JavaFXGUI.oeffne(args);
		MainWindow.oeffne(args);
	}
	
	public static void importiereProjekte(){
		projekte = persistenz.importiereProjekte();
	}
	
	public static void importiereStudenten(){
		studenten = persistenz.importiereStudenten();
	}
	
	//Liefert die durchschnittliche Zufriedenheit aller Studenten
	public static double getDurchschnitt(){
		double i = 0;
		for(Student s : studenten){
			i += s.getZufriedenheit();
		}
		return i / studenten.size();
	}
	
	//Liefert das Projekt mit der übergebenen ID
	public static Projekt getProjekt(int key){
		for(Projekt p: projekte){
			if(key == p.getId()){
				return p;
			}
		}
		throw new NullPointerException("Es existiert kein Projekt mit der ID " + key);
	}
	
	public static Student getStudent(int key){
		for(Student s: studenten){
			if(key == s.getId()){
				return s;
			}
		}
		throw new NullPointerException("Es existiert kein Student mit der ID " + key);
	}
	
	//Erstellt eine Liste von PNodes aus den vorhandenen Projekten mit zufälliger Reihenfolge
	public static void annonymisiereProjekte(){
		ArrayList<Projekt> leerend = new ArrayList<>(projekte);
		ArrayList<PNode> ergebnis = new ArrayList<>();
		int zaehler = 0;
		
		while(!leerend.isEmpty()){
			int zufallsZahl = (int)(Math.random()*leerend.size());
			ergebnis.add(zaehler, leerend.get(zufallsZahl).makePNode(zaehler));
			leerend.remove(zufallsZahl);
			++zaehler;
		}
		pNodes = ergebnis;
	}
	
	//Erstellt eine Liste von SNodes aus den vorhandenen Studenten mit zufälliger Reihenfolge
	public static void annonymisiereStudenten(){
		ArrayList<Student> leerend = new ArrayList<>(studenten);
		ArrayList<SNode> ergebnis = new ArrayList<>();
		int zaehler = 0;
		
		while(!leerend.isEmpty()){
			int zufallsZahl = (int)(Math.random()*leerend.size());
			ergebnis.add(zaehler, leerend.get(zufallsZahl).makeSNode(zaehler));
			leerend.remove(zufallsZahl);
			++zaehler;
		}
		sNodes = ergebnis;
	}
	
	//Wendet den Dijkstra-Algorithmus auf die vorhandenen Listen aus PNodes und SNodes an und ordnet den Studenten ihr Projekt zu
	//Liefert eine TreeMap mit der annonymen ID des Studenten als Key und der annonymen ID des Projekts als Value
	public static TreeMap<Integer, Integer> ordne(){
		annonymisiereProjekte();
		annonymisiereStudenten();
		Graph graph = new Graph(pNodes, sNodes);
		TreeMap<Integer, Integer> zuordnung = new TreeMap<>();
		
		int dist=0;
		for (int k=1; dist!=Integer.MAX_VALUE; ++k) {
			// Dijkstra-Algorithmus ausführen, um kürzeste Wege zu berechnen
			dist=graph.Dijkstra(false);
			zuordnung = graph.getMatching();
		}
		
		for(int i = 0; i < zuordnung.size(); ++i){
			sNodes.get(i).getStudent().setProjekt(pNodes.get(zuordnung.get(i)).getProjekt());
			pNodes.get(zuordnung.get(i)).getProjekt().addTeilnehmer(sNodes.get(i).getStudent());
		}
		
		return zuordnung;
	}
	
	//Liefert Daten für die GUI
	public static Object[][] present(String was){
		Object[][] ergebnis;
		switch(was){
			case "studenten":
				ergebnis = new Object[studenten.size()][4];
				for(Student s : studenten){
					int i = s.getId();
					ergebnis[i][0] = s.getId();
					ergebnis[i][1] = s.getVorname();
					ergebnis[i][2] = s.getName();
					ergebnis[i][3] = s.getMatrikelNummer();
				}
				return ergebnis;
			case "matching":
				ergebnis = new Object[studenten.size()][6];
				for(Student s : studenten){
						int i = s.getId();
						ergebnis[i][0] = s.getId();
						ergebnis[i][1] = s.getVorname();
						ergebnis[i][2] = s.getName();
						ergebnis[i][3] = s.getMatrikelNummer();
						if(s.getProjekt() != null){
							ergebnis[i][4] = s.getProjekt().getTitel();
							ergebnis[i][5] = s.getZufriedenheit();
						}
				}
				return ergebnis;
			case "projekte":
				ergebnis = new Object[projekte.size()][3];
				for(Projekt p : projekte){
					int i = p.getId();
					ergebnis[i][0] = p.getId();
					ergebnis[i][1] = p.getTitel();
					ergebnis[i][2] = p.getTeilnehmer().size() + " von " +  p.getMaxTeilnehmer();
				}
				return ergebnis;
		}
		throw new IllegalArgumentException("Gueltige Argumente sind 'studenten', 'matching' und 'projekte'");
	}
	
	public static Collection<Projekt> getProjekte(){
		return projekte;
	}
	
	public static Collection<Student> getStudenten(){
		return studenten;
	}

	public static Collection<Verantwortlicher> getSupervisors(){
		return supervisors;
	}
}