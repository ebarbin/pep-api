package ar.edu.uade.pfi.pep.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import ar.edu.uade.pfi.pep.repository.document.Workspace;

public interface WorkspaceRepository extends MongoRepository<Workspace, String>, QueryByExampleExecutor<Workspace> {

	void deleteByStudentIdAndCourseId(String studentId, String courseId);

}
