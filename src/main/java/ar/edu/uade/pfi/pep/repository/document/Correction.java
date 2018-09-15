package ar.edu.uade.pfi.pep.repository.document;

public class Correction {

	private Student student;
	private Teacher teacher;
	private Course course;
	private WorkspaceProblem workspaceProblem;
	private boolean wasReadedByTeacher;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public WorkspaceProblem getWorkspaceProblem() {
		return workspaceProblem;
	}

	public void setWorkspaceProblem(WorkspaceProblem workspaceProblem) {
		this.workspaceProblem = workspaceProblem;
	}

	public boolean isWasReadedByTeacher() {
		return wasReadedByTeacher;
	}

	public void setWasReadedByTeacher(boolean wasReadedByTeacher) {
		this.wasReadedByTeacher = wasReadedByTeacher;
	}
}
