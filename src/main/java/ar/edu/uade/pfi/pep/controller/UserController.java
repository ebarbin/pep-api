package ar.edu.uade.pfi.pep.controller;

import javax.validation.Valid;
import javax.ws.rs.Consumes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.uade.pfi.pep.controller.request.ChangePassword;
import ar.edu.uade.pfi.pep.controller.response.Response;
import ar.edu.uade.pfi.pep.controller.response.ResponseBuilder;
import ar.edu.uade.pfi.pep.repository.document.user.User;
import ar.edu.uade.pfi.pep.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@PutMapping("/login")
	public ResponseEntity<Response> login(@RequestBody User user) {
		try {
			return ResponseBuilder.success(this.userService.login(user));
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping
	public ResponseEntity<Response> update(@RequestBody User user) {
		try {
			return ResponseBuilder.success(this.userService.update(user));
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
	
	@PutMapping("/{username}/store-profile-image")
	@Consumes("multipart/form-data")
	public ResponseEntity<Response> updateProfileImage(@PathVariable("username") String username,
			@RequestParam("file") MultipartFile file) {
		try {
			return ResponseBuilder.success(this.userService.storeProfileImage(username, file));
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PutMapping("/{username}/change-password")
	public ResponseEntity<Response> changePassword(@PathVariable("username") String username,
			@RequestBody ChangePassword changePassword) {
		try {
			this.userService.changePassword(username, changePassword);
			return ResponseBuilder.success();
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<Response> register(@Valid @RequestBody User user) {
		try {
			this.userService.register(user);
			return ResponseBuilder.success();
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/request-unlock/{username}")
	public ResponseEntity<Response> activate(@PathVariable("username") String username) {
		try {
			this.userService.requestUnlock(username);
			return ResponseBuilder.success();
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/activate/{username}/{token}")
	public ResponseEntity<Response> activate(@PathVariable("username") String username,
			@PathVariable("token") String token) {
		try {
			this.userService.activate(username, token);
			return ResponseBuilder.success();
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}

	@GetMapping("/logout/{username}")
	public ResponseEntity<Response> logout(@PathVariable("username") String username) {
		try {
			this.userService.logout(username);
			return ResponseBuilder.success();
		} catch (Exception e) {
			UserController.LOGGER.error(e.getMessage(), e);
			return ResponseBuilder.error(e);
		}
	}
}
