package Proto.domain;

import Proto.dijkstra.Project;

public class Rating{

	private Student student;
	private Project project;
	private int grade;
	
	public Rating(Student student, Project project, int grade){
		this.student = student;
		this.project = project;
		this.grade = grade;
	}
	
	@Override
	public String toString(){
		return "(" + project.getId() + ", " + grade + ")";
	}
	
	public int getGrade(){
		return grade;
	}
	
	public Project getProject(){
		return project;
	}
}