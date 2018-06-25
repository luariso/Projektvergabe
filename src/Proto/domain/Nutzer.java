package Proto.domain;

public abstract class Nutzer {

	protected String surename;
	protected String name;

	public Nutzer(String surename, String name) {
		this.surename = surename;
		this.name = name;
	}

	public String getSurename() {
		return surename;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return surename + " " + name;
	}
}