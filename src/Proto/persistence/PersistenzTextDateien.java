package Proto.persistence;

import Proto.dijkstra.Projekt;
import Proto.domain.Student;
import Proto.domain.Supervisor;

import java.io.File;
import java.util.*;

public class PersistenzTextDateien implements Persistenz{

	private String pfad = "data/";

	public Collection<Student> importiereStudenten() {
		Collection<Student> ergebnis = new ArrayList<>();
		try{
			Scanner dateiScanner = new Scanner(new File(pfad + "students.txt"));
			while(dateiScanner.hasNext()){
				Integer id = null;
				String name = null, vorname = null, matrikelNummer = null;
				
				while(!dateiScanner.hasNextInt()){
					dateiScanner.next();
				}
				id = dateiScanner.nextInt() - 1;
				name = dateiScanner.next();
				vorname = dateiScanner.next();
				matrikelNummer = dateiScanner.next();
				
				Student aktuellerStudent = new Student(id, name, vorname, matrikelNummer);
				
				Scanner bewertungsScanner = new Scanner(dateiScanner.nextLine());
				while(bewertungsScanner.hasNext()){
					Scanner paarScanner = new Scanner(bewertungsScanner.next());
					int projekt = new Integer(paarScanner.findInLine("[0-9]+")) - 1;
					int note = new Integer(paarScanner.findInLine("[0-9]+"));
					aktuellerStudent.addBewertungen(projekt, note);
					paarScanner.close();
				}
				bewertungsScanner.close();
				ergebnis.add(aktuellerStudent);
				
			}
			dateiScanner.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return ergebnis;
	}
	
	public Collection<Projekt> importiereProjekte(){
		Collection<Projekt> ergebnis = new ArrayList<>();
		try{
			Scanner dateiScanner = new Scanner(new File(pfad + "projects.txt"));
			while(dateiScanner.hasNext()){
				Integer id, maxTeilnehmer;
				String supervisorSurename, supervisorName;
				String titel;
				
				while(!dateiScanner.hasNextInt()){
					dateiScanner.next();
				}
				id = dateiScanner.nextInt() - 1;
				maxTeilnehmer = dateiScanner.nextInt();
				supervisorSurename = dateiScanner.next();
				supervisorName = dateiScanner.next();

				titel = dateiScanner.nextLine();
				int i = 0;
				while ((i<titel.length()) && (titel.charAt(i)==' ')){
					++i;
				}
				titel = titel.substring(i,titel.length());
				Projekt project = new Projekt(id, titel, maxTeilnehmer);
				ergebnis.add(project);
				Supervisor supervisor = new Supervisor(supervisorSurename, supervisorName);
				supervisor.addProject(project);
			}
			dateiScanner.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return ergebnis;
	}
	
	public void exportiere() {
		// TODO - implement PersistenzTextDateien.exportiere
		throw new UnsupportedOperationException();
	}

}