package com.example.api;

import com.example.api.util.PasswordEncoderTools;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Startup {

	public static void main(String[] args) {
		SpringApplication.run(Startup.class, args);

		PasswordEncoderTools.printPasswordEncoded("admin123");
		PasswordEncoderTools.printPasswordEncoded("102030");
	}

}
