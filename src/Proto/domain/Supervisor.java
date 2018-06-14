package Proto.domain;

import Proto.dijkstra.Projekt;

import java.util.*;

public class Supervisor extends Nutzer {

	private Collection<Projekt> projects = new ArrayList<>();

	public Supervisor(String surename, String name) {
		super(surename, name);
		System.out.println("Supervisor created: " + surename + " " + name);
	}

	public void addProject(Projekt p) {
		projects.add(p);
	}
}