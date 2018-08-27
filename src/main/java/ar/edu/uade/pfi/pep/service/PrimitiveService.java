package ar.edu.uade.pfi.pep.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.PrimitiveRepository;
import ar.edu.uade.pfi.pep.repository.custom.PrimitiveRepositoryImpl;
import ar.edu.uade.pfi.pep.repository.document.Primitive;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class PrimitiveService {

	@Autowired
	private PrimitiveRepository repository;
	
	@Autowired
	private PrimitiveRepositoryImpl customRepository;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private RequestDataHolder requestDataHolder;

	public List<Primitive> getPrimitives() {
		Primitive primitive = new Primitive(new Teacher(new User(this.requestDataHolder.getUserId())));
		Example<Primitive> example = Example.of(primitive);
		return this.repository.findAll(example);
	}

	public Optional<Primitive> findById(String primitiveId) {
		return this.repository.findById(primitiveId);
	}

	public void createPrimitive(Primitive primitive) {
		Teacher teacher = this.teacherService.getTeacher();
		primitive.setTeacher(teacher);
		this.repository.save(primitive);
	}
	
	public void updateCourse(String primitiveId, Primitive primitive) {
		Teacher teacher = this.teacherService.getTeacher();
		primitive.setTeacher(teacher);
		this.repository.save(primitive);
	}

	public void deleteById(String primitiveId) {
		//TODO validar si la primitiva esta en algun ejercicio...
		this.repository.deleteById(primitiveId);
	}

	public void updatePrimitive(String primitiveId, Primitive primitive) {
		this.repository.findById(primitiveId);
		Teacher teacher = this.teacherService.getTeacher();
		primitive.setTeacher(teacher);
		this.repository.save(primitive);
		
	}

	public List<Primitive> findByNameLike(String nameSearch) {
		return this.customRepository.findByNameLike(nameSearch);
	}

}
