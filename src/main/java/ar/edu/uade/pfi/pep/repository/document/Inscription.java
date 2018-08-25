package ar.edu.uade.pfi.pep.repository.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inscription")
public class Inscription {

	@Id
	private String id;
	
	private Student student;
	
	private Course course;
	
	private Date inscriptionDate;

	public Inscription() {}
	
	public Inscription(Student student) {
		this.student = student;
	}
	
	public Inscription(Student student, Course course) {
		this.student = student;
		this.course = course;
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

	public Date getInscriptionDate() {
		return inscriptionDate;
	}

	public void setInscriptionDate(Date inscriptionDate) {
		this.inscriptionDate = inscriptionDate;
	}
}
