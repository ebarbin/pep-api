package ar.edu.uade.pfi.pep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.edu.uade.pfi.pep.repository.CourseRepository;
import ar.edu.uade.pfi.pep.repository.document.Course;

@Component
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;
	
	public void createCourse(Course course) {
		this.courseRepository.save(course);
	}
}
