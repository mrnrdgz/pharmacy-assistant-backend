package com.pharmacyassistant.pharmacy_assistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.pharmacyassistant.pharmacy_assistant.catalog.application.CreateOfferService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PharmacyAssistantApplication {

	public static void main(String[] args) {

        SpringApplication.run(PharmacyAssistantApplication.class, args);
	}
//    @Bean
//    CommandLineRunner run(CreateOfferService service) {
//        return args -> {
//            service.createSampleOffer();
//            System.out.println("Sample offer creada!");
//        };
//    }
}
