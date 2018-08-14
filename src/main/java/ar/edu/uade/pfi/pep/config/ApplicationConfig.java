package ar.edu.uade.pfi.pep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

	@Autowired
	private RequestInterceptor custom;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(this.custom).addPathPatterns("/pep-api/**").excludePathPatterns("/pep-api/user/**");
	}
}
