package ar.edu.uade.pfi.pep.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.CourseRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;

@Component
public class CourseService {

	@Autowired
	private CourseRepository repository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private StudentService studentService;

	public void createCourse(Course course) {
		Teacher teacher = this.teacherService.getTeacher();
		course.setTeacher(teacher);
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		this.repository.save(course);
	}

	public List<Course> getCoursesForTeacher() {
		return this.repository.findByInstituteIdAndTeacherUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public List<Course> getCoursesForStudent() {
		return this.repository.findByInstituteId(this.requestDataHolder.getInstituteId());
	}

	public Optional<Course> findById(String courseId) {
		return this.repository.findById(courseId);
	}

	public List<Course> deleteById(String courseId) throws Exception {
		List<Student> students = this.studentService.getStudentsByCourseId(courseId);
		if (!students.isEmpty())
			throw new Exception("No se pude eliminar el curso pues hay alumnos inscriptos.");

		this.repository.deleteById(courseId);
		return this.getCoursesForTeacher();
	}

	public void updateCourse(String courseId, Course course) {
		Teacher teacher = this.teacherService.getTeacher();
		course.setTeacher(teacher);
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		this.repository.save(course);
	}

	public List<Course> getCoursesByProblemId(String problemId) {
		return this.repository.findByInstituteIdAndProblemsId(this.requestDataHolder.getInstituteId(), problemId);
	}
}
