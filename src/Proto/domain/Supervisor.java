package Proto.domain;

import Proto.dijkstra.Project;

import java.util.*;

public class Supervisor extends User{

	private Collection<Project> projects = new ArrayList<>();

	public Supervisor(String surname, String name) {
		super(surname, name);
		System.out.println("Supervisor created: " + surname + " " + name);
	}

	public void addProject(Project p) {
		projects.add(p);
	}
}