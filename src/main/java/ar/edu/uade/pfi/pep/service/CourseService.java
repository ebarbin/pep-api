package ar.edu.uade.pfi.pep.service;

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
}
