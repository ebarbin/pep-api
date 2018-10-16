package ar.edu.uade.pfi.pep.repository.custom;

import java.util.List;

import ar.edu.uade.pfi.pep.repository.document.Course;

public interface CourseRepositoryCustom {

	List<Course> findForTeacherByNameLike(String nameSearch);

	List<Course> findForStudentByNameLike(String nameSearch);
}
