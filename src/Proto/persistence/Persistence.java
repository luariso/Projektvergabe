package Proto.persistence;

import Proto.dijkstra.Project;
import Proto.domain.Student;

import java.util.*;

public interface Persistence{
	
	Collection<Student> importStudents();
	
	Collection<Project> importProjects();
	
	void exportiere();
}
