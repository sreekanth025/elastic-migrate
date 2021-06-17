package com.sreekanth.ElasticsearchMigrate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.GregorianCalendar;

@SpringBootApplication
public class ElasticsearchMigrateApplication {

	public static void main(String[] args) {

		System.out.println(new GregorianCalendar(2021, Calendar.JUNE, 05).getTime());

		SpringApplication.run(ElasticsearchMigrateApplication.class, args);
	}

}
