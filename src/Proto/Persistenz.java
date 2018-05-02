package Proto;

import java.util.*;

public interface Persistenz{
	
	public Collection<Student> importiereStudenten();
	
	public Collection<Projekt> importiereProjekte();
	
	public void exportiere();
}
