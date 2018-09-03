package ar.edu.uade.pfi.pep.repository.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "consultation")
public class Consultation {

	@Id
	private String id;
	private String consultation;
	private Student student;
	private Boolean wasReadedByStudent;
	private Date creationDate;
	private String code;
	
	private Course course;
	private Problem problem;
	
	private Teacher teacher;
	private Boolean wasReadedByTeacher;
	private String teacherResponse;
	
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

	public Boolean getWasReadedByTeacher() {
		return wasReadedByTeacher;
	}

	public void setWasReadedByTeacher(Boolean wasReadedByTeacher) {
		this.wasReadedByTeacher = wasReadedByTeacher;
	}

	public String getTeacherResponse() {
		return teacherResponse;
	}

	public void setTeacherResponse(String teacherResponse) {
		this.teacherResponse = teacherResponse;
	}

	public Boolean getWasReadedByStudent() {
		return wasReadedByStudent;
	}

	public void setWasReadedByStudent(Boolean wasReadedByStudent) {
		this.wasReadedByStudent = wasReadedByStudent;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
