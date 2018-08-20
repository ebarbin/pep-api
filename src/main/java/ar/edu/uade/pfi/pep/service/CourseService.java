package ar.edu.uade.pfi.pep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.CourseRepository;
import ar.edu.uade.pfi.pep.repository.StudentRepository;
import ar.edu.uade.pfi.pep.repository.TeacherRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;

@Component
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private StudentRepository studentRepository;

	public void createCourse(Course course) {
		Teacher teacher = this.teacherRepository.findByUserId(this.requestDataHolder.getUserId());
		course.setTeacher(teacher);
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		this.courseRepository.save(course);
	}

	public List<Course> findAllForTeacher() {
		return this.courseRepository.findByInstituteIdAndTeacherUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public Optional<Course> findById(String courseId) {
		return this.courseRepository.findById(courseId);
	}

	public List<Course> deleteById(String courseId) throws Exception {
		List<Student> students = this.studentRepository.findByInstituteIdAndCoursesId(this.requestDataHolder.getInstituteId(), courseId);
		if (!students.isEmpty())
			throw new Exception("No se pude eliminar el curso pues hay alumnos inscriptos.");

		this.courseRepository.deleteById(courseId);
		return this.findAllForTeacher();
	}

	public void updateCourse(String courseId, Course course) {
		Teacher teacher = this.teacherRepository.findByUserId(this.requestDataHolder.getUserId());
		course.setTeacher(teacher);
		course.setInstituteId(this.requestDataHolder.getInstituteId());
		this.courseRepository.save(course);
	}

	public List<Course> findAllForStudent() {
		return this.courseRepository.findByInstituteId(this.requestDataHolder.getInstituteId());
	}

	public List<Course> findEnrolledCourses() {
		Student student = this.studentRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());

		return student.getCourses();
	}

	public List<Course> enroll(String courseId) {
		Course course = this.courseRepository.findById(courseId).get();
		Student student = this.studentRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
		if (student.getCourses() == null)
			student.setCourses(new ArrayList<Course>());
		student.getCourses().add(course);

		this.studentRepository.save(student);
		return this.findAllForStudent();
	}

	public List<Course> removeEnroll(String courseId) {
		Course course = this.courseRepository.findById(courseId).get();
		Student student = this.studentRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
		student.getCourses().remove(course);

		this.studentRepository.save(student);
		return this.findAllForStudent();
	}
}
