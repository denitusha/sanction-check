package com.sanctionapp;



import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.cache.PersonCache;
import com.sanctionapp.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class SanctionAppApplication implements CommandLineRunner {


	private final PersonCache personCache;
	private final PersonRepository personRepository;

	public static void main(String[] args) {
		SpringApplication.run(SanctionAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


//		List<Person> personList = personRepository.findAll();
//
//		for(Person person: personList){
//
//			personCache.put(person);
//		}
//
//		System.out.println("Cache is ready");
//		System.out.println(personCache.get(16L));

		Runtime runtime = Runtime.getRuntime();

		// Get the number of available processors
		int availableProcessors = runtime.availableProcessors();

		// Print the number of available processors
		System.out.println("Number of available processors: " + availableProcessors);

	}
}
