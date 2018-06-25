package Proto.persistence;

import Proto.domain.Project;
import Proto.domain.Student;
import Proto.domain.Supervisor;

import java.util.*;

public interface Persistence{
	
	Collection<Student> importStudents();
	
	Collection<Project> importProjects();

	Collection<Supervisor> importSupervisors();

	void exportiere();
}
