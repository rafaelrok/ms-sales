package br.com.rafaelvieira.securityapi.modules.auth.dto;

/**
 * @author rafae
 */

public enum SocialProvider {
    FACEBOOK("facebook"),
    TWITTER("twitter"),
    GOOGLE("google"),
    LOCAL("local");

    private String providerType;

    public String getProviderType() {
        return providerType;
    }

    SocialProvider(final String providerType) {
        this.providerType = providerType;
    }
}
