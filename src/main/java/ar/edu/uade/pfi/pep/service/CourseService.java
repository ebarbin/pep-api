package ar.edu.uade.pfi.pep.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.CourseRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class CourseService {

	@Autowired
	private CourseRepository repository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private InscriptionService inscriptionService;

	@Autowired
	private WorkspaceService workspaceService;

	public void createCourse(Course course) throws Exception {

		if (!this.existCourseWithSameName(course)) {
			Teacher teacher = this.teacherService.getTeacher();
			course.setTeacher(teacher);
			this.repository.save(course);
		} else {
			throw new Exception("Ya existe un curso con ese nombre.");
		}
	}

	public List<Course> getCoursesForTeacher() {

		return this.repository.findByTeacherUserId(this.requestDataHolder.getUserId());
	}

	public List<Course> getCoursesForStudent() {
		return this.repository.findByTeacherInstituteId(this.requestDataHolder.getInstituteId());
	}

	public Optional<Course> findById(String courseId) {
		return this.repository.findById(courseId);
	}

	public void deleteById(String courseId) throws Exception {
		if (this.inscriptionService.hasInscriptionsWithCourseId(courseId))
			throw new Exception("No se puede eliminar el curso pues hay alumnos inscriptos.");

		this.repository.deleteById(courseId);
	}

	public void updateCourse(String courseId, Course course) throws Exception {

		if (!this.existCourseWithSameName(course)) {
			Teacher teacher = this.teacherService.getTeacher();
			course.setTeacher(teacher);
			course = this.repository.save(course);

			this.inscriptionService.updateInscriptionsByCourse(course);
			this.workspaceService.updateWorkspacesByCourse(course);
		} else {
			throw new Exception("Ya existe un curso con ese nombre.");
		}
	}

	public boolean hasCoursesByProblemId(String problemId) {
		return !this.repository.findByProblemsId(problemId).isEmpty();
	}

	public void updateCoursesByProblem(Problem updatedProblem) {
		List<Course> courses = this.repository.findByProblemsId(updatedProblem.getId());
		for (Course c : courses) {
			for (Problem p : c.getProblems()) {
				if (p.equals(updatedProblem)) {
					p.setExplanation(updatedProblem.getExplanation());
					p.setName(updatedProblem.getName());
					p.setPosExecution(updatedProblem.getPosExecution());
					p.setPreExecution(updatedProblem.getPreExecution());
					p.setPrimitives(updatedProblem.getPrimitives());

					this.repository.save(c);
				}
			}
		}

		this.workspaceService.updateWorkspacesByProblem(updatedProblem);
		this.inscriptionService.updateInscriptionsByProblem(updatedProblem);
	}

	private boolean existCourseWithSameName(Course c) {

		Course exampleCourse = new Course(new Teacher(new User(this.requestDataHolder.getUserId())));
		exampleCourse.setName(c.getName());
		Example<Course> example = Example.of(exampleCourse);
		Optional<Course> optional = this.repository.findOne(example);

		if (c.getId() == null) {
			return optional.isPresent();
		} else {
			if (optional.isPresent()) {
				Course course = optional.get();
				if (!course.getId().equals(c.getId())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}
}
