package br.com.fill.surveyscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SurveySchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveySchedulerApplication.class, args);
	}

}
