package Proto.persistence;

import Proto.domain.Control;
import Proto.domain.Project;
import Proto.domain.Student;
import Proto.domain.Supervisor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PersistenceTextFiles implements Persistence{

	private final String path = "data/";
	private final String studentsFile = path + "students.txt";
	private final String projectsFile = path + "projects.txt";

	public Collection<Student> importStudents() {
		Collection<Student> result = new ArrayList<>();
		try{
			Scanner fileScanner = new Scanner(new File(studentsFile));
			while(fileScanner.hasNext()){
				Integer id = null;
				String surname = null, name = null, matricleNumber = null;
				
				while(!fileScanner.hasNextInt()){
					fileScanner.next();
				}
				id = fileScanner.nextInt() - 1;
				surname = fileScanner.next();
				name = fileScanner.next();
				matricleNumber = fileScanner.next();
				
				Student currentStudent = new Student(id, surname, name, matricleNumber);
				
				Scanner ratingScanner = new Scanner(fileScanner.nextLine());
				while(ratingScanner.hasNext()){
					Scanner coupleScanner = new Scanner(ratingScanner.next());
					int project = new Integer(coupleScanner.findInLine("[0-9]+")) - 1;
					int grade = new Integer(coupleScanner.findInLine("[0-9]+"));
					currentStudent.addRating(project, grade);
					coupleScanner.close();
				}
				ratingScanner.close();
				result.add(currentStudent);
				
			}
			fileScanner.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}

	public Collection<Project> importProjects() {
		Collection<Project> ergebnis = new ArrayList<>();
		try{
			Scanner dateiScanner = new Scanner(new File(projectsFile));
			while(dateiScanner.hasNext()){
				Integer id, maxMembers;
				String supervisorSurename, supervisorName;
				String title;

				while(!dateiScanner.hasNextInt()) {
					dateiScanner.next();
				}
				id = dateiScanner.nextInt() - 1;
				maxMembers = dateiScanner.nextInt();
				supervisorSurename = dateiScanner.next();
				supervisorName = dateiScanner.next();

				title = dateiScanner.nextLine();
				int i = 0;
				while ((i<title.length()) && (title.charAt(i)==' ')) {
					++i;
				}
				title = title.substring(i,title.length());
				Project project = new Project(id, title, maxMembers);
				ergebnis.add(project);
				for (Supervisor s: Control.getSupervisors()) {
					if (s.getSurname().equals(supervisorSurename) && s.getName().equals(supervisorName)) {
						s.addProject(project);
						break;
					}
				}
			}
			dateiScanner.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return ergebnis;
	}

	@Override
	public Collection<Supervisor> importSupervisors() {
		Collection<Supervisor> result = new ArrayList<>();
		try {
			Scanner dateiScanner = new Scanner(new File(path + "projects.txt"));
			while(dateiScanner.hasNext()){
				String supervisorSurename, supervisorName;

				dateiScanner.next();
				dateiScanner.next();

				supervisorSurename = dateiScanner.next();
				supervisorName = dateiScanner.next();
				dateiScanner.nextLine();

				Supervisor supervisor = new Supervisor(supervisorSurename, supervisorName);
				if (!result.contains(supervisor)) {
					result.add(supervisor);
				}
			}
			dateiScanner.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public void exportMatching(String out) {
		File file = new File(path + "matching.txt");
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(out);
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}