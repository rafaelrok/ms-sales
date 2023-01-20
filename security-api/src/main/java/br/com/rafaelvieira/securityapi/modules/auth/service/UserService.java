package br.com.rafaelvieira.securityapi.modules.auth.service;

import br.com.rafaelvieira.securityapi.modules.auth.dto.UserDTO;
import br.com.rafaelvieira.securityapi.modules.auth.exceptions.handler.ObjectAlreadyExistException;
import br.com.rafaelvieira.securityapi.modules.auth.exceptions.handler.ObjectNotFoundException;
import br.com.rafaelvieira.securityapi.modules.auth.model.Role;
import br.com.rafaelvieira.securityapi.modules.auth.model.TokenValidation;
import br.com.rafaelvieira.securityapi.modules.auth.model.User;
import br.com.rafaelvieira.securityapi.modules.auth.repository.RoleRepository;
import br.com.rafaelvieira.securityapi.modules.auth.repository.TokenValidationRepository;
import br.com.rafaelvieira.securityapi.modules.auth.repository.UserRepository;
import br.com.rafaelvieira.securityapi.modules.auth.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenValidationRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public  User findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    public User create(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User fromDTO (UserDTO userDTO) {
        return new User(userDTO);
    }

    public User update(User user) {
        Optional<User> updateUser = userRepository.findById(user.getId());
        return updateUser.map(u -> userRepository.save(new User(
                u.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        u.getPassword(),
                        u.isEnabled(),
                        u.getCreatedAt(),
                        u.getRoles())
                        ))
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado!"));
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public User registerUser(User user) {
        if (emailExist(user.getEmail())){
            throw new ObjectAlreadyExistException(String.format("Já extiste uma conta com esse endereço de email"));
        }
        user.setRoles((Set<Role>) Arrays.asList(roleRepository.findByName("ROLE_USER").get()));
        user.setEnabled(false);
        user = create(user);
        this.emailService.sendConfirmationHtmlEmail(user, null);
        return user;
    }

    private boolean emailExist(final String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return true;
        }
        return false;
    }

    public void createVerificationTokenForUser(User user, String token) {
        final TokenValidation vToken = new TokenValidation(token, user);
        verificationTokenRepository.save(vToken);
    }

    public String validateVerificationToken(String token) {
        final Optional<TokenValidation> vToken = verificationTokenRepository.findByToken(token);
        if (!vToken.isPresent()) {
            return "invalidToken";
        }
        final User user = vToken.get().getUser();
        final Calendar cal = Calendar.getInstance();
        if ((vToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0)
        {
            return "expired";
        }
        user.setEnabled(true);
        this.userRepository.save(user);
        return null;
    }

    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ObjectNotFoundException(String.format("Usuário não encontrado!")));
    }

    public TokenValidation generateNewVerificationToken(String email) {
        User user = findByEmail(email);
        Optional<TokenValidation> vToken = verificationTokenRepository.findByUserId(user);
        vToken.get().updateToken(UUID.randomUUID().toString());
        TokenValidation updateVToken = verificationTokenRepository.save(vToken.get());
        emailService.sendConfirmationHtmlEmail(user, updateVToken);
        return updateVToken;
    }

}
