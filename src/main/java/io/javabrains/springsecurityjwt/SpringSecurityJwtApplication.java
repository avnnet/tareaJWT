package io.javabrains.springsecurityjwt;

import io.javabrains.springsecurityjwt.filters.JwtRequestFilter;
import io.javabrains.springsecurityjwt.models.AuthenticationRequest;
import io.javabrains.springsecurityjwt.models.AuthenticationResponse;
import io.javabrains.springsecurityjwt.models.PhoneNewDto;
import io.javabrains.springsecurityjwt.models.PhoneRepository;
import io.javabrains.springsecurityjwt.models.RegisterResponseDto;
import io.javabrains.springsecurityjwt.models.RegisterUserNewDto;
import io.javabrains.springsecurityjwt.models.Task;
import io.javabrains.springsecurityjwt.models.TaskRepository;
import io.javabrains.springsecurityjwt.models.Telefono;
import io.javabrains.springsecurityjwt.models.User;
import io.javabrains.springsecurityjwt.models.UserNew;
import io.javabrains.springsecurityjwt.models.UserNewRepository;
import io.javabrains.springsecurityjwt.models.UserRepository;
import io.javabrains.springsecurityjwt.models.Usuario;
import io.javabrains.springsecurityjwt.models.UsuarioRepository;
import io.javabrains.springsecurityjwt.models.UsuarioRepository2;
import io.javabrains.springsecurityjwt.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

}

@RestController
class HelloWorldController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private UserNewRepository userNewRepository;

    @Autowired
    private PhoneRepository phoneRepository;

	@PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Aquí guardamos el usuario en la base de datos
        userRepository.save(user);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

	@Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioRepository2 usuarioRepository2;


    @PostMapping("/registerusuario")
    public ResponseEntity<?> registerUser(@RequestBody Usuario usuario) {

         if (userNewRepository.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya registrado.");
        } 

        if (!emailIsValid(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("El correo tiene un formato incorrecto.");
        }

        // Verificar que el formato de la contraseña sea válido
        if (!passwordIsValid(usuario.getPassword())) {
            return ResponseEntity.badRequest().body("La contraseña no cumple con los requisitos.");
        }

        // Asocia los teléfonos con el usuario antes de guardar
        for (Telefono telefono : usuario.getTelefonos()) {
            telefono.setUsuario(usuario);
        }
        try {

            usuarioRepository.save(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya registrado.");
        }


        // Guardamos el usuario junto con los teléfonos en la BD
        //usuarioRepository.save(usuario);

       // return ResponseEntity.ok("Usuario registrado exitosamente");

	// Crear la respuesta con los datos requeridos
	/* RegisterResponseDto response = new RegisterResponseDto();
	 response.setId(1); //"newUser.getId()");
	response.setCreated(LocalDateTime.now());
	response.setModified(newUser.getModified());
	response.setLastLogin(newUser.getLastLogin());
	response.setToken(token);
	response.setIsActive(newUser.getIsActive()); */

    // Generar el token JWT
    //final UserDetails userDetails = ((UserDetailsService) getUserDetails()).loadUserByUsername(usuario.getNombre());
    //final String jwt = jwtTokenUtil.generateToken(userDetails);

    UserNew newUser = new UserNew();
    //newUser.setNombre(registerUserNewDto.getName());
    //newUser.setNombre(registerUserNewDto.getName());
    //newUser.setEmail(registerUserNewDto.getEmail());
    //newUser.setPassword(encodePassword(registerUserNewDto.getPassword())); // Hashear la contraseña
    newUser.setCreated(LocalDateTime.now());
    newUser.setModified(LocalDateTime.now());
    newUser.setLastLogin(LocalDateTime.now());
    //newUser.setToken(jwt);
    newUser.setIsActive(true);

    return ResponseEntity.ok(newUser); 

	//return ResponseEntity.ok(response); 

    }

    // register2
    @PostMapping("/registerusuario2")
public ResponseEntity<?> registerUser2(@RequestBody Usuario usuario) {
    // Verificar si el correo ya está registrado
    if (userNewRepository.existsByEmail(usuario.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya registrado.");
    }

    // Verificar que el formato del correo sea válido
    if (!emailIsValid(usuario.getEmail())) {
        return ResponseEntity.badRequest().body("El correo tiene un formato incorrecto.");
    }

    // Verificar que el formato de la contraseña sea válido
    if (!passwordIsValid(usuario.getPassword())) {
        return ResponseEntity.badRequest().body("La contraseña no cumple con los requisitos.");
    }

    // Asocia los teléfonos con el usuario antes de guardar
    for (Telefono telefono : usuario.getTelefonos()) {
        telefono.setUsuario(usuario);
    }

    // Crear el nuevo usuario
    UserNew newUser = new UserNew();
    //newUser.setId(UUID.randomUUID()); // Generar UUID para el usuario
    newUser.setNombre(usuario.getNombre());
    newUser.setEmail(usuario.getEmail());
    newUser.setPassword(encodePassword(usuario.getPassword())); // Hashear la contraseña
    newUser.setCreated(LocalDateTime.now());
    newUser.setModified(LocalDateTime.now());
    newUser.setLastLogin(LocalDateTime.now());
    newUser.setIsActive(true);

    // Generar token JWT o UUID
    String token = JwtTokenUtils.generateToken(usuario.getEmail());
    newUser.setToken(token); // Persistir el token junto con el usuario

    // Guardar el usuario en la base de datos
    try {
        usuarioRepository2.save(newUser);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el usuario.");
    }

    // Crear la respuesta con los datos requeridos
    RegisterResponseDto response = new RegisterResponseDto();
    response.setId(newUser.getId());
    response.setToken(newUser.getToken());
    response.setModified(newUser.getModified());
    response.setIsActive(true);
    response.setCreated(newUser.getCreated());

    response.setLastLogin(newUser.getLastLogin());

   /* 
    
    response.setLastLogin(newUser.getLastLogin());
    
     */

    return ResponseEntity.status(HttpStatus.CREATED).body(response); // Retornar código 201 para creación exitosa
}
    // fin register2

    public boolean emailIsValid(String email) {
        // Regular expression for validating an email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
	// Este es

	@PostMapping("/registerusernew")    

	public ResponseEntity<?> registerUser(@RequestBody RegisterUserNewDto registerUserNewDto) {
    // Verificar que el email sea válido
    if (!emailIsValid(registerUserNewDto.getEmail())) {
        return ResponseEntity.badRequest().body("El correo tiene un formato incorrecto.");
    }

    // Verificar que el formato de la contraseña sea válido
    if (!passwordIsValid(registerUserNewDto.getPassword())) {
        return ResponseEntity.badRequest().body("La contraseña no cumple con los requisitos.");
    }

    // Verificar si el correo ya está registrado
    if (userNewRepository.existsByEmail(registerUserNewDto.getEmail())) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya registrado.");
    }

    // Crear un nuevo usuario y guardar en la base de datos
    UserNew newUser = new UserNew();
    newUser.setNombre(registerUserNewDto.getName());
    newUser.setNombre(registerUserNewDto.getName());
    newUser.setEmail(registerUserNewDto.getEmail());
    newUser.setPassword(encodePassword(registerUserNewDto.getPassword())); // Hashear la contraseña
    newUser.setCreated(LocalDateTime.now());
    newUser.setModified(LocalDateTime.now());
    newUser.setLastLogin(LocalDateTime.now());
    newUser.setIsActive(true);


    try {

        userNewRepository.save(newUser);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya registrado.");
    }

   // userNewRepository.save(newUser);

    // Guardar teléfonos en la base de datos
    for (PhoneNewDto phoneDto : registerUserNewDto.getPhones()) {
        PhoneNewDto phone = new PhoneNewDto();
        phone.setNumber(phoneDto.getNumber());
        phone.setCitycode(phoneDto.getCitycode());
        phone.setContrycode(phoneDto.getContrycode());
        phone.setUser(newUser); // Relacionar con el usuario
        //PhoneRepository phoneRepository;
         phoneRepository.save(phone);
    }

    // Generar un token (JWT o UUID)
    String token = generateToken(newUser);

    // Crear la respuesta con los datos requeridos
   /* RegisterResponseDto response = new RegisterResponseDto();
     response.setId(newUser.getId());
    response.setCreated(newUser.getCreated());
    response.setModified(newUser.getModified());
    response.setLastLogin(newUser.getLastLogin());
    response.setToken(token);
    response.setIsActive(newUser.getIsActive()); */

    //return ResponseEntity.ok(response);
    return ResponseEntity.ok(newUser);
   }
 


	private String generateToken(User newUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

    private String encodePassword(String password) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'encodePassword'");
        return password;
    }

    private boolean passwordIsValid(String password) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'passwordIsValid'");
        return true;
    }

    /* private boolean emailIsValid(String email) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'emailIsValid'");
    } */

    private String generateToken(UserNew newUser) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateToken'");
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody User loginRequest) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(loginRequest.getUsername()));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

	@GetMapping("/me")
    public ResponseEntity<User> getUserDetails() {
        // Obtén el usuario autenticado desde el contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Encuentra el usuario en la base de datos
        User user = userRepository.findByUsername(username);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(user);
    }

	@Autowired
    private TaskRepository taskRepository;

	@GetMapping("/tasks/user")
    public List<Task> getTasksByUsername(@RequestParam String username) {
        return taskRepository.findByUsername(username);
    }

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}

@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService myUserDetailsService;
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers("/authenticate").permitAll().
						anyRequest().authenticated().and().
						exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

}