package com.mephi.library.controller;

import com.mephi.library.model.Role;
import com.mephi.library.model.User;
import com.mephi.library.postRequest.AuthenticationRequest;
import com.mephi.library.postRequest.AuthenticationResponse;
import com.mephi.library.postRequest.MessageResponse;
import com.mephi.library.postRequest.UserRegistration;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


//@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
//@CrossOrigin(origins = "http://localhost:4000")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminService adminService;

    private AuthenticationManager authenticate;
    private JwtUtil jwtTokenUtil;
    private UserAuthService userDetailsService;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder, AdminService adminService, AuthenticationManager authenticate, JwtUtil jwtTokenUtil, UserAuthService userDetailsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.authenticate = authenticate;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value="/registration/createUser", method= RequestMethod.POST)
    public void createUser(@RequestBody UserRegistration data){
        try {
            User user = new User();
            Role role = adminService.getUserRoleByName("ROLE_USER");
            user.setName(data.getName());
            user.setEmail(data.getEmail());
            user.setPassword(passwordEncoder.encode(data.getPassword()));
            user.setRole(role);
            userService.createUser(user);
            System.out.println("Пользователь создан");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticate.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getName(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
//            throw new Exception("Неверное имя пользователя или пароль", e);
             return ResponseEntity.badRequest().body(new MessageResponse("Неверный логин или пароль!"));
        }
        final MyUserDetails userDetails = (MyUserDetails) userDetailsService.loadUserByUsername(authenticationRequest.getName());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        AuthenticationResponse response = new AuthenticationResponse(userDetails.getIdUser(), userDetails.getUsername(), userDetails.getEmail(), roles, jwt);

        return ResponseEntity.ok().body(response);
    }
}
