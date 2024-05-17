package com.j0k3r.challengealuralatamliteratura;

import com.j0k3r.challengealuralatamliteratura.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChallengeAluraLatamLiteraturaApplication{

	public static void main(String[] args) {
		SpringApplication.run(ChallengeAluraLatamLiteraturaApplication.class, args);
	}

	@Autowired
	private Main main;

	@Bean
	CommandLineRunner init(){
		return args -> {
			main.init();
		};
	}
}
