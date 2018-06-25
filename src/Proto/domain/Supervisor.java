package Proto.domain;

import java.util.*;

public class Supervisor extends User {

	private Collection<Project> projects = new ArrayList<>();

	public Supervisor(String surname, String name) {
		super(surname, name);
	}

	public void addProject(Project p) {
		projects.add(p);
	}

	public Collection<Project> getProjects() {
		return projects;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(Supervisor.class)) {
			return name.equals(((Supervisor) obj).getName()) && ((Supervisor) obj).getSurname().equals(surname);
		}
		else {
			return false;
		}
	}
}