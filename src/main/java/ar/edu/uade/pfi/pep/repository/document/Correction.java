package ar.edu.uade.pfi.pep.repository.document;

public class Correction {

	private String id;
	private String workspaceId;
	private Student student;
	private Teacher teacher;
	private Course course;
	private WorkspaceProblem workspaceProblem;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

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
}
