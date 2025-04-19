package br.com.onboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.InMemoryBrokerImplementation;


@SpringBootApplication
public class DigitalOnboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalOnboardingApplication.class, args);
	}

     @Bean
    public IMessageBroker messageBroker() {
        return new InMemoryBrokerImplementation();
    }
}
