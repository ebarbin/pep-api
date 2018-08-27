package ar.edu.uade.pfi.pep.repository.document.consultation;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;

@Document(collection = "consultation")
public class Consultation {

	@Id
	private String id;
	private String consultation;
	private Student student;
	private Course course;
	private Teacher teacher;
	private Problem problem;
	private Boolean wasReaded;
	private TeacherResponse teacherResponse;
	private Date creationDate;
	 
	public Consultation() {}
	
	public Consultation(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Consultation(Student student) {
		this.student = student;
	}
	
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

	public Boolean getWasReaded() {
		return wasReaded;
	}

	public void setWasReaded(Boolean wasReaded) {
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
