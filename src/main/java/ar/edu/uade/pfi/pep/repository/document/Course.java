package ar.edu.uade.pfi.pep.repository.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.edu.uade.pfi.pep.repository.document.user.User;

@Document(collection = "course")
public class Course {

	@Id
	private String id;
	private String instituteId;
	private String name;
	private String description;
	private String code;
	private List<Problem> problems;
	private Teacher teacher;
	
	public Course() {}
	
	public Course(Teacher t) {
		this.teacher = t;
	}
	
	public Course(User u) {
		this.teacher = new Teacher(u);
	}
	
	public Course(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}

	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Course))
			return false;
		Course otherCourse = (Course) other;
		return this.getId().equals(otherCourse.getId());
	}
}
