package br.com.rafaelvieira.securityapi.modules.auth.controller;

import br.com.rafaelvieira.securityapi.modules.auth.dto.UserDTO;
import br.com.rafaelvieira.securityapi.modules.auth.exceptions.GenericResponse;
import br.com.rafaelvieira.securityapi.modules.auth.model.User;
import br.com.rafaelvieira.securityapi.modules.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author rafae
 */

@RestController
@RequestMapping("/api/public")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/registration/users")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO){
        User user = this.userService.fromDTO(userDTO);
        this.userService.registerUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/regitrationConfirm/users")
    public ResponseEntity<GenericResponse> confirmRegistrationUser(@RequestParam("token") String token){
        final Object result = this.userService.validateVerificationToken(token);
        if (result == null) {
            return ResponseEntity.ok().body(new GenericResponse("Success"));
        }
        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(new GenericResponse(result.toString()));
    }

    @GetMapping(value = "/resendRegistrationToken/users")
    public ResponseEntity<Void> resendRegistrationToken(@RequestParam("email") String email) {
        this.userService.generateNewVerificationToken(email);
        return ResponseEntity.noContent().build();
    }
}
