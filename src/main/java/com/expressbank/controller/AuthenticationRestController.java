package com.expressbank.controller;

import com.expressbank.dto.ResponseDTO;
import com.expressbank.dto.CredentialDTO;
import com.expressbank.dto.RegistrationRequestDTO;
import com.expressbank.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRestController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/home")
    public String home(){
        return "Welcome";
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody RegistrationRequestDTO request){
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<ResponseDTO> confirm(@RequestParam("token") String token) {
        System.out.println("Confirm page");
        System.out.println("Confirm token: " + token);

        return registrationService.confirmToken(token);
    }

    @GetMapping("/")
    public String test(){
        return "Welcome";
    }

    @PostMapping({"/login"})
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody CredentialDTO model){
        return registrationService.signIn(model);
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<ResponseDTO> getUserByEmail(@Valid @PathVariable String email){
        return registrationService.getUserByEmail(email);
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam Integer id){
        return registrationService.deleteAccount(id);
    }
}
