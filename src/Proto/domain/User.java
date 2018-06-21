package Proto.domain;

public abstract class User{

	protected String surname;
	protected String name;

	public User(String surname, String name) {
		this.surname = surname;
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}
	
	
}