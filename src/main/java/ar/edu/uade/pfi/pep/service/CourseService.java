package ar.edu.uade.pfi.pep.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.CourseRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
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

	public void createCourse(Course course) {
		Teacher teacher = this.teacherService.getTeacher();
		course.setTeacher(teacher);
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		this.repository.save(course);
	}

	public List<Course> getCoursesForTeacher() {

		Course course = new Course(new User(this.requestDataHolder.getUserId()));
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		Example<Course> example = Example.of(course);
		return this.repository.findAll(example);
	}

	public List<Course> getCoursesForStudent() {
		Course course = new Course();
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		Example<Course> example = Example.of(course);
		return this.repository.findAll(example);
	}

	public Optional<Course> findById(String courseId) {
		return this.repository.findById(courseId);
	}

	public void deleteById(String courseId) throws Exception {
		if (this.inscriptionService.hasInscriptionsWithCourseId(courseId))
			throw new Exception("No se puede eliminar el curso pues hay alumnos inscriptos.");

		this.repository.deleteById(courseId);
	}

	public void updateCourse(String courseId, Course course) {
		Teacher teacher = this.teacherService.getTeacher();
		course.setTeacher(teacher);
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		this.repository.save(course);
	}

	public boolean hasCoursesByProblemId(String problemId) {
		return !this.repository.findByTeacherUserIdAndProblemsId(this.requestDataHolder.getUserId(), problemId)
				.isEmpty();
	}
}
