package ar.edu.uade.pfi.pep.repository.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "primitive")
public class Primitive {

	@Id
	private String id;
	private String name;
	private String description;
	private String code;
	private Teacher teacher;

	public Primitive() {

	}

	public Primitive(String id) {
		this.id = id;
	}

	public Primitive(Teacher teacher) {
		this.teacher = teacher;
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
		if (!(other instanceof Primitive))
			return false;
		Primitive otherPrimitive = (Primitive) other;
		return this.getId().equals(otherPrimitive.getId());
	}
}
