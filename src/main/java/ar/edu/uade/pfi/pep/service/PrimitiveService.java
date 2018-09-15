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
	private ProblemService problemService;

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

	public void createPrimitive(Primitive primitive) throws Exception {
		if (!this.existPrimitiveWithSameName(primitive)) {
			Teacher teacher = this.teacherService.getTeacher();
			primitive.setTeacher(teacher);
			this.repository.save(primitive);
		} else {
			throw new Exception("Ya existe una primitiva con ese nombre.");
		}
	}

	private boolean existPrimitiveWithSameName(Primitive p) {

		Primitive examplePrimitive = new Primitive(new Teacher(new User(this.requestDataHolder.getUserId())));
		examplePrimitive.setName(p.getName());
		Example<Primitive> example = Example.of(examplePrimitive);
		Optional<Primitive>optional = this.repository.findOne(example);
		
		if (p.getId() == null) {
			return optional.isPresent();
		} else {
			if (optional.isPresent()) {
				Primitive primitive = optional.get();
				if (!primitive.getId().equals(p.getId())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		
	}

	public void deleteById(String primitiveId) throws Exception {

		if (this.problemService.hasProblemsWithPrimitiveId(primitiveId))
			throw new Exception("No se puede eliminar la primitiva pues hay ejercicios que la usan.");

		this.repository.deleteById(primitiveId);
	}

	public void updatePrimitive(String primitiveId, Primitive primitive) throws Exception {

		if (!this.existPrimitiveWithSameName(primitive)) {
			this.repository.findById(primitiveId);
			Teacher teacher = this.teacherService.getTeacher();
			primitive.setTeacher(teacher);
			this.repository.save(primitive);

			this.problemService.updateProblemsByPrimitive(primitive);
		} else {
			throw new Exception("Ya existe una primitiva con ese nombre.");
		}
	}

	public List<Primitive> findByNameLike(String nameSearch) {
		return this.customRepository.findByNameLike(nameSearch);
	}
}
