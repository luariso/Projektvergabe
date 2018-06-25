package Proto.domain;

public class Admin extends User{
	
	public Admin(String surname, String name){
		super(surname, name);
		System.out.println("Admin created: " + surname + " " + name);
	}
	
}
