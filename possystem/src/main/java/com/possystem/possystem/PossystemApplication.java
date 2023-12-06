package com.possystem.possystem;

import com.possystem.possystem.domain.Category;
import com.possystem.possystem.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication()
public class PossystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PossystemApplication.class, args);
	}

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public void run(String... args) throws Exception {

	}
}
