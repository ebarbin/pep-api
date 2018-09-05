package ar.edu.uade.pfi.pep.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Inscription;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
import ar.edu.uade.pfi.pep.repository.document.WorkspaceProblem;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Component
public class ChartService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private InscriptionService inscriptionService;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private CourseService courseService;

	public List<DBObject> getStudentsForCourse() {
		Teacher teacher = new Teacher(new User(this.requestDataHolder.getUserId()));
		List<Course> courses = this.courseService.getCoursesForTeacher();

		List<Inscription> inscriptions = this.inscriptionService.getInscriptionsByTeacher(teacher);
		Map<Course, AtomicInteger> auxMap = new HashMap<Course, AtomicInteger>();
		for (Inscription inscription : inscriptions) {
			if (!auxMap.containsKey(inscription.getCourse())) {
				auxMap.put(inscription.getCourse(), new AtomicInteger(0));
			}
			auxMap.get(inscription.getCourse()).incrementAndGet();
		}

		List<DBObject> results = new ArrayList<DBObject>();
		DBObject result;
		for (Course course : courses) {
			result = new BasicDBObject("courseName", course.getName());
			if (auxMap.containsKey(course)) {
				result.put("quantity", auxMap.get(course).get());
			} else {
				result.put("quantity", Integer.valueOf(0));
			}

			results.add(result);
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public List<DBObject> getProgressStudentsForCourse(String courseId) {
		List<DBObject> results = new ArrayList<DBObject>();
		List<Workspace> workspaces = this.workspaceService.getWorkspacesByCourse(courseId);

		DBObject result;
		for (Workspace ws : workspaces) {
			result = new BasicDBObject("label",
					ws.getStudent().getUser().getName() + " " + ws.getStudent().getUser().getSurename());
			result.put("data", new ArrayList<Integer>());
			for (WorkspaceProblem wsp : ws.getProblems()) {
				if (Strings.isEmpty(wsp.getState())) {
					((List<Integer>) result.get("data")).add(0);
				} else if ("OK".equals(wsp.getState())) {
					((List<Integer>) result.get("data")).add(1);
				} else if ("NOOK".equals(wsp.getState())) {
					((List<Integer>) result.get("data")).add(-1);
				}
			}
			results.add(result);
		}
		return results;
	}

}
