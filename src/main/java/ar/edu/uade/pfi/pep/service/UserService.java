package ar.edu.uade.pfi.pep.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.uade.pfi.pep.controller.request.ChangePassword;
import ar.edu.uade.pfi.pep.repository.UserRepository;
import ar.edu.uade.pfi.pep.repository.document.Primitive;
import ar.edu.uade.pfi.pep.repository.document.Problem;
import ar.edu.uade.pfi.pep.repository.document.Student;
import ar.edu.uade.pfi.pep.repository.document.Teacher;
import ar.edu.uade.pfi.pep.repository.document.user.User;
import ar.edu.uade.pfi.pep.repository.document.user.UserAccountEvent;
import ar.edu.uade.pfi.pep.repository.document.user.UserAccountEventType;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private MailService mailService;

	@Autowired
	private GridFsOperations gridOperations;
	
	@Autowired
	private PrimitiveService primitiveService;

	@Autowired
	private ProblemService problemService;
	
	public void register(User user) throws Exception {

		User existingUser = this.repository.findByUsername(user.getUsername());
		if (existingUser != null)
			throw new Exception("El email ingresado ya se encuentra registrado.");

		String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

		Teacher teacher = this.teacherService.getTeacherByDocument(user.getDocumentType(),
				user.getDocumentNumber());
		Student student = this.studentService.getStudentByDocument(user.getDocumentType(),
				user.getDocumentNumber());

		if (teacher == null && student == null) {
			throw new Exception("No hay persona registrada con ese tipo y nro. de documento.");
		} else if (teacher != null && student != null) {
			throw new Exception(
					"La persona se encuentra registrada como alumno y como docente. Verifique los datos o contactese con la institución.");
		} else if (teacher != null){
			if (teacher.getUser() != null) 
				throw new Exception("Ya se ha registrado el docente. Verifique los datos o contactese con la institución.");
		} else if (student != null){
			if (student.getUser() != null) 
				throw new Exception("Ya se ha registrado el alumno. Verifique los datos o contactese con la institución.");
		}

		user.setPassword(hashedPassword);
		user.setActive(Boolean.FALSE);
		user.setLoginAttempt(Integer.valueOf(0));
		user.setLastEvent(new UserAccountEvent());
		user.getLastEvent().setDate(new Date());
		user.getLastEvent().setType(UserAccountEventType.PENDING_ACTIVATION);
		user.getLastEvent().setToken(UUID.randomUUID().toString());

		user = this.repository.save(user);

		if (teacher != null) {
			teacher.setUser(user);
			this.teacherService.update(teacher);
			user.setRole("ROLE_TEACHER");
			user.setInstituteId(teacher.getInstituteId());
			
			List<Primitive> defaultPrimitives = this.primitiveService.getDefaultPrimitives();
			for (Primitive defaultPrimitive : defaultPrimitives) {
				Primitive p = new Primitive();
				p.setCode(defaultPrimitive.getCode());
				p.setDescription(defaultPrimitive.getDescription());
				p.setName(defaultPrimitive.getName());
				p.setTeacher(teacher);
				this.primitiveService.save(p);
			}
			
			List<Problem> defaultProblems = this.problemService.getDefaultProblems();
			for(Problem defaultProblem: defaultProblems) {
				Problem p = new Problem();
				p.setExplanation(defaultProblem.getExplanation());
				p.setName(defaultProblem.getName());
				p.setPreExecution(defaultProblem.getPreExecution());
				p.setTeacher(teacher);
				p.setTeacherSolucion(defaultProblem.getTeacherSolucion());
				this.problemService.save(p);
			}
		}

		if (student != null) {
			student.setUser(user);
			this.studentService.update(student);
			user.setRole("ROLE_STUDENT");
			user.setInstituteId(student.getInstituteId());
		}

		user = this.repository.save(user);

		this.mailService.sendMail(user);
	}

	public void activate(String username, String token) throws Exception {

		User existingUser = this.repository.findByUsername(username);
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (existingUser.getActive())
			throw new Exception("El usuario ingresado ya se encuentra activo.");

		Interval interval = new Interval(new DateTime(existingUser.getLastEvent().getDate()), new DateTime());
		if (interval.toDuration().getStandardHours() < 24) {
			if (existingUser.getLastEvent().getToken().equals(token)) {
				existingUser.setLastEvent(null);
				existingUser.setActive(Boolean.TRUE);
				existingUser.setLoginAttempt(0);
				this.repository.save(existingUser);
			} else {
				throw new Exception("Ha fallado el proceso de validacion. Consulte atraves de pfipep@gmail.com");
			}
		} else {
			throw new Exception("El link de activación se ha vencido, vuelve a gestionarlo.");
		}
	}

	public void requestUnlock(String username) throws Exception {

		User existingUser = this.repository.findByUsername(username);
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (existingUser.getActive()) {
			throw new Exception("El usuario ingresado no está bloqueado.");
		} else {
			if (existingUser.getLastEvent().getType() == UserAccountEventType.PENDING_ACTIVATION) {
				throw new Exception("El usuario esta pendiente de activación. Verifica tu correo electrónico.");
			}
		}

		existingUser.setLastEvent(new UserAccountEvent());
		existingUser.getLastEvent().setDate(new Date());
		existingUser.getLastEvent().setType(UserAccountEventType.UNLOCK);
		existingUser.getLastEvent().setToken(UUID.randomUUID().toString());

		this.repository.save(existingUser);
		this.mailService.sendMail(existingUser);
	}

	public User login(User user) throws Exception {

		User existingUser = this.repository.findByUsername(user.getUsername());
		if (existingUser == null)
			throw new Exception("El usuario ingresado no existe.");

		if (!existingUser.getActive()) {
			if (existingUser.getLastEvent().getType() == UserAccountEventType.PENDING_ACTIVATION) {
				throw new Exception("El usuario esta pendiente de activación. Verifica tu correo electrónico.");
			} else if (existingUser.getLastEvent().getType() == UserAccountEventType.LOCK) {
				throw new Exception("El usuario ingresado está bloqueado.");
			}
		}

		if (!BCrypt.checkpw(user.getPassword(), existingUser.getPassword())) {
			existingUser.setLoginAttempt(existingUser.getLoginAttempt() + 1);
			if (existingUser.getLoginAttempt() > 3) {
				existingUser.setActive(Boolean.FALSE);
				existingUser.setLastEvent(new UserAccountEvent());
				existingUser.getLastEvent().setDate(new Date());
				existingUser.getLastEvent().setType(UserAccountEventType.LOCK);
				this.repository.save(existingUser);
				throw new Exception("La contraseña no corresponde. Usuario bloqueado!");
			} else {
				this.repository.save(existingUser);
				throw new Exception("La contraseña no corresponde.");
			}
		} else {
			existingUser.setLoginAttempt(0);
			existingUser.setLastEvent(new UserAccountEvent());
			existingUser.getLastEvent().setDate(new Date());
			existingUser.getLastEvent().setType(UserAccountEventType.OPEN_SESSION);
			existingUser.getLastEvent().setToken(UUID.randomUUID().toString());
			return this.repository.save(existingUser);
		}
	}

	public void logout(String username) {
		User existingUser = this.repository.findByUsername(username);
		if (existingUser == null)
			return;

		existingUser.setLastEvent(null);
		this.repository.save(existingUser);
	}

	public User update(User user) {
		User existingUser = this.repository.findByUsername(user.getUsername());
		
		existingUser.setName(user.getName());
		existingUser.setSurename(user.getSurename());

		return this.repository.save(existingUser);
	}

	public void changePassword(String username, ChangePassword changePassword) throws Exception {
		User existingUser = this.repository.findByUsername(username);

		if (!BCrypt.checkpw(changePassword.getOldPassword(), existingUser.getPassword())) {
			throw new Exception("La contraseña anterior no corresponde.");
		}

		String hashedPassword = BCrypt.hashpw(changePassword.getNewPassword(), BCrypt.gensalt());
		existingUser.setPassword(hashedPassword);

		this.repository.save(existingUser);
	}

	public User storeProfileImage(String username, MultipartFile file) throws Exception {
		User existingUser = this.repository.findByUsername(username);
		if (existingUser.getImageId() != null) {
			Criteria c = Criteria.where("_id").is(new ObjectId(existingUser.getImageId()));
			this.gridOperations.delete(new Query(c));
		}
		ObjectId objectId = this.gridOperations.store(file.getInputStream(), file.getOriginalFilename());
		existingUser.setImageId(objectId.toString());
		return this.repository.save(existingUser);
	}
	
	public User getUser(String userId) {
		Optional<User> existingUser = this.repository.findById(userId);
		return existingUser.get();
	}
}
