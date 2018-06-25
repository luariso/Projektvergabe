package Proto.domain;

public class Bewertung {

	private Student student;
	private Project project;
	private int note;
	
	public Bewertung(Student student, Project project, int note){
		this.student = student;
		this.project = project;
		this.note = note;
	}
	
	@Override
	public String toString(){
		return "(" + project.getId() + ", " + note + ")";
	}
	
	public int getNote(){
		return note;
	}
	
	public Project getProject(){
		return project;
	}
}