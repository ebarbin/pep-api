package ar.edu.uade.pfi.pep.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.CourseRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;

@Component
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private RequestDataHolder requestDataHolder;
	
	public void createCourse(Course course) {
		course.setUserId(this.requestDataHolder.getUserId());
		course.setInstituteId(this.requestDataHolder.getInstituteId());

		this.courseRepository.save(course);
	}

	public List<Course> findAllForTeacher() {
		return this.courseRepository.findByInstituteIdAndUserId(this.requestDataHolder.getInstituteId(),
				this.requestDataHolder.getUserId());
	}

	public Optional<Course> findById(String courseId) {
		return this.courseRepository.findById(courseId);
	}

	public List<Course> deleteById(String courseId) {
		this.courseRepository.deleteById(courseId);
		return this.findAllForTeacher();
	}
}
