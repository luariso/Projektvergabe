package Proto.domain;

import Proto.dijkstra.Projekt;

import java.util.*;

public class Verantwortlicher extends Nutzer {

	private Collection<Projekt> projekte;
	
	public Verantwortlicher(String name, String vorname, Collection<Projekt> projekte){
		this.projekte = projekte;
	}
}