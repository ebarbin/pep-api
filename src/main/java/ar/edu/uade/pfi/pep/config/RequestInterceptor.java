package ar.edu.uade.pfi.pep.config;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import ar.edu.uade.pfi.pep.repository.UserRepository;
import ar.edu.uade.pfi.pep.repository.document.user.User;
import ar.edu.uade.pfi.pep.repository.document.user.UserAccountEventType;

@Component
public class RequestInterceptor implements HandlerInterceptor {

	@Autowired
	private UserRepository userRepository;

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
							user.getLastEvent().setDate(new Date());
							this.userRepository.save(user);
							return true;
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
}
