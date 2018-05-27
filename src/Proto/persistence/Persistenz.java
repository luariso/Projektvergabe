package Proto.persistence;

import Proto.dijkstra.Projekt;
import Proto.domain.Student;

import java.util.*;

public interface Persistenz{
	
	public Collection<Student> importiereStudenten();
	
	public Collection<Projekt> importiereProjekte();
	
	public void exportiere();
}
