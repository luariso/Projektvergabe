package Proto.persistence;

import Proto.domain.Project;
import Proto.domain.Student;
import Proto.domain.Supervisor;

import java.util.*;
import java.util.stream.Collectors;

public interface Persistenz{
	
	public Collection<Student> importStudents();
	
	public Collection<Project> importProjects();

	public Collection<Supervisor> importSupervisors();
	
	public void exportiere();
}
