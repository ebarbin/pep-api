package ar.edu.uade.pfi.pep.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import ar.edu.uade.pfi.pep.common.ProblemState;
import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.document.Course;
import ar.edu.uade.pfi.pep.repository.document.Inscription;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.Workspace;
import ar.edu.uade.pfi.pep.repository.document.WorkspaceProblem;
import ar.edu.uade.pfi.pep.repository.document.user.User;

@Service
public class ChartService {

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Autowired
	private InscriptionService inscriptionService;

	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private CourseService courseService;

	public List<DBObject> getStudentsPerCourse() {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DBObject> getProgressStudentsPerCourse(String courseId) {
		
		List<DBObject> results = new ArrayList<DBObject>();
		List<Workspace> workspaces = this.workspaceService.getWorkspacesByCourse(courseId);

		if (workspaces.isEmpty()) return new ArrayList();
		
		// Creo los mapas
		Map<String, AtomicInteger> mapSuccess = new LinkedHashMap<String, AtomicInteger>();
		Map<String, AtomicInteger> mapNon = new LinkedHashMap<String, AtomicInteger>();
		Map<String, AtomicInteger> mapFail = new LinkedHashMap<String, AtomicInteger>();
		Map<String, AtomicInteger> mapFeedback = new LinkedHashMap<String, AtomicInteger>();

		for (WorkspaceProblem wp : workspaces.get(0).getProblems()) {
			mapSuccess.put(wp.getProblem().getId(), new AtomicInteger(0));
			mapNon.put(wp.getProblem().getId(), new AtomicInteger(0));
			mapFail.put(wp.getProblem().getId(), new AtomicInteger(0));
			mapFeedback.put(wp.getProblem().getId(), new AtomicInteger(0));
		}

		DBObject successResolucion = new BasicDBObject("label", "Correcto");
		successResolucion.put("data", new ArrayList<Integer>());
		results.add(successResolucion);
		
		DBObject nonResolucion = new BasicDBObject("label", "Sin Resolver");
		nonResolucion.put("data", new ArrayList<Integer>());
		results.add(nonResolucion);
		
		DBObject failResolucion = new BasicDBObject("label", "Incorrecto");
		failResolucion.put("data", new ArrayList<Integer>());
		results.add(failResolucion);
		
		DBObject feedBackResolucion = new BasicDBObject("label", "A corrección");
		feedBackResolucion.put("data", new ArrayList<Integer>());
		results.add(feedBackResolucion);
		
		for (Workspace ws : workspaces) {
			for (WorkspaceProblem wsp : ws.getProblems()) {
				if (Strings.isEmpty(wsp.getState())) {
					mapNon.get(wsp.getProblem().getId()).incrementAndGet();
				} else if (ProblemState.OK.name().equals(wsp.getState())) {
					mapSuccess.get(wsp.getProblem().getId()).incrementAndGet();
				} else if (ProblemState.NOOK.name().equals(wsp.getState())) {
					mapFail.get(wsp.getProblem().getId()).incrementAndGet();
				} else if (ProblemState.FEEDBACK.name().equals(wsp.getState())) {
					mapFeedback.get(wsp.getProblem().getId()).incrementAndGet();
				}
			}
		}
		
		for(String id : mapSuccess.keySet()) {
			((List) successResolucion.get("data")).add(Integer.valueOf(mapSuccess.get(id).get()));
		}
		
		for(String id : mapNon.keySet()) {
			((List) nonResolucion.get("data")).add(Integer.valueOf(mapNon.get(id).get()));
		}
		
		for(String id : mapFail.keySet()) {
			((List) failResolucion.get("data")).add(Integer.valueOf(mapFail.get(id).get()));
		}
		
		for(String id : mapFeedback.keySet()) {
			((List) feedBackResolucion.get("data")).add(Integer.valueOf(mapFeedback.get(id).get()));
		}
		
		return results;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DBObject> getTotalProgressStudentsPerCourse(String courseId) {
		
		List<DBObject> results = new ArrayList<DBObject>();
		List<Student> students = this.inscriptionService.getStudentsByCourseId(courseId);
		List<Workspace> workspaces = this.workspaceService.getWorkspacesByCourse(courseId);

		if (students.isEmpty()) return new ArrayList();
		
		// Creo los mapas
		Map<String, AtomicInteger> mapSuccess = new LinkedHashMap<String, AtomicInteger>();
		Map<String, AtomicInteger> mapNon = new LinkedHashMap<String, AtomicInteger>();
		Map<String, AtomicInteger> mapFail = new LinkedHashMap<String, AtomicInteger>();
		Map<String, AtomicInteger> mapFeedback = new LinkedHashMap<String, AtomicInteger>();

		for (Student s : students) {
			mapSuccess.put(s.getId(), new AtomicInteger(0));
			mapNon.put(s.getId(), new AtomicInteger(0));
			mapFail.put(s.getId(), new AtomicInteger(0));
			mapFeedback.put(s.getId(), new AtomicInteger(0));
		}

		DBObject successResolucion = new BasicDBObject("label", "Correcto");
		successResolucion.put("data", new ArrayList<Integer>());
		results.add(successResolucion);
		
		DBObject nonResolucion = new BasicDBObject("label", "Sin Resolver");
		nonResolucion.put("data", new ArrayList<Integer>());
		results.add(nonResolucion);
		
		DBObject failResolucion = new BasicDBObject("label", "Incorrecto");
		failResolucion.put("data", new ArrayList<Integer>());
		results.add(failResolucion);
		
		DBObject feedBackResolucion = new BasicDBObject("label", "A corrección");
		feedBackResolucion.put("data", new ArrayList<Integer>());
		results.add(feedBackResolucion);
		
		for (Workspace ws : workspaces) {
			for (WorkspaceProblem wsp : ws.getProblems()) {
				if (Strings.isEmpty(wsp.getState())) {
					mapNon.get(ws.getStudent().getId()).incrementAndGet();
				} else if (ProblemState.OK.name().equals(wsp.getState())) {
					mapSuccess.get(ws.getStudent().getId()).incrementAndGet();
				} else if (ProblemState.NOOK.name().equals(wsp.getState())) {
					mapFail.get(ws.getStudent().getId()).incrementAndGet();
				} else if (ProblemState.FEEDBACK.name().equals(wsp.getState())) {
					mapFeedback.get(ws.getStudent().getId()).incrementAndGet();
				}
			}
		}
		
		for(String id : mapSuccess.keySet()) {
			((List) successResolucion.get("data")).add(Integer.valueOf(mapSuccess.get(id).get()));
		}
		
		for(String id : mapNon.keySet()) {
			((List) nonResolucion.get("data")).add(Integer.valueOf(mapNon.get(id).get()));
		}
		
		for(String id : mapFail.keySet()) {
			((List) failResolucion.get("data")).add(Integer.valueOf(mapFail.get(id).get()));
		}
		
		for(String id : mapFeedback.keySet()) {
			((List) feedBackResolucion.get("data")).add(Integer.valueOf(mapFeedback.get(id).get()));
		}
		
		return results;
	}

}
