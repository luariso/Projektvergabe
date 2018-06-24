package Proto.persistence;

import Proto.domain.Project;
import Proto.domain.Student;
import Proto.domain.Supervisor;

import java.io.File;
import java.util.*;

public class PersistenceTextFiles implements Persistence{

	private String path = "data/";

	public Collection<Student> importStudents() {
		Collection<Student> result = new ArrayList<>();
		try{
			Scanner fileScanner = new Scanner(new File(path + "students.txt"));
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
	
	public Collection<Project> importProjects(){
		Collection<Project> result = new ArrayList<>();
		try{
			Scanner fileScanner = new Scanner(new File(path + "projects.txt"));
			while(fileScanner.hasNext()){
				Integer id, maxMembers;
				String supervisorSurname, supervisorName;
				String title;
				
				while(!fileScanner.hasNextInt()){
					fileScanner.next();
				}
				id = fileScanner.nextInt() - 1;
				maxMembers = fileScanner.nextInt();
				supervisorSurname = fileScanner.next();
				supervisorName = fileScanner.next();

				title = fileScanner.nextLine();
				int i = 0;
				while ((i<title.length()) && (title.charAt(i)==' ')){
					++i;
				}
				title = title.substring(i,title.length());
				Project project = new Project(id, title, maxMembers);
				result.add(project);
				Supervisor supervisor = new Supervisor(supervisorSurname, supervisorName);
				supervisor.addProject(project);
			}
			fileScanner.close();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public void exportiere() {
		// TODO - implement PersistenceTextFiles.exportiere
		throw new UnsupportedOperationException();
	}

}