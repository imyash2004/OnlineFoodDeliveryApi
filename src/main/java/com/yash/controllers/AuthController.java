package com.yash.controllers;

import com.yash.config.JwtProvider;
import com.yash.model.Cart;
import com.yash.model.USER_ROLE;
import com.yash.model.User;
import com.yash.repository.CartRepo;
import com.yash.repository.UserRepo;
import com.yash.request.LoginRequest;
import com.yash.response.AuthResponse;
import com.yash.service.CustomerUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    private CartRepo cartRepo;


    @PostMapping("signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {
        System.out.println(user);
        User isEmailExist=userRepo.findByEmail(user.getEmail());
        if(isEmailExist!=null){
            throw  new Exception("Email Already used with some Other Account...........");
        }
        User createUser=new User();

//        createUser.setAddresses(user.getAddresses());
        createUser.setEmail(user.getEmail());
        createUser.setFullName(user.getFullName());
        createUser.setPassword(passwordEncoder.encode(user.getPassword()));
        createUser.setRole(user.getRole());
        System.out.println(createUser);

        User savedUser=userRepo.save(createUser);
        Cart cart=new Cart();
        cart.setCustomer(savedUser);
        cartRepo.save(cart);




       Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Registered Successfully ...");
        authResponse.setRole(savedUser.getRole());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse>signin(@RequestBody LoginRequest loginRequest){

        String username=loginRequest.getEmail();
        String password= loginRequest.getPassword();
        Authentication authentication=authenticate(username,password);


        String jwt=jwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Login Successfully ...");
        Collection<?extends GrantedAuthority>authorities=authentication.getAuthorities();
        String role=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();
        authResponse.setRole(USER_ROLE.valueOf(role));
//        authResponse.setRole();
        return new ResponseEntity<>(authResponse, HttpStatus.OK);





    }

    private Authentication authenticate(String username, String password) {


        UserDetails userDetails= customerUserDetailsService.loadUserByUsername(username);

        if(userDetails==null){
            throw new BadCredentialsException("invalid username...");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid Password...");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }

}
