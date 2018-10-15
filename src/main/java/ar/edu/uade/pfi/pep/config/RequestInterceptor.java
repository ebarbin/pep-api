package ar.edu.uade.pfi.pep.config;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import ar.edu.uade.pfi.pep.common.RequestDataHolder;
import ar.edu.uade.pfi.pep.repository.UserRepository;
import ar.edu.uade.pfi.pep.repository.document.user.User;
import ar.edu.uade.pfi.pep.repository.document.user.UserAccountEventType;

@Component
public class RequestInterceptor implements HandlerInterceptor {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RequestDataHolder requestDataHolder;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getHeader("username") != null && request.getHeader("token") != null) {

			String userame = (String) request.getHeader("username");
			String token = (String) request.getHeader("token");

			User user = this.userRepository.findByUsername(userame);

			if (user != null) {
				if (user.getLastEvent().getType() == UserAccountEventType.OPEN_SESSION) {
					if (user.getLastEvent().getToken().equals(token)) {
						Interval interval = new Interval(new DateTime(user.getLastEvent().getDate()), new DateTime());
						if (interval.toDuration().getStandardMinutes() < 10) {

							if (this.checkRequestRolePermission(request.getRequestURI(), user.getRole())) {
								user.getLastEvent().setDate(new Date());
								this.userRepository.save(user);

								this.requestDataHolder.setInstituteId(user.getInstituteId());
								this.requestDataHolder.setUserId(user.getId());

								return true;

							} else {
								response.getWriter().println(HttpStatus.FORBIDDEN.name());
								response.setStatus(HttpStatus.FORBIDDEN.value());
								return false;
							}

						} else {
							response.getWriter().println(HttpStatus.UNAUTHORIZED.name());
							response.setStatus(HttpStatus.UNAUTHORIZED.value());
							return false;
						}
					}
				}
			}
		}

		response.getWriter().println(HttpStatus.FORBIDDEN.name());
		response.setStatus(HttpStatus.FORBIDDEN.value());
		return false;
	}

	private boolean checkRequestRolePermission(String requestedURI, String role) {
		List<String> acceptedUris = this.urlByRole().get(role);
		for (String uri : acceptedUris) {
			if (requestedURI.contains(uri)) {
				return true;
			}
		}
		return false;
	}

	@Bean
	public Map<String, List<String>> urlByRole() {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		/// pep-api/correction/quantity
		map.put("ROLE_TEACHER", Arrays.asList("/pep-api/course", "/pep-api/course/forTeacher", "/pep-api/forTeacher",
				"/pep-api/correction", "/pep-api/consultation/teacher", "/pep-api/correction/quantity",
				"/change-password", "/pep-api/consultation/unreaded/teacher", "/pep-api/chart/students-per-course",
				"/pep-api/consultation/mark-as-read/teacher", "/pep-api/chart/total-progress-student-per-course",
				"/pep-api/student/byCourseId", "/pep-api/chart/progress-student-per-course", "/pep-api/primitive",
				"/pep-api/chart/progress-student-per-course", "/pep-api/inscription", "/pep-api/problem", "/store-profile-image"));
		map.put("ROLE_STUDENT",
				Arrays.asList("/pep-api/workspace/active", "/pep-api/workspace/updateActive",
						"/pep-api/workspace/updateActive", "/pep-api/workspace/active-other-problem",
						"/pep-api/workspace/update-solution", "/pep-api/workspace/mark-problem-ok",
						"/pep-api/workspace/mark-problem-feedback", "/pep-api/workspace/mark-problem-nook",
						"/pep-api/consultation/unreaded/student", "/pep-api/consultation/student",
						"/pep-api/consultation/", "/pep-api/consultation/mark-as-read/student", "/pep-api/inscription",
						"/pep-api/course/forStudent", "/change-password", "/store-profile-image"));
		return map;
	}
}
