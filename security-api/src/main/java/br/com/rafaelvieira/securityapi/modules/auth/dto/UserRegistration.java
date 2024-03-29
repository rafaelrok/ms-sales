package br.com.rafaelvieira.securityapi.modules.auth.dto;

import br.com.rafaelvieira.securityapi.modules.auth.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.UUID;


/**
 * @author Rafael
 * @since 23/01/2023
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatches
public class UserRegistration {

    private UUID id;

    private String providerUserId;

    @NotEmpty
    private String displayName;

    @NotEmpty
    private String email;

    private SocialProvider socialProvider;

    @Size(min = 8, message = "{Size.userDto.password}")
    private String password;

    @NotEmpty
    private String matchingPassword;


    public UserRegistration(String providerUserId, String displayName, String email, String password, SocialProvider socialProvider) {
        this.providerUserId = providerUserId;
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.socialProvider = socialProvider;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String providerUserID;
        private String displayName;
        private String email;
        private String password;
        private SocialProvider socialProvider;

        public Builder addProviderUserID(final String userID) {
            this.providerUserID = userID;
            return this;
        }

        public Builder addDisplayName(final String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder addEmail(final String email) {
            this.email = email;
            return this;
        }

        public Builder addPassword(final String password) {
            this.password = password;
            return this;
        }

        public Builder addSocialProvider(final SocialProvider socialProvider) {
            this.socialProvider = socialProvider;
            return this;
        }

        public UserRegistration build() {
            return new UserRegistration(providerUserID, displayName, email, password, socialProvider);
        }
    }
}
