package br.com.rafaelvieira.gatewayserver.initializers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author rafae
 */
@Slf4j
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    public ApplicationStartup() {
    }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Application started");
    }
}

