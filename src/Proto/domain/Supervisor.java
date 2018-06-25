package Proto.domain;

import java.util.*;

public class Supervisor extends Nutzer {

	private Collection<Project> projects = new ArrayList<>();

	public Supervisor(String surename, String name) {
		super(surename, name);
	}

	public void addProject(Project p) {
		projects.add(p);
	}

	public Collection<Project> getProjects() {
		return projects;
	}
}