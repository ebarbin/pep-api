package ar.edu.uade.pfi.pep.config;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
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

		if (request.getAttribute("username") != null && request.getAttribute("token") != null) {
			String userame = (String) request.getAttribute("username");
			String token = (String) request.getAttribute("token");
			User user = this.userRepository.findByUsername(userame);

			if (user != null) {
				if (user.getLastEvent().getType() == UserAccountEventType.SESSION) {
					if (user.getLastEvent().getToken().equals(token)) {
						Interval interval = new Interval(new DateTime(user.getLastEvent().getDate()), new DateTime());
						if (interval.toDuration().getStandardMinutes() < 10) {
							user.getLastEvent().setDate(new Date());
							this.userRepository.save(user);
							return true;
						}
					}
				}
			}
		}

		response.getWriter().println("error");
		response.setStatus(401);
		return false;
	}
}
