package br.com.rafaelvieira.securityapi.modules.auth.validation;

import br.com.rafaelvieira.securityapi.modules.auth.dto.UserRegistration;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author rafae
 */

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistration> {

    @Override
    public boolean isValid(final UserRegistration user, final ConstraintValidatorContext context) {
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
