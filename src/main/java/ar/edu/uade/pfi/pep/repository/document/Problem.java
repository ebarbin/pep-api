package ar.edu.uade.pfi.pep.repository.document;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problem")
public class Problem {

	@Id
	private String id;
	private String name;
	private String explanation;
	private Teacher teacher;
	private String teacherSolucion;
	private String preExecution;
	private String posExecution;
	private List<Primitive> primitives;

	public Problem() {
	}

	public Problem(Teacher t) {
		this.teacher = t;
	}

	public Problem(String id) {
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

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public List<Primitive> getPrimitives() {
		return primitives;
	}

	public void setPrimitives(List<Primitive> primitives) {
		this.primitives = primitives;
	}

	public String getPreExecution() {
		return preExecution;
	}

	public void setPreExecution(String preExecution) {
		this.preExecution = preExecution;
	}

	public String getPosExecution() {
		return posExecution;
	}

	public void setPosExecution(String posExecution) {
		this.posExecution = posExecution;
	}
	
	public String getTeacherSolucion() {
		return teacherSolucion;
	}

	public void setTeacherSolucion(String teacherSolucion) {
		this.teacherSolucion = teacherSolucion;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Problem))
			return false;
		Problem otherProblem = (Problem) other;
		return this.getId().equals(otherProblem.getId());
	}
}
