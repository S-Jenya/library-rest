package com.mephi.library.controller;

import com.mephi.library.email.EmailService;
import com.mephi.library.model.Role;
import com.mephi.library.model.User;
import com.mephi.library.postRequestResponse.AuthenticationRequest;
import com.mephi.library.postRequestResponse.AuthenticationResponse;
import com.mephi.library.postRequestResponse.response.MessageResponse;
import com.mephi.library.postRequestResponse.UserRegistration;
import com.mephi.library.security.MyUserDetails;
import com.mephi.library.security.UserAuthService;
import com.mephi.library.service.AdminService;
import com.mephi.library.service.UserService;
import com.mephi.library.JWTutil.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


//@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
//@CrossOrigin(origins = "http://localhost:4000")
public class UserRegAuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminService adminService;

    private AuthenticationManager authenticate;
    private JwtUtil jwtTokenUtil;
    private UserAuthService userDetailsService;
    private EmailService emailService;

    @Autowired
    public UserRegAuthController(UserService userService, BCryptPasswordEncoder passwordEncoder, AdminService adminService, AuthenticationManager authenticate, JwtUtil jwtTokenUtil, UserAuthService userDetailsService, EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.authenticate = authenticate;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.emailService = emailService;
    }

    @RequestMapping(value = "/registration/createUser", method = RequestMethod.POST)
    public void createUser(@RequestBody UserRegistration data) {
        try {
            User user = new User();
            Role role = adminService.getUserRoleByName("USER");
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            user.setLogin(data.getLogin());
            user.setPassword(passwordEncoder.encode(data.getPassword()));
            user.setRole(role);
            userService.createUser(user);
            // emailService.sendSimpleMessage(user.getEmail(), "Регистрация в онлайн библиотеке", "Регистрация успешно пройдена!");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
//            throw new Exception("Неверное имя пользователя или пароль", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Неверный логин или пароль!"));
        }
        final MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(authenticationRequest.getLogin());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        AuthenticationResponse response = new AuthenticationResponse(userDetails.getIdUser(),
                userDetails.getUsername(),
                userDetails.getLogin(),
                userDetails.getEmail(),
                roles,
                jwt);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/user/changePassword/{idUser}/{pwdOld}/{pwdNew}")
    public ResponseEntity<?> changePassword(
            @PathVariable Long idUser,
            @PathVariable String pwdOld,
            @PathVariable String pwdNew) {
        try {
            User user = userService.findUserById(idUser);
            if (!passwordEncoder.matches(pwdOld, user.getPassword())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Неверный пароль!"));
            }
            user.setPassword(passwordEncoder.encode(pwdNew));
            userService.saveUser(user);
            return ResponseEntity.ok().body(new MessageResponse("Пароль успешно изменён!"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Возникла неожиданная ошибка!"));
        }
    }

    @PostMapping(value = "/user/changeBaseUserData")
    public ResponseEntity<?> changeBaseUserData(
            @RequestParam(name = "idUser") Long idUser, @RequestParam(name = "login") String login,
            @RequestParam(name = "name") String name, @RequestParam(name = "email") String email) {
        try {
            User user = userService.findUserByLogin(idUser, login);
            if (user != null && !idUser.equals(user.getIdUser())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Логин уже используется"));
            }
            user = userService.findUserByEmail(idUser, email);
            if (user != null && !idUser.equals(user.getIdUser())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Email уже используется"));
            }

//            Long countName = userService.countUserByName(name);
//            Long countEmail = userService.countUserByEmail(email);

            user = userService.findUserById(idUser);
            user.setLogin(login);
            user.setName(name);
            user.setEmail(email);
            userService.saveUser(user);
            return ResponseEntity.ok().body(new MessageResponse("Ваши данные успешно изменены"));
            // emailService.sendSimpleMessage(user.getEmail(), "Регистрация в онлайн библиотеке", "Регистрация успешно пройдена!");
        } catch (Exception ex) {
            String strEx = ex.getCause().getCause().getMessage();
            System.out.println("Ошибка! Подробнее: " + strEx);
            return ResponseEntity.badRequest().body(new MessageResponse("Во время редактирования данных произошла ошибка!"));
        }
    }
}
