package ar.edu.uade.pfi.pep.repository.document;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import ar.edu.uade.pfi.pep.repository.document.user.User;

@Document(collection = "student")
public class Student {

	private String id;
	private String instituteId;
	private User user;
	private String documentType;
	private String documentNumber;
	private List<Course>courses;
	private Course selectedCourse;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Course getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(Course selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Student))
			return false;
		Student otherStudent = (Student) other;
		return this.getId().equals(otherStudent.getId());
	}
}
