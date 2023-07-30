package com.camilo.ucc.edu.co.carrocomprasthymeleaf.app;

import com.camilo.ucc.edu.co.carrocomprasthymeleaf.app.services.uploadservice.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarroComprasThymeleafApplication implements CommandLineRunner {

	@Autowired
	IUploadService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(CarroComprasThymeleafApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		uploadService.deleteAll();
		uploadService.init();
	}
}
