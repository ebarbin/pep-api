package ar.edu.uade.pfi.pep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Autowired
	private RequestInterceptor requestInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.requestInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login",
				"/user/register", "/user/request-unlock/**", "/user/activate/**", "/user/logout/**", "/file/**");
	}
}
