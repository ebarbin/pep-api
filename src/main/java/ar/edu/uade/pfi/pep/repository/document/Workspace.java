package ar.edu.uade.pfi.pep.repository.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.edu.uade.pfi.pep.repository.document.user.User;

@Document(collection = "workspace")
public class Workspace {

	@Id
	private String id;
	
	private Student student;
	
	private List<WorkspaceProblem> problems;
	
	private Course course;
	
	private String solution;
	
	private boolean active;

	public Workspace() {}

	public Workspace(Student student, boolean active) {
		this.student = student;
		this.active = active;
	}
	
	public Workspace(User user, Course course, boolean active) {
		Student s = new Student();
		s.setUser(user);
		this.course = course;
		this.student = s;
		this.active = active;
	}
	
	public Workspace(User user, boolean active) {
		Student s = new Student();
		s.setUser(user);
		this.student = s;
		this.active = active;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<WorkspaceProblem> getProblems() {
		return problems;
	}

	public void setProblems(List<WorkspaceProblem> problems) {
		this.problems = problems;
	}
}

