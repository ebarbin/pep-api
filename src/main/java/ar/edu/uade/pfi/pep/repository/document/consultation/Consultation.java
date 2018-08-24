package ar.edu.uade.pfi.pep.repository.document.consultation;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.edu.uade.pfi.pep.repository.document.Student;

@Document(collection = "consultation")
public class Consultation {

	@Id
	private String id;
	private String consultation;
	private boolean wasReaded;
	private TeacherResponse teacherResponse;
	private Student student;
	private Date creationDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConsultation() {
		return consultation;
	}

	public void setConsultation(String consultation) {
		this.consultation = consultation;
	}

	public boolean isWasReaded() {
		return wasReaded;
	}

	public void setWasReaded(boolean wasReaded) {
		this.wasReaded = wasReaded;
	}

	public TeacherResponse getTeacherResponse() {
		return teacherResponse;
	}

	public void setTeacherResponse(TeacherResponse teacherResponse) {
		this.teacherResponse = teacherResponse;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
