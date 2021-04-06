package uz.pdp.lesson4vazifa1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.lesson4vazifa1.payload.LoginDto;
import uz.pdp.lesson4vazifa1.security.JwtProvider;
import uz.pdp.lesson4vazifa1.service.MyAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    MyAuthService myAuthService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

            String token = jwtProvider.generateToken(loginDto.getUsername());
            return ResponseEntity.ok(token);
//
        }catch (BadCredentialsException e){
            return ResponseEntity.status(409).body("Login yoki Parol xato");
        }

    }
}
